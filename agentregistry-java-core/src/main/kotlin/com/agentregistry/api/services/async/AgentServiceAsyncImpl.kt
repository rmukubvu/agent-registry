// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.core.RequestOptions
import com.agentregistry.api.core.checkRequired
import com.agentregistry.api.core.handlers.errorBodyHandler
import com.agentregistry.api.core.handlers.errorHandler
import com.agentregistry.api.core.handlers.jsonHandler
import com.agentregistry.api.core.http.HttpMethod
import com.agentregistry.api.core.http.HttpRequest
import com.agentregistry.api.core.http.HttpResponse
import com.agentregistry.api.core.http.HttpResponse.Handler
import com.agentregistry.api.core.http.HttpResponseFor
import com.agentregistry.api.core.http.json
import com.agentregistry.api.core.http.parseable
import com.agentregistry.api.core.prepareAsync
import com.agentregistry.api.models.agents.AgentActivateParams
import com.agentregistry.api.models.agents.AgentCreateParams
import com.agentregistry.api.models.agents.AgentCreateResponse
import com.agentregistry.api.models.agents.AgentListParams
import com.agentregistry.api.models.agents.AgentListResponse
import com.agentregistry.api.models.agents.AgentReinstateParams
import com.agentregistry.api.models.agents.AgentRevokeParams
import com.agentregistry.api.models.agents.AgentSuspendParams
import com.agentregistry.api.models.agents.AgentTransition
import com.agentregistry.api.models.agents.AgentVerifyParams
import com.agentregistry.api.models.agents.AgentVerifyResponse
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import kotlin.jvm.optionals.getOrNull

/** Agent registration, governance, lifecycle, and verification. */
class AgentServiceAsyncImpl internal constructor(private val clientOptions: ClientOptions) :
    AgentServiceAsync {

    private val withRawResponse: AgentServiceAsync.WithRawResponse by lazy {
        WithRawResponseImpl(clientOptions)
    }

    override fun withRawResponse(): AgentServiceAsync.WithRawResponse = withRawResponse

    override fun withOptions(modifier: Consumer<ClientOptions.Builder>): AgentServiceAsync =
        AgentServiceAsyncImpl(clientOptions.toBuilder().apply(modifier::accept).build())

    override fun create(
        params: AgentCreateParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<AgentCreateResponse> =
        // post /v1/agents
        withRawResponse().create(params, requestOptions).thenApply { it.parse() }

    override fun list(
        params: AgentListParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<List<AgentListResponse>> =
        // get /v1/agents
        withRawResponse().list(params, requestOptions).thenApply { it.parse() }

    override fun activate(
        params: AgentActivateParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<AgentTransition> =
        // put /v1/agents/{dnaId}/activate
        withRawResponse().activate(params, requestOptions).thenApply { it.parse() }

    override fun reinstate(
        params: AgentReinstateParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<AgentTransition> =
        // put /v1/agents/{dnaId}/reinstate
        withRawResponse().reinstate(params, requestOptions).thenApply { it.parse() }

    override fun revoke(
        params: AgentRevokeParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<AgentTransition> =
        // put /v1/agents/{dnaId}/revoke
        withRawResponse().revoke(params, requestOptions).thenApply { it.parse() }

    override fun suspend(
        params: AgentSuspendParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<AgentTransition> =
        // put /v1/agents/{dnaId}/suspend
        withRawResponse().suspend(params, requestOptions).thenApply { it.parse() }

    override fun verify(
        params: AgentVerifyParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<AgentVerifyResponse> =
        // get /v1/agents/{dnaId}/verify
        withRawResponse().verify(params, requestOptions).thenApply { it.parse() }

    class WithRawResponseImpl internal constructor(private val clientOptions: ClientOptions) :
        AgentServiceAsync.WithRawResponse {

        private val errorHandler: Handler<HttpResponse> =
            errorHandler(errorBodyHandler(clientOptions.jsonMapper))

        override fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): AgentServiceAsync.WithRawResponse =
            AgentServiceAsyncImpl.WithRawResponseImpl(
                clientOptions.toBuilder().apply(modifier::accept).build()
            )

        private val createHandler: Handler<AgentCreateResponse> =
            jsonHandler<AgentCreateResponse>(clientOptions.jsonMapper)

        override fun create(
            params: AgentCreateParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<AgentCreateResponse>> {
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.POST)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://registry-production-0f52.up.railway.app"
                    )
                    .addPathSegments("v1", "agents")
                    .body(json(clientOptions.jsonMapper, params._body()))
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { createHandler.handle(it) }
                            .also {
                                if (requestOptions.responseValidation!!) {
                                    it.validate()
                                }
                            }
                    }
                }
        }

        private val listHandler: Handler<List<AgentListResponse>> =
            jsonHandler<List<AgentListResponse>>(clientOptions.jsonMapper)

        override fun list(
            params: AgentListParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<List<AgentListResponse>>> {
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.GET)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://registry-production-0f52.up.railway.app"
                    )
                    .addPathSegments("v1", "agents")
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { listHandler.handle(it) }
                            .also {
                                if (requestOptions.responseValidation!!) {
                                    it.forEach { it.validate() }
                                }
                            }
                    }
                }
        }

        private val activateHandler: Handler<AgentTransition> =
            jsonHandler<AgentTransition>(clientOptions.jsonMapper)

        override fun activate(
            params: AgentActivateParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<AgentTransition>> {
            // We check here instead of in the params builder because this can be specified
            // positionally or in the params class.
            checkRequired("dnaId", params.dnaId().getOrNull())
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.PUT)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://registry-production-0f52.up.railway.app"
                    )
                    .addPathSegments("v1", "agents", params._pathParam(0), "activate")
                    .body(json(clientOptions.jsonMapper, params._body()))
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { activateHandler.handle(it) }
                            .also {
                                if (requestOptions.responseValidation!!) {
                                    it.validate()
                                }
                            }
                    }
                }
        }

        private val reinstateHandler: Handler<AgentTransition> =
            jsonHandler<AgentTransition>(clientOptions.jsonMapper)

        override fun reinstate(
            params: AgentReinstateParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<AgentTransition>> {
            // We check here instead of in the params builder because this can be specified
            // positionally or in the params class.
            checkRequired("dnaId", params.dnaId().getOrNull())
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.PUT)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://registry-production-0f52.up.railway.app"
                    )
                    .addPathSegments("v1", "agents", params._pathParam(0), "reinstate")
                    .apply { params._body().ifPresent { body(json(clientOptions.jsonMapper, it)) } }
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { reinstateHandler.handle(it) }
                            .also {
                                if (requestOptions.responseValidation!!) {
                                    it.validate()
                                }
                            }
                    }
                }
        }

        private val revokeHandler: Handler<AgentTransition> =
            jsonHandler<AgentTransition>(clientOptions.jsonMapper)

        override fun revoke(
            params: AgentRevokeParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<AgentTransition>> {
            // We check here instead of in the params builder because this can be specified
            // positionally or in the params class.
            checkRequired("dnaId", params.dnaId().getOrNull())
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.PUT)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://registry-production-0f52.up.railway.app"
                    )
                    .addPathSegments("v1", "agents", params._pathParam(0), "revoke")
                    .body(json(clientOptions.jsonMapper, params._body()))
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { revokeHandler.handle(it) }
                            .also {
                                if (requestOptions.responseValidation!!) {
                                    it.validate()
                                }
                            }
                    }
                }
        }

        private val suspendHandler: Handler<AgentTransition> =
            jsonHandler<AgentTransition>(clientOptions.jsonMapper)

        override fun suspend(
            params: AgentSuspendParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<AgentTransition>> {
            // We check here instead of in the params builder because this can be specified
            // positionally or in the params class.
            checkRequired("dnaId", params.dnaId().getOrNull())
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.PUT)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://registry-production-0f52.up.railway.app"
                    )
                    .addPathSegments("v1", "agents", params._pathParam(0), "suspend")
                    .body(json(clientOptions.jsonMapper, params._body()))
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { suspendHandler.handle(it) }
                            .also {
                                if (requestOptions.responseValidation!!) {
                                    it.validate()
                                }
                            }
                    }
                }
        }

        private val verifyHandler: Handler<AgentVerifyResponse> =
            jsonHandler<AgentVerifyResponse>(clientOptions.jsonMapper)

        override fun verify(
            params: AgentVerifyParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<AgentVerifyResponse>> {
            // We check here instead of in the params builder because this can be specified
            // positionally or in the params class.
            checkRequired("dnaId", params.dnaId().getOrNull())
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.GET)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://registry-production-0f52.up.railway.app"
                    )
                    .addPathSegments("v1", "agents", params._pathParam(0), "verify")
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { verifyHandler.handle(it) }
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
