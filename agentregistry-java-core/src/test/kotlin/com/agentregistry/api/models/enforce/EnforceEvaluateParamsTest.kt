// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.enforce

import com.agentregistry.api.core.JsonValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EnforceEvaluateParamsTest {

    @Test
    fun create() {
        EnforceEvaluateParams.builder()
            .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
            .toolName("toolName")
            .toolPayload(
                EnforceEvaluateParams.ToolPayload.builder()
                    .putAdditionalProperty("foo", JsonValue.from("bar"))
                    .build()
            )
            .signature("signature")
            .build()
    }

    @Test
    fun body() {
        val params =
            EnforceEvaluateParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .toolName("toolName")
                .toolPayload(
                    EnforceEvaluateParams.ToolPayload.builder()
                        .putAdditionalProperty("foo", JsonValue.from("bar"))
                        .build()
                )
                .signature("signature")
                .build()

        val body = params._body()

        assertThat(body.dnaId()).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(body.toolName()).isEqualTo("toolName")
        assertThat(body.toolPayload())
            .isEqualTo(
                EnforceEvaluateParams.ToolPayload.builder()
                    .putAdditionalProperty("foo", JsonValue.from("bar"))
                    .build()
            )
        assertThat(body.signature()).contains("signature")
    }

    @Test
    fun bodyWithoutOptionalFields() {
        val params =
            EnforceEvaluateParams.builder()
                .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .toolName("toolName")
                .toolPayload(
                    EnforceEvaluateParams.ToolPayload.builder()
                        .putAdditionalProperty("foo", JsonValue.from("bar"))
                        .build()
                )
                .build()

        val body = params._body()

        assertThat(body.dnaId()).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(body.toolName()).isEqualTo("toolName")
        assertThat(body.toolPayload())
            .isEqualTo(
                EnforceEvaluateParams.ToolPayload.builder()
                    .putAdditionalProperty("foo", JsonValue.from("bar"))
                    .build()
            )
    }
}
