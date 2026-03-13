// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import com.agentregistry.api.core.http.QueryParams
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AgentReinstateParamsTest {

    @Test
    fun create() {
        AgentReinstateParams.builder()
            .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
            .idemKey("idemKey")
            .build()
    }

    @Test
    fun pathParams() {
        val params =
            AgentReinstateParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .idemKey("idemKey")
                .build()

        assertThat(params._pathParam(0)).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        // out-of-bound path param
        assertThat(params._pathParam(1)).isEqualTo("")
    }

    @Test
    fun queryParams() {
        val params =
            AgentReinstateParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .idemKey("idemKey")
                .build()

        val queryParams = params._queryParams()

        assertThat(queryParams).isEqualTo(QueryParams.builder().put("idemKey", "idemKey").build())
    }
}
