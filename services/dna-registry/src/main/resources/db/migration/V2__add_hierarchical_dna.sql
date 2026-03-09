SET search_path TO agent_dna;

-- ── Hierarchical DNA: parent-child delegation + ephemeral TTL ─────────────────
ALTER TABLE agent_records
    ADD COLUMN parent_dna_id UUID REFERENCES agent_records(dna_id),
    ADD COLUMN expires_at    TIMESTAMPTZ;

-- Fast lookup of all children for a given parent (cascade revocation)
CREATE INDEX idx_agent_parent ON agent_records (parent_dna_id)
    WHERE parent_dna_id IS NOT NULL;

COMMENT ON COLUMN agent_records.parent_dna_id IS
    'Worker agents: references the manager/parent that sponsored this record. '
    'Cascade rule: if parent is revoked, all children are automatically blocked.';

COMMENT ON COLUMN agent_records.expires_at IS
    'Optional TTL for ephemeral worker agents. NULL = no expiry (persistent record).';
