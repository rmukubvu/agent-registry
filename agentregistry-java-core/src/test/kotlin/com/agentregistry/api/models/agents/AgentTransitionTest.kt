// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import com.agentregistry.api.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AgentTransitionTest {

    @Test
    fun create() {
        val agentTransition =
            AgentTransition.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .newStatus(AgentStatus.PENDING)
                .previousStatus(AgentStatus.PENDING)
                .build()

        assertThat(agentTransition.dnaId()).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(agentTransition.newStatus()).isEqualTo(AgentStatus.PENDING)
        assertThat(agentTransition.previousStatus()).isEqualTo(AgentStatus.PENDING)
    }

    @Test
    fun roundtrip() {
        val jsonMapper = jsonMapper()
        val agentTransition =
            AgentTransition.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .newStatus(AgentStatus.PENDING)
                .previousStatus(AgentStatus.PENDING)
                .build()

        val roundtrippedAgentTransition =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(agentTransition),
                jacksonTypeRef<AgentTransition>(),
            )

        assertThat(roundtrippedAgentTransition).isEqualTo(agentTransition)
    }
}
