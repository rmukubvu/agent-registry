// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.enforce

import com.agentregistry.api.core.ExcludeMissing
import com.agentregistry.api.core.JsonField
import com.agentregistry.api.core.JsonMissing
import com.agentregistry.api.core.JsonValue
import com.agentregistry.api.core.Params
import com.agentregistry.api.core.checkRequired
import com.agentregistry.api.core.http.Headers
import com.agentregistry.api.core.http.QueryParams
import com.agentregistry.api.core.toImmutable
import com.agentregistry.api.errors.AgentregistryInvalidDataException
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Collections
import java.util.Objects
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

/** Evaluate a tool call against Agent DNA policy */
class EnforceEvaluateParams
private constructor(
    private val body: Body,
    private val additionalHeaders: Headers,
    private val additionalQueryParams: QueryParams,
) : Params {

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun dnaId(): String = body.dnaId()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun toolName(): String = body.toolName()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun toolPayload(): ToolPayload = body.toolPayload()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun signature(): Optional<String> = body.signature()

    /**
     * Returns the raw JSON value of [dnaId].
     *
     * Unlike [dnaId], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _dnaId(): JsonField<String> = body._dnaId()

    /**
     * Returns the raw JSON value of [toolName].
     *
     * Unlike [toolName], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _toolName(): JsonField<String> = body._toolName()

    /**
     * Returns the raw JSON value of [toolPayload].
     *
     * Unlike [toolPayload], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _toolPayload(): JsonField<ToolPayload> = body._toolPayload()

    /**
     * Returns the raw JSON value of [signature].
     *
     * Unlike [signature], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _signature(): JsonField<String> = body._signature()

    fun _additionalBodyProperties(): Map<String, JsonValue> = body._additionalProperties()

    /** Additional headers to send with the request. */
    fun _additionalHeaders(): Headers = additionalHeaders

    /** Additional query param to send with the request. */
    fun _additionalQueryParams(): QueryParams = additionalQueryParams

    fun toBuilder() = Builder().from(this)

    companion object {

        /**
         * Returns a mutable builder for constructing an instance of [EnforceEvaluateParams].
         *
         * The following fields are required:
         * ```java
         * .dnaId()
         * .toolName()
         * .toolPayload()
         * ```
         */
        @JvmStatic fun builder() = Builder()
    }

    /** A builder for [EnforceEvaluateParams]. */
    class Builder internal constructor() {

        private var body: Body.Builder = Body.builder()
        private var additionalHeaders: Headers.Builder = Headers.builder()
        private var additionalQueryParams: QueryParams.Builder = QueryParams.builder()

        @JvmSynthetic
        internal fun from(enforceEvaluateParams: EnforceEvaluateParams) = apply {
            body = enforceEvaluateParams.body.toBuilder()
            additionalHeaders = enforceEvaluateParams.additionalHeaders.toBuilder()
            additionalQueryParams = enforceEvaluateParams.additionalQueryParams.toBuilder()
        }

        /**
         * Sets the entire request body.
         *
         * This is generally only useful if you are already constructing the body separately.
         * Otherwise, it's more convenient to use the top-level setters instead:
         * - [dnaId]
         * - [toolName]
         * - [toolPayload]
         * - [signature]
         */
        fun body(body: Body) = apply { this.body = body.toBuilder() }

        fun dnaId(dnaId: String) = apply { body.dnaId(dnaId) }

        /**
         * Sets [Builder.dnaId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.dnaId] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun dnaId(dnaId: JsonField<String>) = apply { body.dnaId(dnaId) }

        fun toolName(toolName: String) = apply { body.toolName(toolName) }

        /**
         * Sets [Builder.toolName] to an arbitrary JSON value.
         *
         * You should usually call [Builder.toolName] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun toolName(toolName: JsonField<String>) = apply { body.toolName(toolName) }

        fun toolPayload(toolPayload: ToolPayload) = apply { body.toolPayload(toolPayload) }

        /**
         * Sets [Builder.toolPayload] to an arbitrary JSON value.
         *
         * You should usually call [Builder.toolPayload] with a well-typed [ToolPayload] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun toolPayload(toolPayload: JsonField<ToolPayload>) = apply {
            body.toolPayload(toolPayload)
        }

        fun signature(signature: String?) = apply { body.signature(signature) }

        /** Alias for calling [Builder.signature] with `signature.orElse(null)`. */
        fun signature(signature: Optional<String>) = signature(signature.getOrNull())

        /**
         * Sets [Builder.signature] to an arbitrary JSON value.
         *
         * You should usually call [Builder.signature] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun signature(signature: JsonField<String>) = apply { body.signature(signature) }

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
         * Returns an immutable instance of [EnforceEvaluateParams].
         *
         * Further updates to this [Builder] will not mutate the returned instance.
         *
         * The following fields are required:
         * ```java
         * .dnaId()
         * .toolName()
         * .toolPayload()
         * ```
         *
         * @throws IllegalStateException if any required field is unset.
         */
        fun build(): EnforceEvaluateParams =
            EnforceEvaluateParams(
                body.build(),
                additionalHeaders.build(),
                additionalQueryParams.build(),
            )
    }

    fun _body(): Body = body

    override fun _headers(): Headers = additionalHeaders

    override fun _queryParams(): QueryParams = additionalQueryParams

    class Body
    @JsonCreator(mode = JsonCreator.Mode.DISABLED)
    private constructor(
        private val dnaId: JsonField<String>,
        private val toolName: JsonField<String>,
        private val toolPayload: JsonField<ToolPayload>,
        private val signature: JsonField<String>,
        private val additionalProperties: MutableMap<String, JsonValue>,
    ) {

        @JsonCreator
        private constructor(
            @JsonProperty("dnaId") @ExcludeMissing dnaId: JsonField<String> = JsonMissing.of(),
            @JsonProperty("toolName")
            @ExcludeMissing
            toolName: JsonField<String> = JsonMissing.of(),
            @JsonProperty("toolPayload")
            @ExcludeMissing
            toolPayload: JsonField<ToolPayload> = JsonMissing.of(),
            @JsonProperty("signature")
            @ExcludeMissing
            signature: JsonField<String> = JsonMissing.of(),
        ) : this(dnaId, toolName, toolPayload, signature, mutableMapOf())

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
         *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
         */
        fun dnaId(): String = dnaId.getRequired("dnaId")

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
         *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
         */
        fun toolName(): String = toolName.getRequired("toolName")

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
         *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
         */
        fun toolPayload(): ToolPayload = toolPayload.getRequired("toolPayload")

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g.
         *   if the server responded with an unexpected value).
         */
        fun signature(): Optional<String> = signature.getOptional("signature")

        /**
         * Returns the raw JSON value of [dnaId].
         *
         * Unlike [dnaId], this method doesn't throw if the JSON field has an unexpected type.
         */
        @JsonProperty("dnaId") @ExcludeMissing fun _dnaId(): JsonField<String> = dnaId

        /**
         * Returns the raw JSON value of [toolName].
         *
         * Unlike [toolName], this method doesn't throw if the JSON field has an unexpected type.
         */
        @JsonProperty("toolName") @ExcludeMissing fun _toolName(): JsonField<String> = toolName

        /**
         * Returns the raw JSON value of [toolPayload].
         *
         * Unlike [toolPayload], this method doesn't throw if the JSON field has an unexpected type.
         */
        @JsonProperty("toolPayload")
        @ExcludeMissing
        fun _toolPayload(): JsonField<ToolPayload> = toolPayload

        /**
         * Returns the raw JSON value of [signature].
         *
         * Unlike [signature], this method doesn't throw if the JSON field has an unexpected type.
         */
        @JsonProperty("signature") @ExcludeMissing fun _signature(): JsonField<String> = signature

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
             * .dnaId()
             * .toolName()
             * .toolPayload()
             * ```
             */
            @JvmStatic fun builder() = Builder()
        }

        /** A builder for [Body]. */
        class Builder internal constructor() {

            private var dnaId: JsonField<String>? = null
            private var toolName: JsonField<String>? = null
            private var toolPayload: JsonField<ToolPayload>? = null
            private var signature: JsonField<String> = JsonMissing.of()
            private var additionalProperties: MutableMap<String, JsonValue> = mutableMapOf()

            @JvmSynthetic
            internal fun from(body: Body) = apply {
                dnaId = body.dnaId
                toolName = body.toolName
                toolPayload = body.toolPayload
                signature = body.signature
                additionalProperties = body.additionalProperties.toMutableMap()
            }

            fun dnaId(dnaId: String) = dnaId(JsonField.of(dnaId))

            /**
             * Sets [Builder.dnaId] to an arbitrary JSON value.
             *
             * You should usually call [Builder.dnaId] with a well-typed [String] value instead.
             * This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun dnaId(dnaId: JsonField<String>) = apply { this.dnaId = dnaId }

            fun toolName(toolName: String) = toolName(JsonField.of(toolName))

            /**
             * Sets [Builder.toolName] to an arbitrary JSON value.
             *
             * You should usually call [Builder.toolName] with a well-typed [String] value instead.
             * This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun toolName(toolName: JsonField<String>) = apply { this.toolName = toolName }

            fun toolPayload(toolPayload: ToolPayload) = toolPayload(JsonField.of(toolPayload))

            /**
             * Sets [Builder.toolPayload] to an arbitrary JSON value.
             *
             * You should usually call [Builder.toolPayload] with a well-typed [ToolPayload] value
             * instead. This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun toolPayload(toolPayload: JsonField<ToolPayload>) = apply {
                this.toolPayload = toolPayload
            }

            fun signature(signature: String?) = signature(JsonField.ofNullable(signature))

            /** Alias for calling [Builder.signature] with `signature.orElse(null)`. */
            fun signature(signature: Optional<String>) = signature(signature.getOrNull())

            /**
             * Sets [Builder.signature] to an arbitrary JSON value.
             *
             * You should usually call [Builder.signature] with a well-typed [String] value instead.
             * This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun signature(signature: JsonField<String>) = apply { this.signature = signature }

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
             * .dnaId()
             * .toolName()
             * .toolPayload()
             * ```
             *
             * @throws IllegalStateException if any required field is unset.
             */
            fun build(): Body =
                Body(
                    checkRequired("dnaId", dnaId),
                    checkRequired("toolName", toolName),
                    checkRequired("toolPayload", toolPayload),
                    signature,
                    additionalProperties.toMutableMap(),
                )
        }

        private var validated: Boolean = false

        fun validate(): Body = apply {
            if (validated) {
                return@apply
            }

            dnaId()
            toolName()
            toolPayload().validate()
            signature()
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
            (if (dnaId.asKnown().isPresent) 1 else 0) +
                (if (toolName.asKnown().isPresent) 1 else 0) +
                (toolPayload.asKnown().getOrNull()?.validity() ?: 0) +
                (if (signature.asKnown().isPresent) 1 else 0)

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }

            return other is Body &&
                dnaId == other.dnaId &&
                toolName == other.toolName &&
                toolPayload == other.toolPayload &&
                signature == other.signature &&
                additionalProperties == other.additionalProperties
        }

        private val hashCode: Int by lazy {
            Objects.hash(dnaId, toolName, toolPayload, signature, additionalProperties)
        }

        override fun hashCode(): Int = hashCode

        override fun toString() =
            "Body{dnaId=$dnaId, toolName=$toolName, toolPayload=$toolPayload, signature=$signature, additionalProperties=$additionalProperties}"
    }

    class ToolPayload
    @JsonCreator
    private constructor(
        @com.fasterxml.jackson.annotation.JsonValue
        private val additionalProperties: Map<String, JsonValue>
    ) {

        @JsonAnyGetter
        @ExcludeMissing
        fun _additionalProperties(): Map<String, JsonValue> = additionalProperties

        fun toBuilder() = Builder().from(this)

        companion object {

            /** Returns a mutable builder for constructing an instance of [ToolPayload]. */
            @JvmStatic fun builder() = Builder()
        }

        /** A builder for [ToolPayload]. */
        class Builder internal constructor() {

            private var additionalProperties: MutableMap<String, JsonValue> = mutableMapOf()

            @JvmSynthetic
            internal fun from(toolPayload: ToolPayload) = apply {
                additionalProperties = toolPayload.additionalProperties.toMutableMap()
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
             * Returns an immutable instance of [ToolPayload].
             *
             * Further updates to this [Builder] will not mutate the returned instance.
             */
            fun build(): ToolPayload = ToolPayload(additionalProperties.toImmutable())
        }

        private var validated: Boolean = false

        fun validate(): ToolPayload = apply {
            if (validated) {
                return@apply
            }

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
            additionalProperties.count { (_, value) -> !value.isNull() && !value.isMissing() }

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }

            return other is ToolPayload && additionalProperties == other.additionalProperties
        }

        private val hashCode: Int by lazy { Objects.hash(additionalProperties) }

        override fun hashCode(): Int = hashCode

        override fun toString() = "ToolPayload{additionalProperties=$additionalProperties}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return other is EnforceEvaluateParams &&
            body == other.body &&
            additionalHeaders == other.additionalHeaders &&
            additionalQueryParams == other.additionalQueryParams
    }

    override fun hashCode(): Int = Objects.hash(body, additionalHeaders, additionalQueryParams)

    override fun toString() =
        "EnforceEvaluateParams{body=$body, additionalHeaders=$additionalHeaders, additionalQueryParams=$additionalQueryParams}"
}
