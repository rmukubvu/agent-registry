// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.client

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.core.getPackageVersion
import com.agentregistry.api.services.async.AgentServiceAsync
import com.agentregistry.api.services.async.AgentServiceAsyncImpl
import com.agentregistry.api.services.async.EnforceServiceAsync
import com.agentregistry.api.services.async.EnforceServiceAsyncImpl
import com.agentregistry.api.services.async.QServiceAsync
import com.agentregistry.api.services.async.QServiceAsyncImpl
import java.util.function.Consumer

class AgentregistryClientAsyncImpl(private val clientOptions: ClientOptions) :
    AgentregistryClientAsync {

    private val clientOptionsWithUserAgent =
        if (clientOptions.headers.names().contains("User-Agent")) clientOptions
        else
            clientOptions
                .toBuilder()
                .putHeader("User-Agent", "${javaClass.simpleName}/Java ${getPackageVersion()}")
                .build()

    // Pass the original clientOptions so that this client sets its own User-Agent.
    private val sync: AgentregistryClient by lazy { AgentregistryClientImpl(clientOptions) }

    private val withRawResponse: AgentregistryClientAsync.WithRawResponse by lazy {
        WithRawResponseImpl(clientOptions)
    }

    private val q: QServiceAsync by lazy { QServiceAsyncImpl(clientOptionsWithUserAgent) }

    private val agents: AgentServiceAsync by lazy {
        AgentServiceAsyncImpl(clientOptionsWithUserAgent)
    }

    private val enforce: EnforceServiceAsync by lazy {
        EnforceServiceAsyncImpl(clientOptionsWithUserAgent)
    }

    override fun sync(): AgentregistryClient = sync

    override fun withRawResponse(): AgentregistryClientAsync.WithRawResponse = withRawResponse

    override fun withOptions(modifier: Consumer<ClientOptions.Builder>): AgentregistryClientAsync =
        AgentregistryClientAsyncImpl(clientOptions.toBuilder().apply(modifier::accept).build())

    override fun q(): QServiceAsync = q

    /** Agent registration, governance, lifecycle, and verification. */
    override fun agents(): AgentServiceAsync = agents

    /** Runtime tool-call enforcement. */
    override fun enforce(): EnforceServiceAsync = enforce

    override fun close() = clientOptions.close()

    class WithRawResponseImpl internal constructor(private val clientOptions: ClientOptions) :
        AgentregistryClientAsync.WithRawResponse {

        private val q: QServiceAsync.WithRawResponse by lazy {
            QServiceAsyncImpl.WithRawResponseImpl(clientOptions)
        }

        private val agents: AgentServiceAsync.WithRawResponse by lazy {
            AgentServiceAsyncImpl.WithRawResponseImpl(clientOptions)
        }

        private val enforce: EnforceServiceAsync.WithRawResponse by lazy {
            EnforceServiceAsyncImpl.WithRawResponseImpl(clientOptions)
        }

        override fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): AgentregistryClientAsync.WithRawResponse =
            AgentregistryClientAsyncImpl.WithRawResponseImpl(
                clientOptions.toBuilder().apply(modifier::accept).build()
            )

        override fun q(): QServiceAsync.WithRawResponse = q

        /** Agent registration, governance, lifecycle, and verification. */
        override fun agents(): AgentServiceAsync.WithRawResponse = agents

        /** Runtime tool-call enforcement. */
        override fun enforce(): EnforceServiceAsync.WithRawResponse = enforce
    }
}
