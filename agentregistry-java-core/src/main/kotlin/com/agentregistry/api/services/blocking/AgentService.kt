// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.blocking

import com.agentregistry.api.core.ClientOptions
import com.agentregistry.api.core.RequestOptions
import com.agentregistry.api.core.http.HttpResponseFor
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
import com.google.errorprone.annotations.MustBeClosed
import java.util.function.Consumer

/** Agent registration, governance, lifecycle, and verification. */
interface AgentService {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: Consumer<ClientOptions.Builder>): AgentService

    /** Register a new agent */
    fun create(params: AgentCreateParams): AgentCreateResponse =
        create(params, RequestOptions.none())

    /** @see create */
    fun create(
        params: AgentCreateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentCreateResponse

    /** List all agent records */
    fun list(): List<AgentListResponse> = list(AgentListParams.none())

    /** @see list */
    fun list(
        params: AgentListParams = AgentListParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): List<AgentListResponse>

    /** @see list */
    fun list(params: AgentListParams = AgentListParams.none()): List<AgentListResponse> =
        list(params, RequestOptions.none())

    /** @see list */
    fun list(requestOptions: RequestOptions): List<AgentListResponse> =
        list(AgentListParams.none(), requestOptions)

    /** Approve and activate an agent */
    fun activate(dnaId: String, params: AgentActivateParams): AgentTransition =
        activate(dnaId, params, RequestOptions.none())

    /** @see activate */
    fun activate(
        dnaId: String,
        params: AgentActivateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentTransition = activate(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see activate */
    fun activate(params: AgentActivateParams): AgentTransition =
        activate(params, RequestOptions.none())

    /** @see activate */
    fun activate(
        params: AgentActivateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentTransition

    /** Reinstate a suspended agent */
    fun reinstate(dnaId: String, params: AgentReinstateParams): AgentTransition =
        reinstate(dnaId, params, RequestOptions.none())

    /** @see reinstate */
    fun reinstate(
        dnaId: String,
        params: AgentReinstateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentTransition = reinstate(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see reinstate */
    fun reinstate(params: AgentReinstateParams): AgentTransition =
        reinstate(params, RequestOptions.none())

    /** @see reinstate */
    fun reinstate(
        params: AgentReinstateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentTransition

    /** Revoke an agent permanently */
    fun revoke(dnaId: String, params: AgentRevokeParams): AgentTransition =
        revoke(dnaId, params, RequestOptions.none())

    /** @see revoke */
    fun revoke(
        dnaId: String,
        params: AgentRevokeParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentTransition = revoke(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see revoke */
    fun revoke(params: AgentRevokeParams): AgentTransition = revoke(params, RequestOptions.none())

    /** @see revoke */
    fun revoke(
        params: AgentRevokeParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentTransition

    /** Suspend an active agent */
    fun suspend(dnaId: String, params: AgentSuspendParams): AgentTransition =
        suspend(dnaId, params, RequestOptions.none())

    /** @see suspend */
    fun suspend(
        dnaId: String,
        params: AgentSuspendParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentTransition = suspend(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see suspend */
    fun suspend(params: AgentSuspendParams): AgentTransition =
        suspend(params, RequestOptions.none())

    /** @see suspend */
    fun suspend(
        params: AgentSuspendParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentTransition

    /** Verify whether an agent is currently authorized */
    fun verify(dnaId: String): AgentVerifyResponse = verify(dnaId, AgentVerifyParams.none())

    /** @see verify */
    fun verify(
        dnaId: String,
        params: AgentVerifyParams = AgentVerifyParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentVerifyResponse = verify(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see verify */
    fun verify(
        dnaId: String,
        params: AgentVerifyParams = AgentVerifyParams.none(),
    ): AgentVerifyResponse = verify(dnaId, params, RequestOptions.none())

    /** @see verify */
    fun verify(
        params: AgentVerifyParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): AgentVerifyResponse

    /** @see verify */
    fun verify(params: AgentVerifyParams): AgentVerifyResponse =
        verify(params, RequestOptions.none())

    /** @see verify */
    fun verify(dnaId: String, requestOptions: RequestOptions): AgentVerifyResponse =
        verify(dnaId, AgentVerifyParams.none(), requestOptions)

    /** A view of [AgentService] that provides access to raw HTTP responses for each method. */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(modifier: Consumer<ClientOptions.Builder>): AgentService.WithRawResponse

        /**
         * Returns a raw HTTP response for `post /v1/agents`, but is otherwise the same as
         * [AgentService.create].
         */
        @MustBeClosed
        fun create(params: AgentCreateParams): HttpResponseFor<AgentCreateResponse> =
            create(params, RequestOptions.none())

        /** @see create */
        @MustBeClosed
        fun create(
            params: AgentCreateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentCreateResponse>

        /**
         * Returns a raw HTTP response for `get /v1/agents`, but is otherwise the same as
         * [AgentService.list].
         */
        @MustBeClosed
        fun list(): HttpResponseFor<List<AgentListResponse>> = list(AgentListParams.none())

        /** @see list */
        @MustBeClosed
        fun list(
            params: AgentListParams = AgentListParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<List<AgentListResponse>>

        /** @see list */
        @MustBeClosed
        fun list(
            params: AgentListParams = AgentListParams.none()
        ): HttpResponseFor<List<AgentListResponse>> = list(params, RequestOptions.none())

        /** @see list */
        @MustBeClosed
        fun list(requestOptions: RequestOptions): HttpResponseFor<List<AgentListResponse>> =
            list(AgentListParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `put /v1/agents/{dnaId}/activate`, but is otherwise the
         * same as [AgentService.activate].
         */
        @MustBeClosed
        fun activate(dnaId: String, params: AgentActivateParams): HttpResponseFor<AgentTransition> =
            activate(dnaId, params, RequestOptions.none())

        /** @see activate */
        @MustBeClosed
        fun activate(
            dnaId: String,
            params: AgentActivateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentTransition> =
            activate(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see activate */
        @MustBeClosed
        fun activate(params: AgentActivateParams): HttpResponseFor<AgentTransition> =
            activate(params, RequestOptions.none())

        /** @see activate */
        @MustBeClosed
        fun activate(
            params: AgentActivateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentTransition>

        /**
         * Returns a raw HTTP response for `put /v1/agents/{dnaId}/reinstate`, but is otherwise the
         * same as [AgentService.reinstate].
         */
        @MustBeClosed
        fun reinstate(
            dnaId: String,
            params: AgentReinstateParams,
        ): HttpResponseFor<AgentTransition> = reinstate(dnaId, params, RequestOptions.none())

        /** @see reinstate */
        @MustBeClosed
        fun reinstate(
            dnaId: String,
            params: AgentReinstateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentTransition> =
            reinstate(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see reinstate */
        @MustBeClosed
        fun reinstate(params: AgentReinstateParams): HttpResponseFor<AgentTransition> =
            reinstate(params, RequestOptions.none())

        /** @see reinstate */
        @MustBeClosed
        fun reinstate(
            params: AgentReinstateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentTransition>

        /**
         * Returns a raw HTTP response for `put /v1/agents/{dnaId}/revoke`, but is otherwise the
         * same as [AgentService.revoke].
         */
        @MustBeClosed
        fun revoke(dnaId: String, params: AgentRevokeParams): HttpResponseFor<AgentTransition> =
            revoke(dnaId, params, RequestOptions.none())

        /** @see revoke */
        @MustBeClosed
        fun revoke(
            dnaId: String,
            params: AgentRevokeParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentTransition> =
            revoke(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see revoke */
        @MustBeClosed
        fun revoke(params: AgentRevokeParams): HttpResponseFor<AgentTransition> =
            revoke(params, RequestOptions.none())

        /** @see revoke */
        @MustBeClosed
        fun revoke(
            params: AgentRevokeParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentTransition>

        /**
         * Returns a raw HTTP response for `put /v1/agents/{dnaId}/suspend`, but is otherwise the
         * same as [AgentService.suspend].
         */
        @MustBeClosed
        fun suspend(dnaId: String, params: AgentSuspendParams): HttpResponseFor<AgentTransition> =
            suspend(dnaId, params, RequestOptions.none())

        /** @see suspend */
        @MustBeClosed
        fun suspend(
            dnaId: String,
            params: AgentSuspendParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentTransition> =
            suspend(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see suspend */
        @MustBeClosed
        fun suspend(params: AgentSuspendParams): HttpResponseFor<AgentTransition> =
            suspend(params, RequestOptions.none())

        /** @see suspend */
        @MustBeClosed
        fun suspend(
            params: AgentSuspendParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentTransition>

        /**
         * Returns a raw HTTP response for `get /v1/agents/{dnaId}/verify`, but is otherwise the
         * same as [AgentService.verify].
         */
        @MustBeClosed
        fun verify(dnaId: String): HttpResponseFor<AgentVerifyResponse> =
            verify(dnaId, AgentVerifyParams.none())

        /** @see verify */
        @MustBeClosed
        fun verify(
            dnaId: String,
            params: AgentVerifyParams = AgentVerifyParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentVerifyResponse> =
            verify(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see verify */
        @MustBeClosed
        fun verify(
            dnaId: String,
            params: AgentVerifyParams = AgentVerifyParams.none(),
        ): HttpResponseFor<AgentVerifyResponse> = verify(dnaId, params, RequestOptions.none())

        /** @see verify */
        @MustBeClosed
        fun verify(
            params: AgentVerifyParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): HttpResponseFor<AgentVerifyResponse>

        /** @see verify */
        @MustBeClosed
        fun verify(params: AgentVerifyParams): HttpResponseFor<AgentVerifyResponse> =
            verify(params, RequestOptions.none())

        /** @see verify */
        @MustBeClosed
        fun verify(
            dnaId: String,
            requestOptions: RequestOptions,
        ): HttpResponseFor<AgentVerifyResponse> =
            verify(dnaId, AgentVerifyParams.none(), requestOptions)
    }
}
