SET search_path TO agent_dna;

ALTER TABLE agent_records
    ADD COLUMN workload_identity TEXT,
    ADD COLUMN provenance_ref    TEXT,
    ADD COLUMN approved_at       TIMESTAMPTZ,
    ADD COLUMN approved_by       TEXT,
    ADD COLUMN approval_reason   TEXT;

CREATE INDEX idx_agent_public_key ON agent_records (public_key_hex);

COMMENT ON COLUMN agent_records.workload_identity IS
    'Optional workload or service-principal identity used during issuance review.';

COMMENT ON COLUMN agent_records.provenance_ref IS
    'Optional software supply-chain or deployment provenance reference reviewed by governance.';

COMMENT ON COLUMN agent_records.approved_at IS
    'Timestamp when governance approved this record for activation.';

COMMENT ON COLUMN agent_records.approved_by IS
    'Actor or system that approved activation of the agent record.';

COMMENT ON COLUMN agent_records.approval_reason IS
    'Human-readable reason for the governance approval decision.';
