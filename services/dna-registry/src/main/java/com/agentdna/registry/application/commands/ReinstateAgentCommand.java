package com.agentdna.registry.application.commands;

import java.util.UUID;

public record ReinstateAgentCommand(UUID dnaId, String idemKey) {}
