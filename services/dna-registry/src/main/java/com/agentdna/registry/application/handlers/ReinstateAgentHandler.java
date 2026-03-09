package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.commands.ReinstateAgentCommand;
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
public class ReinstateAgentHandler {

    private static final Logger log = LoggerFactory.getLogger(ReinstateAgentHandler.class);

    private final AgentRepository repository;
    private final IdempotencyStore idempotency;

    @Inject
    public ReinstateAgentHandler(AgentRepository repository, IdempotencyStore idempotency) {
        this.repository  = repository;
        this.idempotency = idempotency;
    }

    @Transactional
    public AgentTransitionResult handle(ReinstateAgentCommand cmd) {
        return idempotency.getOrExecute(cmd.idemKey(), AgentTransitionResult.class,
                () -> executeReinstate(cmd));
    }

    private AgentTransitionResult executeReinstate(ReinstateAgentCommand cmd) {
        var record = repository.findById(cmd.dnaId());
        AgentDnaStateMachine.transition(record.status(), AgentStatus.ACTIVE);
        repository.updateStatus(cmd.dnaId(), record.status(), AgentStatus.ACTIVE, record.version());
        log.info("Agent reinstated dnaId={}", cmd.dnaId());
        return new AgentTransitionResult(cmd.dnaId(), record.status(), AgentStatus.ACTIVE);
    }
}
