// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.blocking.q

import com.agentregistry.api.client.okhttp.AgentregistryOkHttpClient
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class HealthServiceTest {

    @Disabled("Mock server tests are disabled")
    @Test
    fun checkLive() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val healthService = client.q().health()

        val healthStatus = healthService.checkLive()

        healthStatus.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun checkReady() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val healthService = client.q().health()

        val healthStatus = healthService.checkReady()

        healthStatus.validate()
    }
}
