package com.agentdna.registry.application.commands;

import java.util.UUID;

public record RevokeAgentCommand(UUID dnaId, String reason, String idemKey) {}
