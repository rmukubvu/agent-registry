// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async

import com.agentregistry.api.client.okhttp.AgentregistryOkHttpClientAsync
import com.agentregistry.api.models.agents.AgentActivateParams
import com.agentregistry.api.models.agents.AgentCreateParams
import com.agentregistry.api.models.agents.AgentReinstateParams
import com.agentregistry.api.models.agents.AgentRevokeParams
import com.agentregistry.api.models.agents.AgentSuspendParams
import java.time.OffsetDateTime
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class AgentServiceAsyncTest {

    @Disabled("Mock server tests are disabled")
    @Test
    fun create() {
        val client = AgentregistryOkHttpClientAsync.builder().apiKey("My API Key").build()
        val agentServiceAsync = client.agents()

        val agentFuture =
            agentServiceAsync.create(
                AgentCreateParams.builder()
                    .agentName("agentName")
                    .addCapability("string")
                    .idemKey("idemKey")
                    .jurisdiction("jurisdiction")
                    .ownerId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                    .ownerName("ownerName")
                    .publicKeyHex("publicKeyHex")
                    .expiresAt(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
                    .parentDnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                    .provenanceRef("provenanceRef")
                    .workloadIdentity("workloadIdentity")
                    .build()
            )

        val agent = agentFuture.get()
        agent.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun list() {
        val client = AgentregistryOkHttpClientAsync.builder().apiKey("My API Key").build()
        val agentServiceAsync = client.agents()

        val agentsFuture = agentServiceAsync.list()

        val agents = agentsFuture.get()
        agents.forEach { it.validate() }
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun activate() {
        val client = AgentregistryOkHttpClientAsync.builder().apiKey("My API Key").build()
        val agentServiceAsync = client.agents()

        val agentTransitionFuture =
            agentServiceAsync.activate(
                AgentActivateParams.builder()
                    .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                    .queryApprovalReason("approvalReason")
                    .queryApprovedBy("approvedBy")
                    .queryIdemKey("idemKey")
                    .bodyApprovalReason("approvalReason")
                    .bodyApprovedBy("approvedBy")
                    .bodyIdemKey("idemKey")
                    .build()
            )

        val agentTransition = agentTransitionFuture.get()
        agentTransition.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun reinstate() {
        val client = AgentregistryOkHttpClientAsync.builder().apiKey("My API Key").build()
        val agentServiceAsync = client.agents()

        val agentTransitionFuture =
            agentServiceAsync.reinstate(
                AgentReinstateParams.builder()
                    .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                    .idemKey("idemKey")
                    .build()
            )

        val agentTransition = agentTransitionFuture.get()
        agentTransition.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun revoke() {
        val client = AgentregistryOkHttpClientAsync.builder().apiKey("My API Key").build()
        val agentServiceAsync = client.agents()

        val agentTransitionFuture =
            agentServiceAsync.revoke(
                AgentRevokeParams.builder()
                    .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                    .idemKey("idemKey")
                    .reason("reason")
                    .build()
            )

        val agentTransition = agentTransitionFuture.get()
        agentTransition.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun suspend() {
        val client = AgentregistryOkHttpClientAsync.builder().apiKey("My API Key").build()
        val agentServiceAsync = client.agents()

        val agentTransitionFuture =
            agentServiceAsync.suspend(
                AgentSuspendParams.builder()
                    .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                    .idemKey("idemKey")
                    .reason("reason")
                    .build()
            )

        val agentTransition = agentTransitionFuture.get()
        agentTransition.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun verify() {
        val client = AgentregistryOkHttpClientAsync.builder().apiKey("My API Key").build()
        val agentServiceAsync = client.agents()

        val responseFuture = agentServiceAsync.verify("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")

        val response = responseFuture.get()
        response.validate()
    }
}
