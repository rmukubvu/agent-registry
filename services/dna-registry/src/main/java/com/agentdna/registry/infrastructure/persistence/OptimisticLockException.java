package com.agentdna.registry.infrastructure.persistence;

import java.util.UUID;

public class OptimisticLockException extends RuntimeException {

    public OptimisticLockException(UUID dnaId, int version) {
        super("Optimistic lock failure for agent %s at version %d".formatted(dnaId, version));
    }
}
