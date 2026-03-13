// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.blocking

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.services.blocking.q.HealthService
import java.util.function.Consumer

interface QService {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: Consumer<ClientOptions.Builder>): QService

    fun health(): HealthService

    /** A view of [QService] that provides access to raw HTTP responses for each method. */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(modifier: Consumer<ClientOptions.Builder>): QService.WithRawResponse

        fun health(): HealthService.WithRawResponse
    }
}
