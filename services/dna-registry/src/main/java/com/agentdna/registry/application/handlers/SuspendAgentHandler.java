package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.commands.SuspendAgentCommand;
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
public class SuspendAgentHandler {

    private static final Logger log = LoggerFactory.getLogger(SuspendAgentHandler.class);

    private final AgentRepository repository;
    private final IdempotencyStore idempotency;

    @Inject
    public SuspendAgentHandler(AgentRepository repository, IdempotencyStore idempotency) {
        this.repository  = repository;
        this.idempotency = idempotency;
    }

    @Transactional
    public AgentTransitionResult handle(SuspendAgentCommand cmd) {
        return idempotency.getOrExecute(cmd.idemKey(), AgentTransitionResult.class,
                () -> executeSuspend(cmd));
    }

    private AgentTransitionResult executeSuspend(SuspendAgentCommand cmd) {
        var record = repository.findById(cmd.dnaId());
        AgentDnaStateMachine.transition(record.status(), AgentStatus.SUSPENDED);
        repository.updateStatus(cmd.dnaId(), record.status(), AgentStatus.SUSPENDED, record.version());
        log.info("Agent suspended dnaId={} reason={}", cmd.dnaId(), cmd.reason());
        return new AgentTransitionResult(cmd.dnaId(), record.status(), AgentStatus.SUSPENDED);
    }
}
