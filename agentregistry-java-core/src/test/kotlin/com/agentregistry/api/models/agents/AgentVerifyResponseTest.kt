// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import com.agentregistry.api.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AgentVerifyResponseTest {

    @Test
    fun create() {
        val agentVerifyResponse =
            AgentVerifyResponse.builder()
                .agentName("agentName")
                .addCapability("string")
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .isAuthorized(true)
                .publicKeyHex("publicKeyHex")
                .status(AgentStatus.PENDING)
                .deniedReason("deniedReason")
                .parentDnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .build()

        assertThat(agentVerifyResponse.agentName()).isEqualTo("agentName")
        assertThat(agentVerifyResponse.capabilities()).containsExactly("string")
        assertThat(agentVerifyResponse.dnaId()).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(agentVerifyResponse.isAuthorized()).isEqualTo(true)
        assertThat(agentVerifyResponse.publicKeyHex()).isEqualTo("publicKeyHex")
        assertThat(agentVerifyResponse.status()).isEqualTo(AgentStatus.PENDING)
        assertThat(agentVerifyResponse.deniedReason()).contains("deniedReason")
        assertThat(agentVerifyResponse.parentDnaId())
            .contains("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
    }

    @Test
    fun roundtrip() {
        val jsonMapper = jsonMapper()
        val agentVerifyResponse =
            AgentVerifyResponse.builder()
                .agentName("agentName")
                .addCapability("string")
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .isAuthorized(true)
                .publicKeyHex("publicKeyHex")
                .status(AgentStatus.PENDING)
                .deniedReason("deniedReason")
                .parentDnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .build()

        val roundtrippedAgentVerifyResponse =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(agentVerifyResponse),
                jacksonTypeRef<AgentVerifyResponse>(),
            )

        assertThat(roundtrippedAgentVerifyResponse).isEqualTo(agentVerifyResponse)
    }
}
