package com.agentdna.enforcer.infrastructure.registry;

import java.util.List;
import java.util.UUID;

/**
 * Local DTO for the verify response from dna-registry.
 * Mirrors VerifyAgentResult — no shared-module dependency for this POC.
 */
public record AgentVerification(
        UUID         dnaId,
        UUID         parentDnaId,   // null for manager agents
        String       agentName,
        String       publicKeyHex,  // Ed25519 public key — used for signature verification
        String       status,
        List<String> capabilities,
        boolean      isAuthorized,
        String       deniedReason   // null when authorized; explains denial otherwise
) {}
