package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.results.VerifyAgentResult;
import com.agentdna.registry.domain.AgentRecord;
import com.agentdna.registry.domain.AgentStatus;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class VerifyAgentHandler {

    private final AgentRepository repository;

    @Inject
    public VerifyAgentHandler(AgentRepository repository) {
        this.repository = repository;
    }

    public VerifyAgentResult handle(UUID dnaId) {
        var record = repository.findById(dnaId);

        // 1. TTL check — ephemeral worker records expire
        if (record.expiresAt() != null && Instant.now().isAfter(record.expiresAt())) {
            return denied(record, "Agent record has expired");
        }

        // 2. Status check — only ACTIVE records are authorised
        if (record.status() != AgentStatus.ACTIVE) {
            return denied(record, "Agent not authorized — status: " + record.status());
        }

        // 3. Cascade check — if this is a worker, its parent must also be ACTIVE
        if (record.parentDnaId() != null) {
            var parent = repository.findById(record.parentDnaId());
            if (parent.status() != AgentStatus.ACTIVE) {
                return denied(record,
                        "Parent agent blocked — cascade deny (parent status: " + parent.status() + ")");
            }
        }

        return authorized(record);
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private VerifyAgentResult authorized(AgentRecord r) {
        return new VerifyAgentResult(
                r.dnaId(), r.parentDnaId(), r.agentName(), r.publicKeyHex(),
                r.status(), r.capabilities(), true, null);
    }

    private VerifyAgentResult denied(AgentRecord r, String reason) {
        return new VerifyAgentResult(
                r.dnaId(), r.parentDnaId(), r.agentName(), r.publicKeyHex(),
                r.status(), r.capabilities(), false, reason);
    }
}
