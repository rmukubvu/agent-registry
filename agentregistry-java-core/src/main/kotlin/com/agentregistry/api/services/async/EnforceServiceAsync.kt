// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.core.RequestOptions
import com.agentregistry.api.core.http.HttpResponseFor
import com.agentregistry.api.models.enforce.EnforceEvaluateParams
import com.agentregistry.api.models.enforce.EnforceEvaluateResponse
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

/** Runtime tool-call enforcement. */
interface EnforceServiceAsync {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: Consumer<ClientOptions.Builder>): EnforceServiceAsync

    /** Evaluate a tool call against Agent DNA policy */
    fun evaluate(params: EnforceEvaluateParams): CompletableFuture<EnforceEvaluateResponse> =
        evaluate(params, RequestOptions.none())

    /** @see evaluate */
    fun evaluate(
        params: EnforceEvaluateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<EnforceEvaluateResponse>

    /**
     * A view of [EnforceServiceAsync] that provides access to raw HTTP responses for each method.
     */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): EnforceServiceAsync.WithRawResponse

        /**
         * Returns a raw HTTP response for `post /v1/enforce`, but is otherwise the same as
         * [EnforceServiceAsync.evaluate].
         */
        fun evaluate(
            params: EnforceEvaluateParams
        ): CompletableFuture<HttpResponseFor<EnforceEvaluateResponse>> =
            evaluate(params, RequestOptions.none())

        /** @see evaluate */
        fun evaluate(
            params: EnforceEvaluateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<EnforceEvaluateResponse>>
    }
}
