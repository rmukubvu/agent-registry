package com.agentdna.enforcer.infrastructure.registry;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface DnaRegistryClient {

    /**
     * Calls GET /v1/agents/{dnaId}/verify on the dna-registry service.
     *
     * @throws NoSuchElementException if the agent is not found (404)
     */
    AgentVerification verify(UUID dnaId);
}
