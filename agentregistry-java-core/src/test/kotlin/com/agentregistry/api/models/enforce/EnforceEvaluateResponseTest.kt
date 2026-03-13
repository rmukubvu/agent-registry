// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.enforce

import com.agentregistry.api.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EnforceEvaluateResponseTest {

    @Test
    fun create() {
        val enforceEvaluateResponse =
            EnforceEvaluateResponse.builder()
                .allowed(true)
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .toolName("toolName")
                .agentName("agentName")
                .reason("reason")
                .build()

        assertThat(enforceEvaluateResponse.allowed()).isEqualTo(true)
        assertThat(enforceEvaluateResponse.dnaId())
            .isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(enforceEvaluateResponse.toolName()).isEqualTo("toolName")
        assertThat(enforceEvaluateResponse.agentName()).contains("agentName")
        assertThat(enforceEvaluateResponse.reason()).contains("reason")
    }

    @Test
    fun roundtrip() {
        val jsonMapper = jsonMapper()
        val enforceEvaluateResponse =
            EnforceEvaluateResponse.builder()
                .allowed(true)
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .toolName("toolName")
                .agentName("agentName")
                .reason("reason")
                .build()

        val roundtrippedEnforceEvaluateResponse =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(enforceEvaluateResponse),
                jacksonTypeRef<EnforceEvaluateResponse>(),
            )

        assertThat(roundtrippedEnforceEvaluateResponse).isEqualTo(enforceEvaluateResponse)
    }
}
