// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.blocking

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.core.RequestOptions
import com.agentregistry.api.core.http.HttpResponseFor
import com.agentregistry.api.models.enforce.EnforceEvaluateParams
import com.agentregistry.api.models.enforce.EnforceEvaluateResponse
import com.google.errorprone.annotations.MustBeClosed
import java.util.function.Consumer

/** Runtime tool-call enforcement. */
interface EnforceService {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: Consumer<ClientOptions.Builder>): EnforceService

    /** Evaluate a tool call against Agent DNA policy */
    fun evaluate(params: EnforceEvaluateParams): EnforceEvaluateResponse =
        evaluate(params, RequestOptions.none())

    /** @see evaluate */
    fun evaluate(
        params: EnforceEvaluateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): EnforceEvaluateResponse

    /** A view of [EnforceService] that provides access to raw HTTP responses for each method. */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(modifier: Consumer<ClientOptions.Builder>): EnforceService.WithRawResponse

        /**
         * Returns a raw HTTP response for `post /v1/enforce`, but is otherwise the same as
         * [EnforceService.evaluate].
         */
        @MustBeClosed
        fun evaluate(params: EnforceEvaluateParams): HttpResponseFor<EnforceEvaluateResponse> =
            evaluate(params, RequestOptions.none())

        /** @see evaluate */
        @MustBeClosed
        fun evaluate(
            params: EnforceEvaluateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<EnforceEvaluateResponse>
    }
}
