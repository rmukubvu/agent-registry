// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.blocking

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.services.blocking.q.HealthService
import com.agentregistry.api.services.blocking.q.HealthServiceImpl
import java.util.function.Consumer

class QServiceImpl internal constructor(private val clientOptions: ClientOptions) : QService {

    private val withRawResponse: QService.WithRawResponse by lazy {
        WithRawResponseImpl(clientOptions)
    }

    private val health: HealthService by lazy { HealthServiceImpl(clientOptions) }

    override fun withRawResponse(): QService.WithRawResponse = withRawResponse

    override fun withOptions(modifier: Consumer<ClientOptions.Builder>): QService =
        QServiceImpl(clientOptions.toBuilder().apply(modifier::accept).build())

    override fun health(): HealthService = health

    class WithRawResponseImpl internal constructor(private val clientOptions: ClientOptions) :
        QService.WithRawResponse {

        private val health: HealthService.WithRawResponse by lazy {
            HealthServiceImpl.WithRawResponseImpl(clientOptions)
        }

        override fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): QService.WithRawResponse =
            QServiceImpl.WithRawResponseImpl(
                clientOptions.toBuilder().apply(modifier::accept).build()
            )

        override fun health(): HealthService.WithRawResponse = health
    }
}
