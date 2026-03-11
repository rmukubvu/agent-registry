package com.agentdna.registry.infrastructure.persistence;

import com.agentdna.registry.domain.AgentRecord;
import com.agentdna.registry.domain.AgentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.JSONB;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@ApplicationScoped
public class JooqAgentRepository implements AgentRepository {

    private static final String TABLE     = "agent_dna.agent_records";
    private static final TypeReference<List<String>> LIST_TYPE = new TypeReference<>() {};

    private final DSLContext   dsl;
    private final ObjectMapper mapper;

    @Inject
    public JooqAgentRepository(DSLContext dsl, ObjectMapper mapper) {
        this.dsl    = dsl;
        this.mapper = mapper;
    }

    @Override
    public AgentRecord save(AgentRecord r) {
        dsl.insertInto(table(TABLE))
                .set(field("dna_id"),         r.dnaId())
                .set(field("parent_dna_id"),   r.parentDnaId())
                .set(field("agent_name"),      r.agentName())
                .set(field("public_key_hex"),  r.publicKeyHex())
                .set(field("workload_identity"), r.workloadIdentity())
                .set(field("provenance_ref"),    r.provenanceRef())
                .set(field("owner_id"),        r.ownerId())
                .set(field("owner_name"),      r.ownerName())
                .set(field("jurisdiction"),    r.jurisdiction())
                .set(field("capabilities"),    JSONB.valueOf(toJson(r.capabilities())))
                .set(field("status"),          r.status().name())
                .set(field("created_at"),      odt(r.createdAt()))
                .set(field("updated_at"),      odt(r.updatedAt()))
                .set(field("approved_at"),     odt(r.approvedAt()))
                .set(field("approved_by"),     r.approvedBy())
                .set(field("approval_reason"), r.approvalReason())
                .set(field("expires_at"),      odt(r.expiresAt()))
                .set(field("version"),         r.version())
                .execute();
        return r;
    }

    @Override
    public AgentRecord findById(UUID dnaId) {
        var rec = dsl.selectFrom(table(TABLE))
                .where(field("dna_id").eq(dnaId))
                .fetchOne();
        if (rec == null) {
            throw new NoSuchElementException("Agent not found: " + dnaId);
        }
        return toRecord(rec);
    }

    @Override
    public List<AgentRecord> findAll() {
        return dsl.selectFrom(table(TABLE))
                .orderBy(field("created_at").desc())
                .fetch()
                .map(this::toRecord);
    }

    @Override
    public boolean existsRevokedPublicKeyHex(String publicKeyHex) {
        return dsl.fetchExists(
                dsl.selectOne()
                        .from(table(TABLE))
                        .where(field("public_key_hex").eq(publicKeyHex))
                        .and(field("status").eq(AgentStatus.REVOKED.name()))
        );
    }

    @Override
    public List<AgentRecord> findByParentDnaId(UUID parentDnaId) {
        return dsl.selectFrom(table(TABLE))
                .where(field("parent_dna_id").eq(parentDnaId))
                .fetch()
                .map(this::toRecord);
    }

    @Override
    public void updateStatus(UUID dnaId, AgentStatus from, AgentStatus to, int expectedVersion) {
        int rows = dsl.update(table(TABLE))
                .set(field("status"),     to.name())
                .set(field("updated_at"), odt(Instant.now()))
                .set(field("version"),    expectedVersion + 1)
                .where(field("dna_id").eq(dnaId))
                .and(field("version").eq(expectedVersion))
                .execute();
        if (rows == 0) {
            throw new OptimisticLockException(dnaId, expectedVersion);
        }
    }

    @Override
    public void approveActivation(UUID dnaId, int expectedVersion, String approvedBy, String approvalReason) {
        var now  = odt(Instant.now());
        int rows = dsl.update(table(TABLE))
                .set(field("status"),          AgentStatus.ACTIVE.name())
                .set(field("approved_at"),     now)
                .set(field("approved_by"),     approvedBy)
                .set(field("approval_reason"), approvalReason)
                .set(field("updated_at"),      now)
                .set(field("version"),         expectedVersion + 1)
                .where(field("dna_id").eq(dnaId))
                .and(field("version").eq(expectedVersion))
                .execute();
        if (rows == 0) {
            throw new OptimisticLockException(dnaId, expectedVersion);
        }
    }

    @Override
    public void revoke(UUID dnaId, AgentStatus from, String reason, int expectedVersion) {
        var now  = odt(Instant.now());
        int rows = dsl.update(table(TABLE))
                .set(field("status"),         AgentStatus.REVOKED.name())
                .set(field("revoked_at"),      now)
                .set(field("revoked_reason"),  reason)
                .set(field("updated_at"),      now)
                .set(field("version"),         expectedVersion + 1)
                .where(field("dna_id").eq(dnaId))
                .and(field("version").eq(expectedVersion))
                .execute();
        if (rows == 0) {
            throw new OptimisticLockException(dnaId, expectedVersion);
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private AgentRecord toRecord(org.jooq.Record rec) {
        return new AgentRecord(
                rec.get(field("dna_id"),         UUID.class),
                rec.get(field("parent_dna_id"),  UUID.class),
                rec.get(field("agent_name"),      String.class),
                rec.get(field("public_key_hex"),  String.class),
                rec.get(field("workload_identity"), String.class),
                rec.get(field("provenance_ref"),    String.class),
                rec.get(field("owner_id"),        UUID.class),
                rec.get(field("owner_name"),      String.class),
                rec.get(field("jurisdiction"),    String.class),
                fromJson(rec.get(field("capabilities"), JSONB.class)),
                AgentStatus.valueOf(rec.get(field("status"), String.class)),
                toInstant(rec.get(field("created_at"),  OffsetDateTime.class)),
                toInstant(rec.get(field("updated_at"),  OffsetDateTime.class)),
                toInstant(rec.get(field("approved_at"), OffsetDateTime.class)),
                rec.get(field("approved_by"),     String.class),
                rec.get(field("approval_reason"), String.class),
                toInstant(rec.get(field("revoked_at"),  OffsetDateTime.class)),
                rec.get(field("revoked_reason"),  String.class),
                toInstant(rec.get(field("expires_at"),  OffsetDateTime.class)),
                rec.get(field("version"),         Integer.class)
        );
    }

    private OffsetDateTime odt(Instant i) {
        return i == null ? null : i.atOffset(ZoneOffset.UTC);
    }

    private Instant toInstant(OffsetDateTime odt) {
        return odt == null ? null : odt.toInstant();
    }

    private String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("JSON serialisation failed", e);
        }
    }

    private List<String> fromJson(JSONB jsonb) {
        try {
            return jsonb == null ? List.of()
                    : mapper.readValue(jsonb.data(), LIST_TYPE);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("JSON deserialisation failed", e);
        }
    }
}
