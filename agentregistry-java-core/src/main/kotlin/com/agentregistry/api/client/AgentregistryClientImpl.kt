// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.client

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.core.getPackageVersion
import com.agentregistry.api.services.blocking.AgentService
import com.agentregistry.api.services.blocking.AgentServiceImpl
import com.agentregistry.api.services.blocking.EnforceService
import com.agentregistry.api.services.blocking.EnforceServiceImpl
import com.agentregistry.api.services.blocking.QService
import com.agentregistry.api.services.blocking.QServiceImpl
import java.util.function.Consumer

class AgentregistryClientImpl(private val clientOptions: ClientOptions) : AgentregistryClient {

    private val clientOptionsWithUserAgent =
        if (clientOptions.headers.names().contains("User-Agent")) clientOptions
        else
            clientOptions
                .toBuilder()
                .putHeader("User-Agent", "${javaClass.simpleName}/Java ${getPackageVersion()}")
                .build()

    // Pass the original clientOptions so that this client sets its own User-Agent.
    private val async: AgentregistryClientAsync by lazy {
        AgentregistryClientAsyncImpl(clientOptions)
    }

    private val withRawResponse: AgentregistryClient.WithRawResponse by lazy {
        WithRawResponseImpl(clientOptions)
    }

    private val q: QService by lazy { QServiceImpl(clientOptionsWithUserAgent) }

    private val agents: AgentService by lazy { AgentServiceImpl(clientOptionsWithUserAgent) }

    private val enforce: EnforceService by lazy { EnforceServiceImpl(clientOptionsWithUserAgent) }

    override fun async(): AgentregistryClientAsync = async

    override fun withRawResponse(): AgentregistryClient.WithRawResponse = withRawResponse

    override fun withOptions(modifier: Consumer<ClientOptions.Builder>): AgentregistryClient =
        AgentregistryClientImpl(clientOptions.toBuilder().apply(modifier::accept).build())

    override fun q(): QService = q

    /** Agent registration, governance, lifecycle, and verification. */
    override fun agents(): AgentService = agents

    /** Runtime tool-call enforcement. */
    override fun enforce(): EnforceService = enforce

    override fun close() = clientOptions.close()

    class WithRawResponseImpl internal constructor(private val clientOptions: ClientOptions) :
        AgentregistryClient.WithRawResponse {

        private val q: QService.WithRawResponse by lazy {
            QServiceImpl.WithRawResponseImpl(clientOptions)
        }

        private val agents: AgentService.WithRawResponse by lazy {
            AgentServiceImpl.WithRawResponseImpl(clientOptions)
        }

        private val enforce: EnforceService.WithRawResponse by lazy {
            EnforceServiceImpl.WithRawResponseImpl(clientOptions)
        }

        override fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): AgentregistryClient.WithRawResponse =
            AgentregistryClientImpl.WithRawResponseImpl(
                clientOptions.toBuilder().apply(modifier::accept).build()
            )

        override fun q(): QService.WithRawResponse = q

        /** Agent registration, governance, lifecycle, and verification. */
        override fun agents(): AgentService.WithRawResponse = agents

        /** Runtime tool-call enforcement. */
        override fun enforce(): EnforceService.WithRawResponse = enforce
    }
}
