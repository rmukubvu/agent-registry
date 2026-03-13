// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AgentSuspendParamsTest {

    @Test
    fun create() {
        AgentSuspendParams.builder()
            .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
            .idemKey("idemKey")
            .reason("reason")
            .build()
    }

    @Test
    fun pathParams() {
        val params =
            AgentSuspendParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .idemKey("idemKey")
                .reason("reason")
                .build()

        assertThat(params._pathParam(0)).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        // out-of-bound path param
        assertThat(params._pathParam(1)).isEqualTo("")
    }

    @Test
    fun body() {
        val params =
            AgentSuspendParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .idemKey("idemKey")
                .reason("reason")
                .build()

        val body = params._body()

        assertThat(body.idemKey()).isEqualTo("idemKey")
        assertThat(body.reason()).isEqualTo("reason")
    }
}
