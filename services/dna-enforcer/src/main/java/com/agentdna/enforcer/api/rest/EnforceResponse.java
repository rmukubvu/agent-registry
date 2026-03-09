package com.agentdna.enforcer.api.rest;

import java.util.UUID;

public record EnforceResponse(
        boolean allowed,
        UUID    dnaId,
        String  agentName,
        String  toolName,
        String  reason
) {
    public static EnforceResponse allowed(UUID dnaId, String agentName, String toolName) {
        return new EnforceResponse(true, dnaId, agentName, toolName, null);
    }

    public static EnforceResponse denied(UUID dnaId, String agentName,
                                         String toolName, String reason) {
        return new EnforceResponse(false, dnaId, agentName, toolName, reason);
    }
}
