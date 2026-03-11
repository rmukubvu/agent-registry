package com.agentdna.registry.infrastructure.persistence;

import com.agentdna.registry.domain.AgentRecord;
import com.agentdna.registry.domain.AgentStatus;

import java.util.List;
import java.util.UUID;

public interface AgentRepository {

    AgentRecord save(AgentRecord record);

    AgentRecord findById(UUID dnaId);

    List<AgentRecord> findAll();

    boolean existsRevokedPublicKeyHex(String publicKeyHex);

    /** Returns all agents whose parentDnaId matches — used for cascade revocation. */
    List<AgentRecord> findByParentDnaId(UUID parentDnaId);

    void updateStatus(UUID dnaId, AgentStatus from, AgentStatus to, int expectedVersion);

    void approveActivation(UUID dnaId, int expectedVersion, String approvedBy, String approvalReason);

    void revoke(UUID dnaId, AgentStatus from, String reason, int expectedVersion);
}
