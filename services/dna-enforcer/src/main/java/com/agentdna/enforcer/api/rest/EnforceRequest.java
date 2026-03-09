package com.agentdna.enforcer.api.rest;

import java.util.Map;
import java.util.UUID;

public record EnforceRequest(
        UUID                dnaId,
        String              toolName,
        Map<String, Object> toolPayload,
        String              signature   // optional hex-encoded Ed25519 signature over "{dnaId}:{toolName}"
) {}
