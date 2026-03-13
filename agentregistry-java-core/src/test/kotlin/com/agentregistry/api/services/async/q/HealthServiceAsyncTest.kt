// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async.q

import com.agentregistry.api.client.okhttp.AgentregistryOkHttpClientAsync
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class HealthServiceAsyncTest {

    @Disabled("Mock server tests are disabled")
    @Test
    fun checkLive() {
        val client = AgentregistryOkHttpClientAsync.builder().apiKey("My API Key").build()
        val healthServiceAsync = client.q().health()

        val healthStatusFuture = healthServiceAsync.checkLive()

        val healthStatus = healthStatusFuture.get()
        healthStatus.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun checkReady() {
        val client = AgentregistryOkHttpClientAsync.builder().apiKey("My API Key").build()
        val healthServiceAsync = client.q().health()

        val healthStatusFuture = healthServiceAsync.checkReady()

        val healthStatus = healthStatusFuture.get()
        healthStatus.validate()
    }
}
