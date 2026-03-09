package com.agentdna.registry.application.results;

import com.agentdna.registry.domain.AgentStatus;

import java.util.UUID;

public record AgentTransitionResult(
        UUID dnaId,
        AgentStatus previousStatus,
        AgentStatus newStatus
) {}
