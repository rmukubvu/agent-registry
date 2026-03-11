package com.agentdna.registry.application.commands;

import java.util.UUID;

public record ActivateAgentCommand(
        UUID   dnaId,
        String idemKey,
        String approvedBy,
        String approvalReason
) {}
