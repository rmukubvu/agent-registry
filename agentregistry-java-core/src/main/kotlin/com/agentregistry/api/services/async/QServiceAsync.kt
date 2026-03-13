// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.services.async.q.HealthServiceAsync
import java.util.function.Consumer

interface QServiceAsync {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: Consumer<ClientOptions.Builder>): QServiceAsync

    fun health(): HealthServiceAsync

    /** A view of [QServiceAsync] that provides access to raw HTTP responses for each method. */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(modifier: Consumer<ClientOptions.Builder>): QServiceAsync.WithRawResponse

        fun health(): HealthServiceAsync.WithRawResponse
    }
}
