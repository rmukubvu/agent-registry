package com.agentdna.registry.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AgentRecord(
        UUID           dnaId,
        UUID           parentDnaId,   // nullable — links a worker agent to its manager
        String         agentName,
        String         publicKeyHex,
        String         workloadIdentity, // nullable — binds the record to a workload or service principal
        String         provenanceRef,    // nullable — build/release/provenance reference for governance review
        UUID           ownerId,
        String         ownerName,
        String         jurisdiction,
        List<String>   capabilities,
        AgentStatus    status,
        Instant        createdAt,
        Instant        updatedAt,
        Instant        approvedAt,
        String         approvedBy,
        String         approvalReason,
        Instant        revokedAt,
        String         revokedReason,
        Instant        expiresAt,     // nullable — TTL for ephemeral worker agents
        int            version
) {}
