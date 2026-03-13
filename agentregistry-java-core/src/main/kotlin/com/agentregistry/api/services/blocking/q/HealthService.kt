// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.blocking.q

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.core.RequestOptions
import com.agentregistry.api.core.http.HttpResponseFor
import com.agentregistry.api.models.q.health.HealthCheckLiveParams
import com.agentregistry.api.models.q.health.HealthCheckReadyParams
import com.agentregistry.api.models.q.health.HealthStatus
import com.google.errorprone.annotations.MustBeClosed
import java.util.function.Consumer

interface HealthService {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: Consumer<ClientOptions.Builder>): HealthService

    /** Enforcer liveness health check */
    fun checkLive(): HealthStatus = checkLive(HealthCheckLiveParams.none())

    /** @see checkLive */
    fun checkLive(
        params: HealthCheckLiveParams = HealthCheckLiveParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): HealthStatus

    /** @see checkLive */
    fun checkLive(params: HealthCheckLiveParams = HealthCheckLiveParams.none()): HealthStatus =
        checkLive(params, RequestOptions.none())

    /** @see checkLive */
    fun checkLive(requestOptions: RequestOptions): HealthStatus =
        checkLive(HealthCheckLiveParams.none(), requestOptions)

    /** Registry readiness health check */
    fun checkReady(): HealthStatus = checkReady(HealthCheckReadyParams.none())

    /** @see checkReady */
    fun checkReady(
        params: HealthCheckReadyParams = HealthCheckReadyParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): HealthStatus

    /** @see checkReady */
    fun checkReady(params: HealthCheckReadyParams = HealthCheckReadyParams.none()): HealthStatus =
        checkReady(params, RequestOptions.none())

    /** @see checkReady */
    fun checkReady(requestOptions: RequestOptions): HealthStatus =
        checkReady(HealthCheckReadyParams.none(), requestOptions)

    /** A view of [HealthService] that provides access to raw HTTP responses for each method. */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(modifier: Consumer<ClientOptions.Builder>): HealthService.WithRawResponse

        /**
         * Returns a raw HTTP response for `get /q/health/live`, but is otherwise the same as
         * [HealthService.checkLive].
         */
        @MustBeClosed
        fun checkLive(): HttpResponseFor<HealthStatus> = checkLive(HealthCheckLiveParams.none())

        /** @see checkLive */
        @MustBeClosed
        fun checkLive(
            params: HealthCheckLiveParams = HealthCheckLiveParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<HealthStatus>

        /** @see checkLive */
        @MustBeClosed
        fun checkLive(
            params: HealthCheckLiveParams = HealthCheckLiveParams.none()
        ): HttpResponseFor<HealthStatus> = checkLive(params, RequestOptions.none())

        /** @see checkLive */
        @MustBeClosed
        fun checkLive(requestOptions: RequestOptions): HttpResponseFor<HealthStatus> =
            checkLive(HealthCheckLiveParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `get /q/health/ready`, but is otherwise the same as
         * [HealthService.checkReady].
         */
        @MustBeClosed
        fun checkReady(): HttpResponseFor<HealthStatus> = checkReady(HealthCheckReadyParams.none())

        /** @see checkReady */
        @MustBeClosed
        fun checkReady(
            params: HealthCheckReadyParams = HealthCheckReadyParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<HealthStatus>

        /** @see checkReady */
        @MustBeClosed
        fun checkReady(
            params: HealthCheckReadyParams = HealthCheckReadyParams.none()
        ): HttpResponseFor<HealthStatus> = checkReady(params, RequestOptions.none())

        /** @see checkReady */
        @MustBeClosed
        fun checkReady(requestOptions: RequestOptions): HttpResponseFor<HealthStatus> =
            checkReady(HealthCheckReadyParams.none(), requestOptions)
    }
}
