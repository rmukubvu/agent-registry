// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.blocking

import com.agentregistry.api.client.okhttp.AgentregistryOkHttpClient
import com.agentregistry.api.core.JsonValue
import com.agentregistry.api.models.enforce.EnforceEvaluateParams
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class EnforceServiceTest {

    @Disabled("Mock server tests are disabled")
    @Test
    fun evaluate() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val enforceService = client.enforce()

        val response =
            enforceService.evaluate(
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
            )

        response.validate()
    }
}
