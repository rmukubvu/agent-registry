package com.agentdna.registry.api.rest;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RegisterAgentPayload(
        String       agentName,
        String       publicKeyHex,
        String       workloadIdentity,
        String       provenanceRef,
        UUID         ownerId,
        String       ownerName,
        String       jurisdiction,
        List<String> capabilities,
        UUID         parentDnaId,  // optional — null for top-level manager agents
        Instant      expiresAt,    // optional — null for persistent agents; set TTL for workers
        String       idemKey
) {}
