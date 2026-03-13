// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import java.time.OffsetDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AgentCreateParamsTest {

    @Test
    fun create() {
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
    }

    @Test
    fun body() {
        val params =
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

        val body = params._body()

        assertThat(body.agentName()).isEqualTo("agentName")
        assertThat(body.capabilities()).containsExactly("string")
        assertThat(body.idemKey()).isEqualTo("idemKey")
        assertThat(body.jurisdiction()).isEqualTo("jurisdiction")
        assertThat(body.ownerId()).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(body.ownerName()).isEqualTo("ownerName")
        assertThat(body.publicKeyHex()).isEqualTo("publicKeyHex")
        assertThat(body.expiresAt()).contains(OffsetDateTime.parse("2019-12-27T18:11:19.117Z"))
        assertThat(body.parentDnaId()).contains("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(body.provenanceRef()).contains("provenanceRef")
        assertThat(body.workloadIdentity()).contains("workloadIdentity")
    }

    @Test
    fun bodyWithoutOptionalFields() {
        val params =
            AgentCreateParams.builder()
                .agentName("agentName")
                .addCapability("string")
                .idemKey("idemKey")
                .jurisdiction("jurisdiction")
                .ownerId("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
                .ownerName("ownerName")
                .publicKeyHex("publicKeyHex")
                .build()

        val body = params._body()

        assertThat(body.agentName()).isEqualTo("agentName")
        assertThat(body.capabilities()).containsExactly("string")
        assertThat(body.idemKey()).isEqualTo("idemKey")
        assertThat(body.jurisdiction()).isEqualTo("jurisdiction")
        assertThat(body.ownerId()).isEqualTo("182bd5e5-6e1a-4fe4-a799-aa6d9a6ab26e")
        assertThat(body.ownerName()).isEqualTo("ownerName")
        assertThat(body.publicKeyHex()).isEqualTo("publicKeyHex")
    }
}
