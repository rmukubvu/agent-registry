package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.commands.RevokeAgentCommand;
import com.agentdna.registry.application.results.AgentTransitionResult;
import com.agentdna.registry.domain.AgentStatus;
import com.agentdna.registry.domain.statemachine.AgentDnaStateMachine;
import com.agentdna.registry.infrastructure.events.OutboxPublisher;
import com.agentdna.registry.infrastructure.idempotency.IdempotencyStore;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RevokeAgentHandler {

    private static final Logger log = LoggerFactory.getLogger(RevokeAgentHandler.class);

    private final AgentRepository  repository;
    private final IdempotencyStore idempotency;
    private final OutboxPublisher  outbox;

    @Inject
    public RevokeAgentHandler(AgentRepository  repository,
                              IdempotencyStore  idempotency,
                              OutboxPublisher  outbox) {
        this.repository  = repository;
        this.idempotency = idempotency;
        this.outbox      = outbox;
    }

    @Transactional
    public AgentTransitionResult handle(RevokeAgentCommand cmd) {
        return idempotency.getOrExecute(cmd.idemKey(), AgentTransitionResult.class,
                () -> executeRevoke(cmd));
    }

    private AgentTransitionResult executeRevoke(RevokeAgentCommand cmd) {
        var record = repository.findById(cmd.dnaId());
        AgentDnaStateMachine.transition(record.status(), AgentStatus.REVOKED);
        repository.revoke(cmd.dnaId(), record.status(), cmd.reason(), record.version());
        outbox.publish("AGENT_REVOKED", cmd.dnaId(), record);

        // Cascade: revoke all children that are not already REVOKED
        var cascadeReason = "Parent agent " + cmd.dnaId() + " was revoked — cascade";
        var children      = repository.findByParentDnaId(cmd.dnaId());
        for (var child : children) {
            if (child.status() != AgentStatus.REVOKED) {
                repository.revoke(child.dnaId(), child.status(), cascadeReason, child.version());
                outbox.publish("AGENT_REVOKED", child.dnaId(), child);
                log.warn("Child REVOKED (cascade) dnaId={} parent={}", child.dnaId(), cmd.dnaId());
            }
        }

        log.warn("Agent REVOKED dnaId={} reason={} cascadeCount={}",
                cmd.dnaId(), cmd.reason(), children.size());
        return new AgentTransitionResult(cmd.dnaId(), record.status(), AgentStatus.REVOKED);
    }
}
