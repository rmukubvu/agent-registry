package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.commands.RegisterAgentCommand;
import com.agentdna.registry.application.results.RegisterAgentResult;
import com.agentdna.registry.domain.AgentRecord;
import com.agentdna.registry.domain.AgentStatus;
import com.agentdna.registry.infrastructure.events.OutboxPublisher;
import com.agentdna.registry.infrastructure.idempotency.IdempotencyStore;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class RegisterAgentHandler {

    private static final Logger log = LoggerFactory.getLogger(RegisterAgentHandler.class);

    private final AgentRepository  repository;
    private final IdempotencyStore idempotency;
    private final OutboxPublisher  outbox;

    @Inject
    public RegisterAgentHandler(AgentRepository  repository,
                                IdempotencyStore idempotency,
                                OutboxPublisher  outbox) {
        this.repository  = repository;
        this.idempotency = idempotency;
        this.outbox      = outbox;
    }

    @Transactional
    public RegisterAgentResult handle(RegisterAgentCommand cmd) {
        return idempotency.getOrExecute(cmd.idemKey(), RegisterAgentResult.class,
                () -> executeRegister(cmd));
    }

    private RegisterAgentResult executeRegister(RegisterAgentCommand cmd) {
        validateGovernanceAnchors(cmd);

        if (cmd.parentDnaId() != null) {
            validateParentIsActive(cmd.parentDnaId());
        }

        var now   = Instant.now();
        var dnaId = UUID.randomUUID();
        var record = new AgentRecord(
                dnaId, cmd.parentDnaId(),
                cmd.agentName(), cmd.publicKeyHex(), cmd.workloadIdentity(), cmd.provenanceRef(),
                cmd.ownerId(), cmd.ownerName(), cmd.jurisdiction(),
                cmd.capabilities(), AgentStatus.PENDING,
                now, now, null, null, null, null, null, cmd.expiresAt(), 1);

        repository.save(record);
        outbox.publish("AGENT_REGISTERED", dnaId, record);

        log.info("Agent registered dnaId={} owner={} jurisdiction={} parent={}",
                dnaId, cmd.ownerName(), cmd.jurisdiction(), cmd.parentDnaId());

        return new RegisterAgentResult(dnaId, cmd.agentName(), AgentStatus.PENDING);
    }

    private void validateParentIsActive(UUID parentDnaId) {
        var parent = repository.findById(parentDnaId);
        if (parent.status() != AgentStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Parent agent " + parentDnaId + " is not ACTIVE — cannot sponsor a worker");
        }
    }

    private void validateGovernanceAnchors(RegisterAgentCommand cmd) {
        if (repository.existsRevokedPublicKeyHex(cmd.publicKeyHex())) {
            throw new IllegalStateException(
                    "Public key has revoked lineage and cannot be re-registered: " + cmd.publicKeyHex());
        }
    }
}
