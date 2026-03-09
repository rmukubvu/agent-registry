package com.agentdna.registry.infrastructure.events;

import java.util.UUID;

public interface OutboxPublisher {

    void publish(String eventType, UUID entityId, Object payload);
}
