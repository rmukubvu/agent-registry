// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.proguard

import com.agentregistry.api.client.okhttp.AgentregistryOkHttpClient
import com.agentregistry.api.core.JsonValue
import com.agentregistry.api.core.jsonMapper
import com.agentregistry.api.models.agents.AgentStatus
import com.agentregistry.api.models.q.health.HealthStatus
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.javaMethod
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ProGuardCompatibilityTest {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            // To debug that we're using the right JAR.
            val jarPath = this::class.java.getProtectionDomain().codeSource.location
            println("JAR being used: $jarPath")

            // We have to manually run the test methods instead of using the JUnit runner because it
            // seems impossible to get working with R8.
            val test = ProGuardCompatibilityTest()
            test::class
                .memberFunctions
                .asSequence()
                .filter { function ->
                    function.javaMethod?.isAnnotationPresent(Test::class.java) == true
                }
                .forEach { it.call(test) }
        }
    }

    @Test
    fun proguardRules() {
        val rulesFile =
            javaClass.classLoader.getResourceAsStream(
                "META-INF/proguard/agentregistry-java-core.pro"
            )

        assertThat(rulesFile).isNotNull()
    }

    @Test
    fun client() {
        val client = AgentregistryOkHttpClient.builder().apiKey("My API Key").build()

        assertThat(client).isNotNull()
        assertThat(client.q()).isNotNull()
        assertThat(client.agents()).isNotNull()
        assertThat(client.enforce()).isNotNull()
    }

    @Test
    fun healthStatusRoundtrip() {
        val jsonMapper = jsonMapper()
        val healthStatus =
            HealthStatus.builder()
                .addCheck(
                    HealthStatus.Check.builder()
                        .putAdditionalProperty("foo", JsonValue.from("bar"))
                        .build()
                )
                .status("status")
                .build()

        val roundtrippedHealthStatus =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(healthStatus),
                jacksonTypeRef<HealthStatus>(),
            )

        assertThat(roundtrippedHealthStatus).isEqualTo(healthStatus)
    }

    @Test
    fun agentStatusRoundtrip() {
        val jsonMapper = jsonMapper()
        val agentStatus = AgentStatus.PENDING

        val roundtrippedAgentStatus =
            jsonMapper.readValue(
                jsonMapper.writeValueAsString(agentStatus),
                jacksonTypeRef<AgentStatus>(),
            )

        assertThat(roundtrippedAgentStatus).isEqualTo(agentStatus)
    }
}
