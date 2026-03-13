// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async

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
import com.agentregistry.api.core.http.json
import com.agentregistry.api.core.http.parseable
import com.agentregistry.api.core.prepareAsync
import com.agentregistry.api.models.enforce.EnforceEvaluateParams
import com.agentregistry.api.models.enforce.EnforceEvaluateResponse
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

/** Runtime tool-call enforcement. */
class EnforceServiceAsyncImpl internal constructor(private val clientOptions: ClientOptions) :
    EnforceServiceAsync {

    private val withRawResponse: EnforceServiceAsync.WithRawResponse by lazy {
        WithRawResponseImpl(clientOptions)
    }

    override fun withRawResponse(): EnforceServiceAsync.WithRawResponse = withRawResponse

    override fun withOptions(modifier: Consumer<ClientOptions.Builder>): EnforceServiceAsync =
        EnforceServiceAsyncImpl(clientOptions.toBuilder().apply(modifier::accept).build())

    override fun evaluate(
        params: EnforceEvaluateParams,
        requestOptions: RequestOptions,
    ): CompletableFuture<EnforceEvaluateResponse> =
        // post /v1/enforce
        withRawResponse().evaluate(params, requestOptions).thenApply { it.parse() }

    class WithRawResponseImpl internal constructor(private val clientOptions: ClientOptions) :
        EnforceServiceAsync.WithRawResponse {

        private val errorHandler: Handler<HttpResponse> =
            errorHandler(errorBodyHandler(clientOptions.jsonMapper))

        override fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): EnforceServiceAsync.WithRawResponse =
            EnforceServiceAsyncImpl.WithRawResponseImpl(
                clientOptions.toBuilder().apply(modifier::accept).build()
            )

        private val evaluateHandler: Handler<EnforceEvaluateResponse> =
            jsonHandler<EnforceEvaluateResponse>(clientOptions.jsonMapper)

        override fun evaluate(
            params: EnforceEvaluateParams,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<EnforceEvaluateResponse>> {
            val request =
                HttpRequest.builder()
                    .method(HttpMethod.POST)
                    .baseUrl(
                        if (clientOptions.baseUrlOverridden()) clientOptions.baseUrl()
                        else "https://enforcer-production.up.railway.app"
                    )
                    .addPathSegments("v1", "enforce")
                    .body(json(clientOptions.jsonMapper, params._body()))
                    .build()
                    .prepareAsync(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            return request
                .thenComposeAsync { clientOptions.httpClient.executeAsync(it, requestOptions) }
                .thenApply { response ->
                    errorHandler.handle(response).parseable {
                        response
                            .use { evaluateHandler.handle(it) }
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
