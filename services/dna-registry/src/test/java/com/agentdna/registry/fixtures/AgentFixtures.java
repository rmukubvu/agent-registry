package com.agentdna.registry.fixtures;

import com.agentdna.registry.domain.AgentRecord;
import com.agentdna.registry.domain.AgentStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class AgentFixtures {

    public static final UUID DNA_ID    = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    public static final UUID PARENT_ID = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");
    public static final UUID OWNER_ID  = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

    private AgentFixtures() {}

    /** Top-level manager agent — no parent, no expiry. */
    public static AgentRecord pending() {
        return agentWith(AgentStatus.PENDING, null, null);
    }

    public static AgentRecord active() {
        return agentWith(AgentStatus.ACTIVE, null, null);
    }

    public static AgentRecord suspended() {
        return agentWith(AgentStatus.SUSPENDED, null, null);
    }

    public static AgentRecord revoked() {
        var now = Instant.now();
        return new AgentRecord(
                DNA_ID, null, "TestAgent", "deadbeef",
                OWNER_ID, "TurfOS", "ZA",
                List.of("mcp:filesystem"), AgentStatus.REVOKED,
                now, now, now, "Misbehaving", null, 2);
    }

    /** Active worker — linked to PARENT_ID, no expiry. */
    public static AgentRecord activeWorker() {
        return agentWith(AgentStatus.ACTIVE, PARENT_ID, null);
    }

    /** Active worker whose TTL has already passed. */
    public static AgentRecord expiredWorker() {
        return agentWith(AgentStatus.ACTIVE, null, Instant.now().minusSeconds(3600));
    }

    /** Active manager (used as the parent in cascade tests). */
    public static AgentRecord activeParent() {
        var now = Instant.now();
        return new AgentRecord(
                PARENT_ID, null, "ParentManager", "cafebabe",
                OWNER_ID, "TurfOS", "ZA",
                List.of("mcp:filesystem"), AgentStatus.ACTIVE,
                now, now, null, null, null, 1);
    }

    /** Revoked manager — triggers cascade deny for its workers. */
    public static AgentRecord revokedParent() {
        var now = Instant.now();
        return new AgentRecord(
                PARENT_ID, null, "ParentManager", "cafebabe",
                OWNER_ID, "TurfOS", "ZA",
                List.of("mcp:filesystem"), AgentStatus.REVOKED,
                now, now, now, "Compromised", null, 2);
    }

    // ── internal factory ─────────────────────────────────────────────────────

    private static AgentRecord agentWith(AgentStatus status, UUID parentDnaId, Instant expiresAt) {
        var now = Instant.now();
        return new AgentRecord(
                DNA_ID, parentDnaId, "TestAgent", "deadbeef",
                OWNER_ID, "TurfOS", "ZA",
                List.of("mcp:filesystem"), status,
                now, now, null, null, expiresAt, 1);
    }
}
