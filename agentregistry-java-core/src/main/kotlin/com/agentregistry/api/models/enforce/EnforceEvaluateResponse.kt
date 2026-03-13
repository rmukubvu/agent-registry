// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.enforce

import com.agentregistry.api.core.ExcludeMissing
import com.agentregistry.api.core.JsonField
import com.agentregistry.api.core.JsonMissing
import com.agentregistry.api.core.JsonValue
import com.agentregistry.api.core.checkRequired
import com.agentregistry.api.errors.AgentregistryInvalidDataException
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Collections
import java.util.Objects
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

class EnforceEvaluateResponse
@JsonCreator(mode = JsonCreator.Mode.DISABLED)
private constructor(
    private val allowed: JsonField<Boolean>,
    private val dnaId: JsonField<String>,
    private val toolName: JsonField<String>,
    private val agentName: JsonField<String>,
    private val reason: JsonField<String>,
    private val additionalProperties: MutableMap<String, JsonValue>,
) {

    @JsonCreator
    private constructor(
        @JsonProperty("allowed") @ExcludeMissing allowed: JsonField<Boolean> = JsonMissing.of(),
        @JsonProperty("dnaId") @ExcludeMissing dnaId: JsonField<String> = JsonMissing.of(),
        @JsonProperty("toolName") @ExcludeMissing toolName: JsonField<String> = JsonMissing.of(),
        @JsonProperty("agentName") @ExcludeMissing agentName: JsonField<String> = JsonMissing.of(),
        @JsonProperty("reason") @ExcludeMissing reason: JsonField<String> = JsonMissing.of(),
    ) : this(allowed, dnaId, toolName, agentName, reason, mutableMapOf())

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun allowed(): Boolean = allowed.getRequired("allowed")

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
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun agentName(): Optional<String> = agentName.getOptional("agentName")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun reason(): Optional<String> = reason.getOptional("reason")

    /**
     * Returns the raw JSON value of [allowed].
     *
     * Unlike [allowed], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("allowed") @ExcludeMissing fun _allowed(): JsonField<Boolean> = allowed

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
     * Returns the raw JSON value of [agentName].
     *
     * Unlike [agentName], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("agentName") @ExcludeMissing fun _agentName(): JsonField<String> = agentName

    /**
     * Returns the raw JSON value of [reason].
     *
     * Unlike [reason], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("reason") @ExcludeMissing fun _reason(): JsonField<String> = reason

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
         * Returns a mutable builder for constructing an instance of [EnforceEvaluateResponse].
         *
         * The following fields are required:
         * ```java
         * .allowed()
         * .dnaId()
         * .toolName()
         * ```
         */
        @JvmStatic fun builder() = Builder()
    }

    /** A builder for [EnforceEvaluateResponse]. */
    class Builder internal constructor() {

        private var allowed: JsonField<Boolean>? = null
        private var dnaId: JsonField<String>? = null
        private var toolName: JsonField<String>? = null
        private var agentName: JsonField<String> = JsonMissing.of()
        private var reason: JsonField<String> = JsonMissing.of()
        private var additionalProperties: MutableMap<String, JsonValue> = mutableMapOf()

        @JvmSynthetic
        internal fun from(enforceEvaluateResponse: EnforceEvaluateResponse) = apply {
            allowed = enforceEvaluateResponse.allowed
            dnaId = enforceEvaluateResponse.dnaId
            toolName = enforceEvaluateResponse.toolName
            agentName = enforceEvaluateResponse.agentName
            reason = enforceEvaluateResponse.reason
            additionalProperties = enforceEvaluateResponse.additionalProperties.toMutableMap()
        }

        fun allowed(allowed: Boolean) = allowed(JsonField.of(allowed))

        /**
         * Sets [Builder.allowed] to an arbitrary JSON value.
         *
         * You should usually call [Builder.allowed] with a well-typed [Boolean] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun allowed(allowed: JsonField<Boolean>) = apply { this.allowed = allowed }

        fun dnaId(dnaId: String) = dnaId(JsonField.of(dnaId))

        /**
         * Sets [Builder.dnaId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.dnaId] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun dnaId(dnaId: JsonField<String>) = apply { this.dnaId = dnaId }

        fun toolName(toolName: String) = toolName(JsonField.of(toolName))

        /**
         * Sets [Builder.toolName] to an arbitrary JSON value.
         *
         * You should usually call [Builder.toolName] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun toolName(toolName: JsonField<String>) = apply { this.toolName = toolName }

        fun agentName(agentName: String?) = agentName(JsonField.ofNullable(agentName))

        /** Alias for calling [Builder.agentName] with `agentName.orElse(null)`. */
        fun agentName(agentName: Optional<String>) = agentName(agentName.getOrNull())

        /**
         * Sets [Builder.agentName] to an arbitrary JSON value.
         *
         * You should usually call [Builder.agentName] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun agentName(agentName: JsonField<String>) = apply { this.agentName = agentName }

        fun reason(reason: String?) = reason(JsonField.ofNullable(reason))

        /** Alias for calling [Builder.reason] with `reason.orElse(null)`. */
        fun reason(reason: Optional<String>) = reason(reason.getOrNull())

        /**
         * Sets [Builder.reason] to an arbitrary JSON value.
         *
         * You should usually call [Builder.reason] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun reason(reason: JsonField<String>) = apply { this.reason = reason }

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
         * Returns an immutable instance of [EnforceEvaluateResponse].
         *
         * Further updates to this [Builder] will not mutate the returned instance.
         *
         * The following fields are required:
         * ```java
         * .allowed()
         * .dnaId()
         * .toolName()
         * ```
         *
         * @throws IllegalStateException if any required field is unset.
         */
        fun build(): EnforceEvaluateResponse =
            EnforceEvaluateResponse(
                checkRequired("allowed", allowed),
                checkRequired("dnaId", dnaId),
                checkRequired("toolName", toolName),
                agentName,
                reason,
                additionalProperties.toMutableMap(),
            )
    }

    private var validated: Boolean = false

    fun validate(): EnforceEvaluateResponse = apply {
        if (validated) {
            return@apply
        }

        allowed()
        dnaId()
        toolName()
        agentName()
        reason()
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
     * Returns a score indicating how many valid values are contained in this object recursively.
     *
     * Used for best match union deserialization.
     */
    @JvmSynthetic
    internal fun validity(): Int =
        (if (allowed.asKnown().isPresent) 1 else 0) +
            (if (dnaId.asKnown().isPresent) 1 else 0) +
            (if (toolName.asKnown().isPresent) 1 else 0) +
            (if (agentName.asKnown().isPresent) 1 else 0) +
            (if (reason.asKnown().isPresent) 1 else 0)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return other is EnforceEvaluateResponse &&
            allowed == other.allowed &&
            dnaId == other.dnaId &&
            toolName == other.toolName &&
            agentName == other.agentName &&
            reason == other.reason &&
            additionalProperties == other.additionalProperties
    }

    private val hashCode: Int by lazy {
        Objects.hash(allowed, dnaId, toolName, agentName, reason, additionalProperties)
    }

    override fun hashCode(): Int = hashCode

    override fun toString() =
        "EnforceEvaluateResponse{allowed=$allowed, dnaId=$dnaId, toolName=$toolName, agentName=$agentName, reason=$reason, additionalProperties=$additionalProperties}"
}
