package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.commands.ActivateAgentCommand;
import com.agentdna.registry.application.results.AgentTransitionResult;
import com.agentdna.registry.domain.AgentStatus;
import com.agentdna.registry.domain.statemachine.AgentDnaStateMachine;
import com.agentdna.registry.infrastructure.idempotency.IdempotencyStore;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ActivateAgentHandler {

    private static final Logger log = LoggerFactory.getLogger(ActivateAgentHandler.class);

    private final AgentRepository repository;
    private final IdempotencyStore idempotency;

    @Inject
    public ActivateAgentHandler(AgentRepository repository, IdempotencyStore idempotency) {
        this.repository  = repository;
        this.idempotency = idempotency;
    }

    @Transactional
    public AgentTransitionResult handle(ActivateAgentCommand cmd) {
        return idempotency.getOrExecute(cmd.idemKey(), AgentTransitionResult.class,
                () -> executeActivate(cmd));
    }

    private AgentTransitionResult executeActivate(ActivateAgentCommand cmd) {
        requireApprovalMetadata(cmd);

        var record = repository.findById(cmd.dnaId());
        AgentDnaStateMachine.transition(record.status(), AgentStatus.ACTIVE);
        repository.approveActivation(cmd.dnaId(), record.version(), cmd.approvedBy(), cmd.approvalReason());
        log.info("Agent activated dnaId={} approvedBy={}", cmd.dnaId(), cmd.approvedBy());
        return new AgentTransitionResult(cmd.dnaId(), record.status(), AgentStatus.ACTIVE);
    }

    private void requireApprovalMetadata(ActivateAgentCommand cmd) {
        if (cmd.idemKey() == null || cmd.idemKey().isBlank()) {
            throw new IllegalArgumentException("idemKey is required for activation");
        }
        if (cmd.approvedBy() == null || cmd.approvedBy().isBlank()) {
            throw new IllegalArgumentException("approvedBy is required for activation");
        }
        if (cmd.approvalReason() == null || cmd.approvalReason().isBlank()) {
            throw new IllegalArgumentException("approvalReason is required for activation");
        }
    }
}
