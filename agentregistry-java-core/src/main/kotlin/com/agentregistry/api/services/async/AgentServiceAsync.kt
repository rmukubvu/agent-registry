// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.services.async

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
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

/** Agent registration, governance, lifecycle, and verification. */
interface AgentServiceAsync {

    /**
     * Returns a view of this service that provides access to raw HTTP responses for each method.
     */
    fun withRawResponse(): WithRawResponse

    /**
     * Returns a view of this service with the given option modifications applied.
     *
     * The original service is not modified.
     */
    fun withOptions(modifier: Consumer<ClientOptions.Builder>): AgentServiceAsync

    /** Register a new agent */
    fun create(params: AgentCreateParams): CompletableFuture<AgentCreateResponse> =
        create(params, RequestOptions.none())

    /** @see create */
    fun create(
        params: AgentCreateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentCreateResponse>

    /** List all agent records */
    fun list(): CompletableFuture<List<AgentListResponse>> = list(AgentListParams.none())

    /** @see list */
    fun list(
        params: AgentListParams = AgentListParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<List<AgentListResponse>>

    /** @see list */
    fun list(
        params: AgentListParams = AgentListParams.none()
    ): CompletableFuture<List<AgentListResponse>> = list(params, RequestOptions.none())

    /** @see list */
    fun list(requestOptions: RequestOptions): CompletableFuture<List<AgentListResponse>> =
        list(AgentListParams.none(), requestOptions)

    /** Approve and activate an agent */
    fun activate(dnaId: String, params: AgentActivateParams): CompletableFuture<AgentTransition> =
        activate(dnaId, params, RequestOptions.none())

    /** @see activate */
    fun activate(
        dnaId: String,
        params: AgentActivateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentTransition> =
        activate(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see activate */
    fun activate(params: AgentActivateParams): CompletableFuture<AgentTransition> =
        activate(params, RequestOptions.none())

    /** @see activate */
    fun activate(
        params: AgentActivateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentTransition>

    /** Reinstate a suspended agent */
    fun reinstate(dnaId: String, params: AgentReinstateParams): CompletableFuture<AgentTransition> =
        reinstate(dnaId, params, RequestOptions.none())

    /** @see reinstate */
    fun reinstate(
        dnaId: String,
        params: AgentReinstateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentTransition> =
        reinstate(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see reinstate */
    fun reinstate(params: AgentReinstateParams): CompletableFuture<AgentTransition> =
        reinstate(params, RequestOptions.none())

    /** @see reinstate */
    fun reinstate(
        params: AgentReinstateParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentTransition>

    /** Revoke an agent permanently */
    fun revoke(dnaId: String, params: AgentRevokeParams): CompletableFuture<AgentTransition> =
        revoke(dnaId, params, RequestOptions.none())

    /** @see revoke */
    fun revoke(
        dnaId: String,
        params: AgentRevokeParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentTransition> =
        revoke(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see revoke */
    fun revoke(params: AgentRevokeParams): CompletableFuture<AgentTransition> =
        revoke(params, RequestOptions.none())

    /** @see revoke */
    fun revoke(
        params: AgentRevokeParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentTransition>

    /** Suspend an active agent */
    fun suspend(dnaId: String, params: AgentSuspendParams): CompletableFuture<AgentTransition> =
        suspend(dnaId, params, RequestOptions.none())

    /** @see suspend */
    fun suspend(
        dnaId: String,
        params: AgentSuspendParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentTransition> =
        suspend(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see suspend */
    fun suspend(params: AgentSuspendParams): CompletableFuture<AgentTransition> =
        suspend(params, RequestOptions.none())

    /** @see suspend */
    fun suspend(
        params: AgentSuspendParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentTransition>

    /** Verify whether an agent is currently authorized */
    fun verify(dnaId: String): CompletableFuture<AgentVerifyResponse> =
        verify(dnaId, AgentVerifyParams.none())

    /** @see verify */
    fun verify(
        dnaId: String,
        params: AgentVerifyParams = AgentVerifyParams.none(),
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentVerifyResponse> =
        verify(params.toBuilder().dnaId(dnaId).build(), requestOptions)

    /** @see verify */
    fun verify(
        dnaId: String,
        params: AgentVerifyParams = AgentVerifyParams.none(),
    ): CompletableFuture<AgentVerifyResponse> = verify(dnaId, params, RequestOptions.none())

    /** @see verify */
    fun verify(
        params: AgentVerifyParams,
        requestOptions: RequestOptions = RequestOptions.none(),
    ): CompletableFuture<AgentVerifyResponse>

    /** @see verify */
    fun verify(params: AgentVerifyParams): CompletableFuture<AgentVerifyResponse> =
        verify(params, RequestOptions.none())

    /** @see verify */
    fun verify(
        dnaId: String,
        requestOptions: RequestOptions,
    ): CompletableFuture<AgentVerifyResponse> =
        verify(dnaId, AgentVerifyParams.none(), requestOptions)

    /** A view of [AgentServiceAsync] that provides access to raw HTTP responses for each method. */
    interface WithRawResponse {

        /**
         * Returns a view of this service with the given option modifications applied.
         *
         * The original service is not modified.
         */
        fun withOptions(
            modifier: Consumer<ClientOptions.Builder>
        ): AgentServiceAsync.WithRawResponse

        /**
         * Returns a raw HTTP response for `post /v1/agents`, but is otherwise the same as
         * [AgentServiceAsync.create].
         */
        fun create(
            params: AgentCreateParams
        ): CompletableFuture<HttpResponseFor<AgentCreateResponse>> =
            create(params, RequestOptions.none())

        /** @see create */
        fun create(
            params: AgentCreateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentCreateResponse>>

        /**
         * Returns a raw HTTP response for `get /v1/agents`, but is otherwise the same as
         * [AgentServiceAsync.list].
         */
        fun list(): CompletableFuture<HttpResponseFor<List<AgentListResponse>>> =
            list(AgentListParams.none())

        /** @see list */
        fun list(
            params: AgentListParams = AgentListParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<List<AgentListResponse>>>

        /** @see list */
        fun list(
            params: AgentListParams = AgentListParams.none()
        ): CompletableFuture<HttpResponseFor<List<AgentListResponse>>> =
            list(params, RequestOptions.none())

        /** @see list */
        fun list(
            requestOptions: RequestOptions
        ): CompletableFuture<HttpResponseFor<List<AgentListResponse>>> =
            list(AgentListParams.none(), requestOptions)

        /**
         * Returns a raw HTTP response for `put /v1/agents/{dnaId}/activate`, but is otherwise the
         * same as [AgentServiceAsync.activate].
         */
        fun activate(
            dnaId: String,
            params: AgentActivateParams,
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            activate(dnaId, params, RequestOptions.none())

        /** @see activate */
        fun activate(
            dnaId: String,
            params: AgentActivateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            activate(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see activate */
        fun activate(
            params: AgentActivateParams
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            activate(params, RequestOptions.none())

        /** @see activate */
        fun activate(
            params: AgentActivateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentTransition>>

        /**
         * Returns a raw HTTP response for `put /v1/agents/{dnaId}/reinstate`, but is otherwise the
         * same as [AgentServiceAsync.reinstate].
         */
        fun reinstate(
            dnaId: String,
            params: AgentReinstateParams,
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            reinstate(dnaId, params, RequestOptions.none())

        /** @see reinstate */
        fun reinstate(
            dnaId: String,
            params: AgentReinstateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            reinstate(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see reinstate */
        fun reinstate(
            params: AgentReinstateParams
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            reinstate(params, RequestOptions.none())

        /** @see reinstate */
        fun reinstate(
            params: AgentReinstateParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentTransition>>

        /**
         * Returns a raw HTTP response for `put /v1/agents/{dnaId}/revoke`, but is otherwise the
         * same as [AgentServiceAsync.revoke].
         */
        fun revoke(
            dnaId: String,
            params: AgentRevokeParams,
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            revoke(dnaId, params, RequestOptions.none())

        /** @see revoke */
        fun revoke(
            dnaId: String,
            params: AgentRevokeParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            revoke(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see revoke */
        fun revoke(params: AgentRevokeParams): CompletableFuture<HttpResponseFor<AgentTransition>> =
            revoke(params, RequestOptions.none())

        /** @see revoke */
        fun revoke(
            params: AgentRevokeParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentTransition>>

        /**
         * Returns a raw HTTP response for `put /v1/agents/{dnaId}/suspend`, but is otherwise the
         * same as [AgentServiceAsync.suspend].
         */
        fun suspend(
            dnaId: String,
            params: AgentSuspendParams,
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            suspend(dnaId, params, RequestOptions.none())

        /** @see suspend */
        fun suspend(
            dnaId: String,
            params: AgentSuspendParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            suspend(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see suspend */
        fun suspend(
            params: AgentSuspendParams
        ): CompletableFuture<HttpResponseFor<AgentTransition>> =
            suspend(params, RequestOptions.none())

        /** @see suspend */
        fun suspend(
            params: AgentSuspendParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentTransition>>

        /**
         * Returns a raw HTTP response for `get /v1/agents/{dnaId}/verify`, but is otherwise the
         * same as [AgentServiceAsync.verify].
         */
        fun verify(dnaId: String): CompletableFuture<HttpResponseFor<AgentVerifyResponse>> =
            verify(dnaId, AgentVerifyParams.none())

        /** @see verify */
        fun verify(
            dnaId: String,
            params: AgentVerifyParams = AgentVerifyParams.none(),
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentVerifyResponse>> =
            verify(params.toBuilder().dnaId(dnaId).build(), requestOptions)

        /** @see verify */
        fun verify(
            dnaId: String,
            params: AgentVerifyParams = AgentVerifyParams.none(),
        ): CompletableFuture<HttpResponseFor<AgentVerifyResponse>> =
            verify(dnaId, params, RequestOptions.none())

        /** @see verify */
        fun verify(
            params: AgentVerifyParams,
            requestOptions: RequestOptions = RequestOptions.none(),
        ): CompletableFuture<HttpResponseFor<AgentVerifyResponse>>

        /** @see verify */
        fun verify(
            params: AgentVerifyParams
        ): CompletableFuture<HttpResponseFor<AgentVerifyResponse>> =
            verify(params, RequestOptions.none())

        /** @see verify */
        fun verify(
            dnaId: String,
            requestOptions: RequestOptions,
        ): CompletableFuture<HttpResponseFor<AgentVerifyResponse>> =
            verify(dnaId, AgentVerifyParams.none(), requestOptions)
    }
}
