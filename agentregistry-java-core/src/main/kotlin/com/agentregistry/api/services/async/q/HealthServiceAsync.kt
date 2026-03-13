// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async.q

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.core.RequestOptions
import com.agentregistry.api.core.http.HttpResponseFor
import com.agentregistry.api.models.q.health.HealthCheckLiveParams
import com.agentregistry.api.models.q.health.HealthCheckReadyParams
import com.agentregistry.api.models.q.health.HealthStatus
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

interface HealthServiceAsync {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: Consumer<ClientOptions.Builder>): HealthServiceAsync

    /** Enforcer liveness health check */
    fun checkLive(): CompletableFuture<HealthStatus> = checkLive(HealthCheckLiveParams.none())

    /** @see checkLive */
    fun checkLive(
        params: HealthCheckLiveParams = HealthCheckLiveParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<HealthStatus>

    /** @see checkLive */
    fun checkLive(
        params: HealthCheckLiveParams = HealthCheckLiveParams.none()
    ): CompletableFuture<HealthStatus> = checkLive(params, RequestOptions.none())

    /** @see checkLive */
    fun checkLive(requestOptions: RequestOptions): CompletableFuture<HealthStatus> =
        checkLive(HealthCheckLiveParams.none(), requestOptions)

    /** Registry readiness health check */
    fun checkReady(): CompletableFuture<HealthStatus> = checkReady(HealthCheckReadyParams.none())

    /** @see checkReady */
    fun checkReady(
        params: HealthCheckReadyParams = HealthCheckReadyParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<HealthStatus>

    /** @see checkReady */
    fun checkReady(
        params: HealthCheckReadyParams = HealthCheckReadyParams.none()
    ): CompletableFuture<HealthStatus> = checkReady(params, RequestOptions.none())

    /** @see checkReady */
    fun checkReady(requestOptions: RequestOptions): CompletableFuture<HealthStatus> =
        checkReady(HealthCheckReadyParams.none(), requestOptions)

    /**
     * A view of [HealthServiceAsync] that provides access to raw HTTP responses for each method.
     */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): HealthServiceAsync.WithRawResponse

        /**
         * Returns a raw HTTP response for `get /q/health/live`, but is otherwise the same as
         * [HealthServiceAsync.checkLive].
         */
        fun checkLive(): CompletableFuture<HttpResponseFor<HealthStatus>> =
            checkLive(HealthCheckLiveParams.none())

        /** @see checkLive */
        fun checkLive(
            params: HealthCheckLiveParams = HealthCheckLiveParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<HealthStatus>>

        /** @see checkLive */
        fun checkLive(
            params: HealthCheckLiveParams = HealthCheckLiveParams.none()
        ): CompletableFuture<HttpResponseFor<HealthStatus>> =
            checkLive(params, RequestOptions.none())

        /** @see checkLive */
        fun checkLive(
            requestOptions: RequestOptions
        ): CompletableFuture<HttpResponseFor<HealthStatus>> =
            checkLive(HealthCheckLiveParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `get /q/health/ready`, but is otherwise the same as
         * [HealthServiceAsync.checkReady].
         */
        fun checkReady(): CompletableFuture<HttpResponseFor<HealthStatus>> =
            checkReady(HealthCheckReadyParams.none())

        /** @see checkReady */
        fun checkReady(
            params: HealthCheckReadyParams = HealthCheckReadyParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<HealthStatus>>

        /** @see checkReady */
        fun checkReady(
            params: HealthCheckReadyParams = HealthCheckReadyParams.none()
        ): CompletableFuture<HttpResponseFor<HealthStatus>> =
            checkReady(params, RequestOptions.none())

        /** @see checkReady */
        fun checkReady(
            requestOptions: RequestOptions
        ): CompletableFuture<HttpResponseFor<HealthStatus>> =
            checkReady(HealthCheckReadyParams.none(), requestOptions)
    }
}
