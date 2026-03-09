package com.agentdna.registry.application.commands;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RegisterAgentCommand(
        String       agentName,
        String       publicKeyHex,
        UUID         ownerId,
        String       ownerName,
        String       jurisdiction,
        List<String> capabilities,
        UUID         parentDnaId,  // nullable — null for manager agents
        Instant      expiresAt,    // nullable — null for persistent records
        String       idemKey
) {}
