package com.agentdna.registry.application.results;

import com.agentdna.registry.domain.AgentStatus;

import java.util.UUID;

public record RegisterAgentResult(UUID dnaId, String agentName, AgentStatus status) {}
