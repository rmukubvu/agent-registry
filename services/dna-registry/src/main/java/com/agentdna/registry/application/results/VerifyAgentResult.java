package com.agentdna.registry.application.results;

import com.agentdna.registry.domain.AgentStatus;

import java.util.List;
import java.util.UUID;

public record VerifyAgentResult(
        UUID         dnaId,
        UUID         parentDnaId,    // null for top-level manager agents
        String       agentName,
        String       publicKeyHex,   // used by enforcer for Ed25519 signature verification
        AgentStatus  status,
        List<String> capabilities,
        boolean      isAuthorized,
        String       deniedReason    // null when authorized; explains denial otherwise
) {}
