// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.blocking

import com.agentregistry.api.client.okhttp.AgentregistryOkHttpClient
import com.agentregistry.api.models.agents.AgentActivateParams
import com.agentregistry.api.models.agents.AgentCreateParams
import com.agentregistry.api.models.agents.AgentReinstateParams
import com.agentregistry.api.models.agents.AgentRevokeParams
import com.agentregistry.api.models.agents.AgentSuspendParams
import java.time.OffsetDateTime
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class AgentServiceTest {

    @Disabled("Mock server tests are disabled")
    @Test
    fun create() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val agentService = client.agents()

        val agent =
            agentService.create(
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

        agent.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun list() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val agentService = client.agents()

        val agents = agentService.list()

        agents.forEach { it.validate() }
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun activate() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val agentService = client.agents()

        val agentTransition =
            agentService.activate(
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

        agentTransition.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun reinstate() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val agentService = client.agents()

        val agentTransition =
            agentService.reinstate(
                AgentReinstateParams.builder()
                    .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                    .idemKey("idemKey")
                    .build()
            )

        agentTransition.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun revoke() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val agentService = client.agents()

        val agentTransition =
            agentService.revoke(
                AgentRevokeParams.builder()
                    .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                    .idemKey("idemKey")
                    .reason("reason")
                    .build()
            )

        agentTransition.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun suspend() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val agentService = client.agents()

        val agentTransition =
            agentService.suspend(
                AgentSuspendParams.builder()
                    .dnaId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                    .idemKey("idemKey")
                    .reason("reason")
                    .build()
            )

        agentTransition.validate()
    }

    @Disabled("Mock server tests are disabled")
    @Test
    fun verify() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()
        val agentService = client.agents()

        val response = agentService.verify("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")

        response.validate()
    }
}
