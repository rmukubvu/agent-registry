// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import com.agentregistry.api.core.ExcludeMissing
import com.agentregistry.api.core.JsonField
import com.agentregistry.api.core.JsonMissing
import com.agentregistry.api.core.JsonValue
import com.agentregistry.api.core.Params
import com.agentregistry.api.core.checkRequired
import com.agentregistry.api.core.http.Headers
import com.agentregistry.api.core.http.QueryParams
import com.agentregistry.api.errors.AgentregistryInvalidDataException
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Collections
import java.util.Objects
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

/** Approve and activate an agent */
class AgentActivateParams
private constructor(
    private val dnaId: String?,
    private val queryApprovalReason: String?,
    private val queryApprovedBy: String?,
    private val queryIdemKey: String?,
    private val body: Body,
    private val additionalHeaders: Headers,
    private val additionalQueryParams: QueryParams,
) : Params {

    fun dnaId(): Optional<String> = Optional.ofNullable(dnaId)

    fun queryApprovalReason(): Optional<String> = Optional.ofNullable(queryApprovalReason)

    fun queryApprovedBy(): Optional<String> = Optional.ofNullable(queryApprovedBy)

    fun queryIdemKey(): Optional<String> = Optional.ofNullable(queryIdemKey)

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun bodyApprovalReason(): String = body.bodyApprovalReason()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun bodyApprovedBy(): String = body.bodyApprovedBy()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun bodyIdemKey(): String = body.bodyIdemKey()

    /**
     * Returns the raw JSON value of [bodyApprovalReason].
     *
     * Unlike [bodyApprovalReason], this method doesn't throw if the JSON field has an unexpected
     * type.
     */
    fun _bodyApprovalReason(): JsonField<String> = body._bodyApprovalReason()

    /**
     * Returns the raw JSON value of [bodyApprovedBy].
     *
     * Unlike [bodyApprovedBy], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _bodyApprovedBy(): JsonField<String> = body._bodyApprovedBy()

    /**
     * Returns the raw JSON value of [bodyIdemKey].
     *
     * Unlike [bodyIdemKey], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _bodyIdemKey(): JsonField<String> = body._bodyIdemKey()

    fun _additionalBodyProperties(): Map<String, JsonValue> = body._additionalProperties()

    /** Additional headers to send with the request. */
    fun _additionalHeaders(): Headers = additionalHeaders

    /** Additional query param to send with the request. */
    fun _additionalQueryParams(): QueryParams = additionalQueryParams

    fun toBuilder() = Builder().from(this)

    companion object {

        /**
         * Returns a mutable builder for constructing an instance of [AgentActivateParams].
         *
         * The following fields are required:
         * ```java
         * .bodyApprovalReason()
         * .bodyApprovedBy()
         * .bodyIdemKey()
         * ```
         */
        @JvmStatic fun builder() = Builder()
    }

    /** A builder for [AgentActivateParams]. */
    class Builder internal constructor() {

        private var dnaId: String? = null
        private var queryApprovalReason: String? = null
        private var queryApprovedBy: String? = null
        private var queryIdemKey: String? = null
        private var body: Body.Builder = Body.builder()
        private var additionalHeaders: Headers.Builder = Headers.builder()
        private var additionalQueryParams: QueryParams.Builder = QueryParams.builder()

        @JvmSynthetic
        internal fun from(agentActivateParams: AgentActivateParams) = apply {
            dnaId = agentActivateParams.dnaId
            queryApprovalReason = agentActivateParams.queryApprovalReason
            queryApprovedBy = agentActivateParams.queryApprovedBy
            queryIdemKey = agentActivateParams.queryIdemKey
            body = agentActivateParams.body.toBuilder()
            additionalHeaders = agentActivateParams.additionalHeaders.toBuilder()
            additionalQueryParams = agentActivateParams.additionalQueryParams.toBuilder()
        }

        fun dnaId(dnaId: String?) = apply { this.dnaId = dnaId }

        /** Alias for calling [Builder.dnaId] with `dnaId.orElse(null)`. */
        fun dnaId(dnaId: Optional<String>) = dnaId(dnaId.getOrNull())

        fun queryApprovalReason(queryApprovalReason: String?) = apply {
            this.queryApprovalReason = queryApprovalReason
        }

        /**
         * Alias for calling [Builder.queryApprovalReason] with `queryApprovalReason.orElse(null)`.
         */
        fun queryApprovalReason(queryApprovalReason: Optional<String>) =
            queryApprovalReason(queryApprovalReason.getOrNull())

        fun queryApprovedBy(queryApprovedBy: String?) = apply {
            this.queryApprovedBy = queryApprovedBy
        }

        /** Alias for calling [Builder.queryApprovedBy] with `queryApprovedBy.orElse(null)`. */
        fun queryApprovedBy(queryApprovedBy: Optional<String>) =
            queryApprovedBy(queryApprovedBy.getOrNull())

        fun queryIdemKey(queryIdemKey: String?) = apply { this.queryIdemKey = queryIdemKey }

        /** Alias for calling [Builder.queryIdemKey] with `queryIdemKey.orElse(null)`. */
        fun queryIdemKey(queryIdemKey: Optional<String>) = queryIdemKey(queryIdemKey.getOrNull())

        /**
         * Sets the entire request body.
         *
         * This is generally only useful if you are already constructing the body separately.
         * Otherwise, it's more convenient to use the top-level setters instead:
         * - [bodyApprovalReason]
         * - [bodyApprovedBy]
         * - [bodyIdemKey]
         */
        fun body(body: Body) = apply { this.body = body.toBuilder() }

        fun bodyApprovalReason(bodyApprovalReason: String) = apply {
            body.bodyApprovalReason(bodyApprovalReason)
        }

        /**
         * Sets [Builder.bodyApprovalReason] to an arbitrary JSON value.
         *
         * You should usually call [Builder.bodyApprovalReason] with a well-typed [String] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun bodyApprovalReason(bodyApprovalReason: JsonField<String>) = apply {
            body.bodyApprovalReason(bodyApprovalReason)
        }

        fun bodyApprovedBy(bodyApprovedBy: String) = apply { body.bodyApprovedBy(bodyApprovedBy) }

        /**
         * Sets [Builder.bodyApprovedBy] to an arbitrary JSON value.
         *
         * You should usually call [Builder.bodyApprovedBy] with a well-typed [String] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun bodyApprovedBy(bodyApprovedBy: JsonField<String>) = apply {
            body.bodyApprovedBy(bodyApprovedBy)
        }

        fun bodyIdemKey(bodyIdemKey: String) = apply { body.bodyIdemKey(bodyIdemKey) }

        /**
         * Sets [Builder.bodyIdemKey] to an arbitrary JSON value.
         *
         * You should usually call [Builder.bodyIdemKey] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun bodyIdemKey(bodyIdemKey: JsonField<String>) = apply { body.bodyIdemKey(bodyIdemKey) }

        fun additionalBodyProperties(additionalBodyProperties: Map<String, JsonValue>) = apply {
            body.additionalProperties(additionalBodyProperties)
        }

        fun putAdditionalBodyProperty(key: String, value: JsonValue) = apply {
            body.putAdditionalProperty(key, value)
        }

        fun putAllAdditionalBodyProperties(additionalBodyProperties: Map<String, JsonValue>) =
            apply {
                body.putAllAdditionalProperties(additionalBodyProperties)
            }

        fun removeAdditionalBodyProperty(key: String) = apply { body.removeAdditionalProperty(key) }

        fun removeAllAdditionalBodyProperties(keys: Set<String>) = apply {
            body.removeAllAdditionalProperties(keys)
        }

        fun additionalHeaders(additionalHeaders: Headers) = apply {
            this.additionalHeaders.clear()
            putAllAdditionalHeaders(additionalHeaders)
        }

        fun additionalHeaders(additionalHeaders: Map<String, Iterable<String>>) = apply {
            this.additionalHeaders.clear()
            putAllAdditionalHeaders(additionalHeaders)
        }

        fun putAdditionalHeader(name: String, value: String) = apply {
            additionalHeaders.put(name, value)
        }

        fun putAdditionalHeaders(name: String, values: Iterable<String>) = apply {
            additionalHeaders.put(name, values)
        }

        fun putAllAdditionalHeaders(additionalHeaders: Headers) = apply {
            this.additionalHeaders.putAll(additionalHeaders)
        }

        fun putAllAdditionalHeaders(additionalHeaders: Map<String, Iterable<String>>) = apply {
            this.additionalHeaders.putAll(additionalHeaders)
        }

        fun replaceAdditionalHeaders(name: String, value: String) = apply {
            additionalHeaders.replace(name, value)
        }

        fun replaceAdditionalHeaders(name: String, values: Iterable<String>) = apply {
            additionalHeaders.replace(name, values)
        }

        fun replaceAllAdditionalHeaders(additionalHeaders: Headers) = apply {
            this.additionalHeaders.replaceAll(additionalHeaders)
        }

        fun replaceAllAdditionalHeaders(additionalHeaders: Map<String, Iterable<String>>) = apply {
            this.additionalHeaders.replaceAll(additionalHeaders)
        }

        fun removeAdditionalHeaders(name: String) = apply { additionalHeaders.remove(name) }

        fun removeAllAdditionalHeaders(names: Set<String>) = apply {
            additionalHeaders.removeAll(names)
        }

        fun additionalQueryParams(additionalQueryParams: QueryParams) = apply {
            this.additionalQueryParams.clear()
            putAllAdditionalQueryParams(additionalQueryParams)
        }

        fun additionalQueryParams(additionalQueryParams: Map<String, Iterable<String>>) = apply {
            this.additionalQueryParams.clear()
            putAllAdditionalQueryParams(additionalQueryParams)
        }

        fun putAdditionalQueryParam(key: String, value: String) = apply {
            additionalQueryParams.put(key, value)
        }

        fun putAdditionalQueryParams(key: String, values: Iterable<String>) = apply {
            additionalQueryParams.put(key, values)
        }

        fun putAllAdditionalQueryParams(additionalQueryParams: QueryParams) = apply {
            this.additionalQueryParams.putAll(additionalQueryParams)
        }

        fun putAllAdditionalQueryParams(additionalQueryParams: Map<String, Iterable<String>>) =
            apply {
                this.additionalQueryParams.putAll(additionalQueryParams)
            }

        fun replaceAdditionalQueryParams(key: String, value: String) = apply {
            additionalQueryParams.replace(key, value)
        }

        fun replaceAdditionalQueryParams(key: String, values: Iterable<String>) = apply {
            additionalQueryParams.replace(key, values)
        }

        fun replaceAllAdditionalQueryParams(additionalQueryParams: QueryParams) = apply {
            this.additionalQueryParams.replaceAll(additionalQueryParams)
        }

        fun replaceAllAdditionalQueryParams(additionalQueryParams: Map<String, Iterable<String>>) =
            apply {
                this.additionalQueryParams.replaceAll(additionalQueryParams)
            }

        fun removeAdditionalQueryParams(key: String) = apply { additionalQueryParams.remove(key) }

        fun removeAllAdditionalQueryParams(keys: Set<String>) = apply {
            additionalQueryParams.removeAll(keys)
        }

        /**
         * Returns an immutable instance of [AgentActivateParams].
         *
         * Further updates to this [Builder] will not mutate the returned instance.
         *
         * The following fields are required:
         * ```java
         * .bodyApprovalReason()
         * .bodyApprovedBy()
         * .bodyIdemKey()
         * ```
         *
         * @throws IllegalStateException if any required field is unset.
         */
        fun build(): AgentActivateParams =
            AgentActivateParams(
                dnaId,
                queryApprovalReason,
                queryApprovedBy,
                queryIdemKey,
                body.build(),
                additionalHeaders.build(),
                additionalQueryParams.build(),
            )
    }

    fun _body(): Body = body

    fun _pathParam(index: Int): String =
        when (index) {
            0 -> dnaId ?: ""
            else -> ""
        }

    override fun _headers(): Headers = additionalHeaders

    override fun _queryParams(): QueryParams =
        QueryParams.builder()
            .apply {
                queryApprovalReason?.let { put("approvalReason", it) }
                queryApprovedBy?.let { put("approvedBy", it) }
                queryIdemKey?.let { put("idemKey", it) }
                putAll(additionalQueryParams)
            }
            .build()

    class Body
    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    private constructor(
        private val bodyApprovalReason: JsonField<String>,
        private val bodyApprovedBy: JsonField<String>,
        private val bodyIdemKey: JsonField<String>,
        private val additionalProperties: MutableMap<String, JsonValue>,
    ) {

        @JsonCreator
        private constructor(
            @JsonProperty("approvalReason")
            @ExcludeMissing
            bodyApprovalReason: JsonField<String> = JsonMissing.of(),
            @JsonProperty("approvedBy")
            @ExcludeMissing
            bodyApprovedBy: JsonField<String> = JsonMissing.of(),
            @JsonProperty("idemKey")
            @ExcludeMissing
            bodyIdemKey: JsonField<String> = JsonMissing.of(),
        ) : this(bodyApprovalReason, bodyApprovedBy, bodyIdemKey, mutableMapOf())

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
         *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
         */
        fun bodyApprovalReason(): String = bodyApprovalReason.getRequired("approvalReason")

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
         *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
         */
        fun bodyApprovedBy(): String = bodyApprovedBy.getRequired("approvedBy")

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
         *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
         */
        fun bodyIdemKey(): String = bodyIdemKey.getRequired("idemKey")

        /**
         * Returns the raw JSON value of [bodyApprovalReason].
         *
         * Unlike [bodyApprovalReason], this method doesn't throw if the JSON field has an
         * unexpected type.
         */
        @JsonProperty("approvalReason")
        @ExcludeMissing
        fun _bodyApprovalReason(): JsonField<String> = bodyApprovalReason

        /**
         * Returns the raw JSON value of [bodyApprovedBy].
         *
         * Unlike [bodyApprovedBy], this method doesn't throw if the JSON field has an unexpected
         * type.
         */
        @JsonProperty("approvedBy")
        @ExcludeMissing
        fun _bodyApprovedBy(): JsonField<String> = bodyApprovedBy

        /**
         * Returns the raw JSON value of [bodyIdemKey].
         *
         * Unlike [bodyIdemKey], this method doesn't throw if the JSON field has an unexpected type.
         */
        @JsonProperty("idemKey") @ExcludeMissing fun _bodyIdemKey(): JsonField<String> = bodyIdemKey

        @JsonAnySetter
        private fun putAdditionalProperty(key: String, value: JsonValue) {
            additionalProperties.put(key, value)
        }

        @JsonAnyGetter
        @ExcludeMissing
        fun _additionalProperties(): Map<String, JsonValue> =
            Collections.unmodifiableMap(additionalProperties)

        fun toBuilder() = Builder().from(this)

        companion object {

            /**
             * Returns a mutable builder for constructing an instance of [Body].
             *
             * The following fields are required:
             * ```java
             * .bodyApprovalReason()
             * .bodyApprovedBy()
             * .bodyIdemKey()
             * ```
             */
            @JvmStatic fun builder() = Builder()
        }

        /** A builder for [Body]. */
        class Builder internal constructor() {

            private var bodyApprovalReason: JsonField<String>? = null
            private var bodyApprovedBy: JsonField<String>? = null
            private var bodyIdemKey: JsonField<String>? = null
            private var additionalProperties: MutableMap<String, JsonValue> = mutableMapOf()

            @JvmSynthetic
            internal fun from(body: Body) = apply {
                bodyApprovalReason = body.bodyApprovalReason
                bodyApprovedBy = body.bodyApprovedBy
                bodyIdemKey = body.bodyIdemKey
                additionalProperties = body.additionalProperties.toMutableMap()
            }

            fun bodyApprovalReason(bodyApprovalReason: String) =
                bodyApprovalReason(JsonField.of(bodyApprovalReason))

            /**
             * Sets [Builder.bodyApprovalReason] to an arbitrary JSON value.
             *
             * You should usually call [Builder.bodyApprovalReason] with a well-typed [String] value
             * instead. This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun bodyApprovalReason(bodyApprovalReason: JsonField<String>) = apply {
                this.bodyApprovalReason = bodyApprovalReason
            }

            fun bodyApprovedBy(bodyApprovedBy: String) =
                bodyApprovedBy(JsonField.of(bodyApprovedBy))

            /**
             * Sets [Builder.bodyApprovedBy] to an arbitrary JSON value.
             *
             * You should usually call [Builder.bodyApprovedBy] with a well-typed [String] value
             * instead. This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun bodyApprovedBy(bodyApprovedBy: JsonField<String>) = apply {
                this.bodyApprovedBy = bodyApprovedBy
            }

            fun bodyIdemKey(bodyIdemKey: String) = bodyIdemKey(JsonField.of(bodyIdemKey))

            /**
             * Sets [Builder.bodyIdemKey] to an arbitrary JSON value.
             *
             * You should usually call [Builder.bodyIdemKey] with a well-typed [String] value
             * instead. This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun bodyIdemKey(bodyIdemKey: JsonField<String>) = apply {
                this.bodyIdemKey = bodyIdemKey
            }

            fun additionalProperties(additionalProperties: Map<String, JsonValue>) = apply {
                this.additionalProperties.clear()
                putAllAdditionalProperties(additionalProperties)
            }

            fun putAdditionalProperty(key: String, value: JsonValue) = apply {
                additionalProperties.put(key, value)
            }

            fun putAllAdditionalProperties(additionalProperties: Map<String, JsonValue>) = apply {
                this.additionalProperties.putAll(additionalProperties)
            }

            fun removeAdditionalProperty(key: String) = apply { additionalProperties.remove(key) }

            fun removeAllAdditionalProperties(keys: Set<String>) = apply {
                keys.forEach(::removeAdditionalProperty)
            }

            /**
             * Returns an immutable instance of [Body].
             *
             * Further updates to this [Builder] will not mutate the returned instance.
             *
             * The following fields are required:
             * ```java
             * .bodyApprovalReason()
             * .bodyApprovedBy()
             * .bodyIdemKey()
             * ```
             *
             * @throws IllegalStateException if any required field is unset.
             */
            fun build(): Body =
                Body(
                    checkRequired("bodyApprovalReason", bodyApprovalReason),
                    checkRequired("bodyApprovedBy", bodyApprovedBy),
                    checkRequired("bodyIdemKey", bodyIdemKey),
                    additionalProperties.toMutableMap(),
                )
        }

        private var validated: Boolean = false

        fun validate(): Body = apply {
            if (validated) {
                return@apply
            }

            bodyApprovalReason()
            bodyApprovedBy()
            bodyIdemKey()
            validated = true
        }

        fun isValid(): Boolean =
            try {
                validate()
                true
            } catch (e: AgentregistryInvalidDataException) {
                false
            }

        /**
         * Returns a score indicating how many valid values are contained in this object
         * recursively.
         *
         * Used for best match union deserialization.
         */
        @JvmSynthetic
        internal fun validity(): Int =
            (if (bodyApprovalReason.asKnown().isPresent) 1 else 0) +
                (if (bodyApprovedBy.asKnown().isPresent) 1 else 0) +
                (if (bodyIdemKey.asKnown().isPresent) 1 else 0)

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }

            return other is Body &&
                bodyApprovalReason == other.bodyApprovalReason &&
                bodyApprovedBy == other.bodyApprovedBy &&
                bodyIdemKey == other.bodyIdemKey &&
                additionalProperties == other.additionalProperties
        }

        private val hashCode: Int by lazy {
            Objects.hash(bodyApprovalReason, bodyApprovedBy, bodyIdemKey, additionalProperties)
        }

        override fun hashCode(): Int = hashCode

        override fun toString() =
            "Body{bodyApprovalReason=$bodyApprovalReason, bodyApprovedBy=$bodyApprovedBy, bodyIdemKey=$bodyIdemKey, additionalProperties=$additionalProperties}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return other is AgentActivateParams &&
            dnaId == other.dnaId &&
            queryApprovalReason == other.queryApprovalReason &&
            queryApprovedBy == other.queryApprovedBy &&
            queryIdemKey == other.queryIdemKey &&
            body == other.body &&
            additionalHeaders == other.additionalHeaders &&
            additionalQueryParams == other.additionalQueryParams
    }

    override fun hashCode(): Int =
        Objects.hash(
            dnaId,
            queryApprovalReason,
            queryApprovedBy,
            queryIdemKey,
            body,
            additionalHeaders,
            additionalQueryParams,
        )

    override fun toString() =
        "AgentActivateParams{dnaId=$dnaId, queryApprovalReason=$queryApprovalReason, queryApprovedBy=$queryApprovedBy, queryIdemKey=$queryIdemKey, body=$body, additionalHeaders=$additionalHeaders, additionalQueryParams=$additionalQueryParams}"
}
