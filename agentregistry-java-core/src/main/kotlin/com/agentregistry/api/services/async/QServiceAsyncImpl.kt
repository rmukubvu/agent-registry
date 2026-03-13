// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.services.async.q.HealthServiceAsync
import com.agentregistry.api.services.async.q.HealthServiceAsyncImpl
import java.util.function.Consumer

class QServiceAsyncImpl internal constructor(private val clientOptions: ClientOptions) :
    QServiceAsync {

    private val withRawResponse: QServiceAsync.WithRawResponse by lazy {
        WithRawResponseImpl(clientOptions)
    }

    private val health: HealthServiceAsync by lazy { HealthServiceAsyncImpl(clientOptions) }

    override fun withRawResponse(): QServiceAsync.WithRawResponse = withRawResponse

    override fun withOptions(modifier: Consumer<ClientOptions.Builder>): QServiceAsync =
        QServiceAsyncImpl(clientOptions.toBuilder().apply(modifier::accept).build())

    override fun health(): HealthServiceAsync = health

    class WithRawResponseImpl internal constructor(private val clientOptions: ClientOptions) :
        QServiceAsync.WithRawResponse {

        private val health: HealthServiceAsync.WithRawResponse by lazy {
            HealthServiceAsyncImpl.WithRawResponseImpl(clientOptions)
        }

        override fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): QServiceAsync.WithRawResponse =
            QServiceAsyncImpl.WithRawResponseImpl(
                clientOptions.toBuilder().apply(modifier::accept).build()
            )

        override fun health(): HealthServiceAsync.WithRawResponse = health
    }
}
