package com.agentdna.registry.application.commands;

import java.util.UUID;

public record SuspendAgentCommand(UUID dnaId, String reason, String idemKey) {}
