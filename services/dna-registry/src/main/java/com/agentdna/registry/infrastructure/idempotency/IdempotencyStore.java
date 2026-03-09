package com.agentdna.registry.infrastructure.idempotency;

import java.util.function.Supplier;

public interface IdempotencyStore {

    <T> T getOrExecute(String idemKey, Class<T> resultType, Supplier<T> action);
}
