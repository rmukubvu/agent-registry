// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import com.agentregistry.api.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AgentCreateResponseTest {

    @Test
    fun create() {
        val agentCreateResponse =
            AgentCreateResponse.builder()
                .agentName("agentName")
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .status(AgentStatus.PENDING)
                .build()

        assertThat(agentCreateResponse.agentName()).isEqualTo("agentName")
        assertThat(agentCreateResponse.dnaId()).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(agentCreateResponse.status()).isEqualTo(AgentStatus.PENDING)
    }

    @Test
    fun roundtrip() {
        val jsonMapper = jsonMapper()
        val agentCreateResponse =
            AgentCreateResponse.builder()
                .agentName("agentName")
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .status(AgentStatus.PENDING)
                .build()

        val roundtrippedAgentCreateResponse =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(agentCreateResponse),
                jacksonTypeRef<AgentCreateResponse>(),
            )

        assertThat(roundtrippedAgentCreateResponse).isEqualTo(agentCreateResponse)
    }
}
