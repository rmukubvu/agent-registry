package com.agentdna.registry.infrastructure.events;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@ApplicationScoped
public class NoopOutboxPublisher implements OutboxPublisher {

    private static final Logger log = LoggerFactory.getLogger(NoopOutboxPublisher.class);

    @Override
    public void publish(String eventType, UUID entityId, Object payload) {
        log.info("[OUTBOX] eventType={} entityId={}", eventType, entityId);
    }
}
