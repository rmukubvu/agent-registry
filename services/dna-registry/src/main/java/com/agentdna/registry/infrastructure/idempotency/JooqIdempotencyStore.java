package com.agentdna.registry.infrastructure.idempotency;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.JSONB;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.function.Supplier;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@ApplicationScoped
public class JooqIdempotencyStore implements IdempotencyStore {

    private static final String TABLE = "agent_dna.idempotency_keys";

    private final DSLContext dsl;
    private final ObjectMapper mapper;

    @Inject
    public JooqIdempotencyStore(DSLContext dsl, ObjectMapper mapper) {
        this.dsl    = dsl;
        this.mapper = mapper;
    }

    @Override
    public <T> T getOrExecute(String idemKey, Class<T> resultType, Supplier<T> action) {
        var existing = dsl.selectFrom(table(TABLE))
                .where(field("idem_key").eq(idemKey))
                .fetchOne();

        if (existing != null) {
            return deserialize(existing.get(field("result_payload", JSONB.class)), resultType);
        }

        T result = action.get();
        dsl.insertInto(table(TABLE))
                .set(field("idem_key"),       idemKey)
                .set(field("result_payload"), JSONB.valueOf(serialize(result)))
                .set(field("created_at"),     OffsetDateTime.now(ZoneOffset.UTC))
                .onConflictDoNothing()
                .execute();
        return result;
    }

    private String serialize(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Idempotency serialisation failed", e);
        }
    }

    private <T> T deserialize(JSONB jsonb, Class<T> type) {
        try {
            return mapper.readValue(jsonb.data(), type);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Idempotency deserialisation failed", e);
        }
    }
}
