// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.q.health

import com.agentregistry.api.core.JsonValue
import com.agentregistry.api.core.jsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import kotlin.jvm.optionals.getOrNull
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HealthStatusTest {

    @Test
    fun create() {
        val healthStatus =
            HealthStatus.builder()
                .addCheck(
                    HealthStatus.Check.builder()
                        .putAdditionalProperty("foo", JsonValue.from("bar"))
                        .build()
                )
                .status("status")
                .build()

        assertThat(healthStatus.checks().getOrNull())
            .containsExactly(
                HealthStatus.Check.builder()
                    .putAdditionalProperty("foo", JsonValue.from("bar"))
                    .build()
            )
        assertThat(healthStatus.status()).contains("status")
    }

    @Test
    fun roundtrip() {
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
}
