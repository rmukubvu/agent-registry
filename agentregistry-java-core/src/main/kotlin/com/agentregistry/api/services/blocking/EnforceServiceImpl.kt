// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.blocking

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
import com.agentregistry.api.core.prepare
import com.agentregistry.api.models.enforce.EnforceEvaluateParams
import com.agentregistry.api.models.enforce.EnforceEvaluateResponse
import java.util.function.Consumer

/** Runtime tool-call enforcement. */
class EnforceServiceImpl internal constructor(private val clientOptions: ClientOptions) :
    EnforceService {

    private val withRawResponse: EnforceService.WithRawResponse by lazy {
        WithRawResponseImpl(clientOptions)
    }

    override fun withRawResponse(): EnforceService.WithRawResponse = withRawResponse

    override fun withOptions(modifier: Consumer<ClientOptions.Builder>): EnforceService =
        EnforceServiceImpl(clientOptions.toBuilder().apply(modifier::accept).build())

    override fun evaluate(
        params: EnforceEvaluateParams,
        requestOptions: RequestOptions,
    ): EnforceEvaluateResponse =
        // post /v1/enforce
        withRawResponse().evaluate(params, requestOptions).parse()

    class WithRawResponseImpl internal constructor(private val clientOptions: ClientOptions) :
        EnforceService.WithRawResponse {

        private val errorHandler: Handler<HttpResponse> =
            errorHandler(errorBodyHandler(clientOptions.jsonMapper))

        override fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): EnforceService.WithRawResponse =
            EnforceServiceImpl.WithRawResponseImpl(
                clientOptions.toBuilder().apply(modifier::accept).build()
            )

        private val evaluateHandler: Handler<EnforceEvaluateResponse> =
            jsonHandler<EnforceEvaluateResponse>(clientOptions.jsonMapper)

        override fun evaluate(
            params: EnforceEvaluateParams,
            requestOptions: RequestOptions,
        ): HttpResponseFor<EnforceEvaluateResponse> {
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
                    .prepare(clientOptions, params)
            val requestOptions = requestOptions.applyDefaults(RequestOptions.from(clientOptions))
            val response = clientOptions.httpClient.execute(request, requestOptions)
            return errorHandler.handle(response).parseable {
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
