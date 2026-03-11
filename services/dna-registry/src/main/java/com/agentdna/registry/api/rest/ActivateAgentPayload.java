package com.agentdna.registry.api.rest;

public record ActivateAgentPayload(
        String idemKey,
        String approvedBy,
        String approvalReason
) {}
