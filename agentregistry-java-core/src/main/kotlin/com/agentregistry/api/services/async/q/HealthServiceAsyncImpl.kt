// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async.q

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.core.RequestOptions
import com.agentregistry.api.core.handlers.errorBodyHandler
import com.agentregistry.api.core.handlers.errorHandler
import com.agentregistry.api.core.handlers.jsonHandler
import com.agentregistry.api.core.http.HttpMethod
import com.agentregistry.api.core.http.HttpRequest
import com.agentregistry.api.core.http.HttpResponse
import com.agentregistry.api.core.http.HttpResponse.Handler
import com.agentregistry.api.core.http.HttpResponseFor
import com.agentregistry.api.core.http.parseable
import com.agentregistry.api.core.prepareAsync
import com.agentregistry.api.models.q.health.HealthCheckLiveParams
import com.agentregistry.api.models.q.health.HealthCheckReadyParams
import com.agentregistry.api.models.q.health.HealthStatus
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class HealthServiceAsyncImpl internal constructor(private val clientOptions: ClientOptions) :
    HealthServiceAsync {

    private val withRawResponse: HealthServiceAsync.WithRawResponse by lazy {
        WithRawResponseImpl(clientOptions)
    }

    override fun withRawResponse(): HealthServiceAsync.WithRawResponse = withRawResponse

    override fun withOptions(modifier: Consumer<ClientOptions.Builder>): HealthServiceAsync =
        HealthServiceAsyncImpl(clientOptions.toBuilder().apply(modifier::accept).build())

    override fun checkLive(
        params: HealthCheckLiveParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<HealthStatus> =
        // get /q/health/live
        withRawResponse().checkLive(params, requestOptions).thenApply { it.parse() }

    override fun checkReady(
        params: HealthCheckReadyParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<HealthStatus> =
        // get /q/health/ready
        withRawResponse().checkReady(params, requestOptions).thenApply { it.parse() }

    class WithRawResponseImpl internal constructor(private val clientOptions: ClientOptions) :
        HealthServiceAsync.WithRawResponse {

        private val errorHandler: Handler<HttpResponse> =
            errorHandler(errorBodyHandler(clientOptions.jsonMapper))

        override fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): HealthServiceAsync.WithRawResponse =
            HealthServiceAsyncImpl.WithRawResponseImpl(
                clientOptions.toBuilder().apply(modifier::accept).build()
            )

        private val checkLiveHandler: Handler<HealthStatus> =
            jsonHandler<HealthStatus>(clientOptions.jsonMapper)

        override fun checkLive(
            params: HealthCheckLiveParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<HealthStatus>> {
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.GET)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://enforcer-production.up.railway.app"
                    )
                    .addPathSegments("q", "health", "live")
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { checkLiveHandler.handle(it) }
                            .also {
                                if (requestOptions.responseValidation!!) {
                                    it.validate()
                                }
                            }
                    }
                }
        }

        private val checkReadyHandler: Handler<HealthStatus> =
            jsonHandler<HealthStatus>(clientOptions.jsonMapper)

        override fun checkReady(
            params: HealthCheckReadyParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<HealthStatus>> {
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.GET)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://registry-production-0f52.up.railway.app"
                    )
                    .addPathSegments("q", "health", "ready")
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { checkReadyHandler.handle(it) }
                            .also {
                                if (requestOptions.responseValidation!!) {
                                    it.validate()
                                }
                            }
                    }
                }
        }
    }
}
