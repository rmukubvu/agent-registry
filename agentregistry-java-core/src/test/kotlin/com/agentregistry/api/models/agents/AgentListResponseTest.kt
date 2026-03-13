// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import com.agentregistry.api.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import java.time.OffsetDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AgentListResponseTest {

    @Test
    fun create() {
        val agentListResponse =
            AgentListResponse.builder()
                .agentName("agentName")
                .addCapability("string")
                .createdAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .jurisdiction("jurisdiction")
                .ownerId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .ownerName("ownerName")
                .publicKeyHex("publicKeyHex")
                .status(AgentStatus.PENDING)
                .updatedAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .version(0)
                .approvalReason("approvalReason")
                .approvedAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .approvedBy("approvedBy")
                .expiresAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .parentDnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .provenanceRef("provenanceRef")
                .revokedAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .revokedReason("revokedReason")
                .workloadIdentity("workloadIdentity")
                .build()

        assertThat(agentListResponse.agentName()).isEqualTo("agentName")
        assertThat(agentListResponse.capabilities()).containsExactly("string")
        assertThat(agentListResponse.createdAt())
            .isEqualTo(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
        assertThat(agentListResponse.dnaId()).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(agentListResponse.jurisdiction()).isEqualTo("jurisdiction")
        assertThat(agentListResponse.ownerId()).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(agentListResponse.ownerName()).isEqualTo("ownerName")
        assertThat(agentListResponse.publicKeyHex()).isEqualTo("publicKeyHex")
        assertThat(agentListResponse.status()).isEqualTo(AgentStatus.PENDING)
        assertThat(agentListResponse.updatedAt())
            .isEqualTo(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
        assertThat(agentListResponse.version()).isEqualTo(0)
        assertThat(agentListResponse.approvalReason()).contains("approvalReason")
        assertThat(agentListResponse.approvedAt())
            .contains(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
        assertThat(agentListResponse.approvedBy()).contains("approvedBy")
        assertThat(agentListResponse.expiresAt())
            .contains(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
        assertThat(agentListResponse.parentDnaId()).contains("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(agentListResponse.provenanceRef()).contains("provenanceRef")
        assertThat(agentListResponse.revokedAt())
            .contains(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
        assertThat(agentListResponse.revokedReason()).contains("revokedReason")
        assertThat(agentListResponse.workloadIdentity()).contains("workloadIdentity")
    }

    @Test
    fun roundtrip() {
        val jsonMapper = jsonMapper()
        val agentListResponse =
            AgentListResponse.builder()
                .agentName("agentName")
                .addCapability("string")
                .createdAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .jurisdiction("jurisdiction")
                .ownerId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .ownerName("ownerName")
                .publicKeyHex("publicKeyHex")
                .status(AgentStatus.PENDING)
                .updatedAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .version(0)
                .approvalReason("approvalReason")
                .approvedAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .approvedBy("approvedBy")
                .expiresAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .parentDnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .provenanceRef("provenanceRef")
                .revokedAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                .revokedReason("revokedReason")
                .workloadIdentity("workloadIdentity")
                .build()

        val roundtrippedAgentListResponse =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(agentListResponse),
                jacksonTypeRef<AgentListResponse>(),
            )

        assertThat(roundtrippedAgentListResponse).isEqualTo(agentListResponse)
    }
}
