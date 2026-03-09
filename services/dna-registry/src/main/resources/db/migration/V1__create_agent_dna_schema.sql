CREATE SCHEMA IF NOT EXISTS agent_dna;
SET search_path TO agent_dna;

CREATE TABLE agent_records (
    dna_id          UUID        PRIMARY KEY,
    agent_name      TEXT        NOT NULL,
    public_key_hex  TEXT        NOT NULL,
    owner_id        UUID        NOT NULL,
    owner_name      TEXT        NOT NULL,
    jurisdiction    TEXT        NOT NULL,
    capabilities    JSONB       NOT NULL DEFAULT '[]',
    status          TEXT        NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    revoked_at      TIMESTAMPTZ,
    revoked_reason  TEXT,
    version         INTEGER     NOT NULL DEFAULT 1
);

CREATE INDEX idx_agent_status       ON agent_records (status);
CREATE INDEX idx_agent_owner        ON agent_records (owner_id);
CREATE INDEX idx_agent_jurisdiction ON agent_records (jurisdiction);

CREATE TABLE idempotency_keys (
    idem_key       TEXT        PRIMARY KEY,
    result_payload JSONB       NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);
