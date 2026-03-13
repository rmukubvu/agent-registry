// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

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
import kotlin.jvm.optionals.getOrNull

class AgentCreateResponse
@JsonCreator(mode = JsonCreator.Mode.DISABLED)
private constructor(
    private val agentName: JsonField<String>,
    private val dnaId: JsonField<String>,
    private val status: JsonField<AgentStatus>,
    private val additionalProperties: MutableMap<String, JsonValue>,
) {

    @JsonCreator
    private constructor(
        @JsonProperty("agentName") @ExcludeMissing agentName: JsonField<String> = JsonMissing.of(),
        @JsonProperty("dnaId") @ExcludeMissing dnaId: JsonField<String> = JsonMissing.of(),
        @JsonProperty("status") @ExcludeMissing status: JsonField<AgentStatus> = JsonMissing.of(),
    ) : this(agentName, dnaId, status, mutableMapOf())

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun agentName(): String = agentName.getRequired("agentName")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun dnaId(): String = dnaId.getRequired("dnaId")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun status(): AgentStatus = status.getRequired("status")

    /**
     * Returns the raw JSON value of [agentName].
     *
     * Unlike [agentName], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("agentName") @ExcludeMissing fun _agentName(): JsonField<String> = agentName

    /**
     * Returns the raw JSON value of [dnaId].
     *
     * Unlike [dnaId], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("dnaId") @ExcludeMissing fun _dnaId(): JsonField<String> = dnaId

    /**
     * Returns the raw JSON value of [status].
     *
     * Unlike [status], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("status") @ExcludeMissing fun _status(): JsonField<AgentStatus> = status

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
         * Returns a mutable builder for constructing an instance of [AgentCreateResponse].
         *
         * The following fields are required:
         * ```java
         * .agentName()
         * .dnaId()
         * .status()
         * ```
         */
        @JvmStatic fun builder() = Builder()
    }

    /** A builder for [AgentCreateResponse]. */
    class Builder internal constructor() {

        private var agentName: JsonField<String>? = null
        private var dnaId: JsonField<String>? = null
        private var status: JsonField<AgentStatus>? = null
        private var additionalProperties: MutableMap<String, JsonValue> = mutableMapOf()

        @JvmSynthetic
        internal fun from(agentCreateResponse: AgentCreateResponse) = apply {
            agentName = agentCreateResponse.agentName
            dnaId = agentCreateResponse.dnaId
            status = agentCreateResponse.status
            additionalProperties = agentCreateResponse.additionalProperties.toMutableMap()
        }

        fun agentName(agentName: String) = agentName(JsonField.of(agentName))

        /**
         * Sets [Builder.agentName] to an arbitrary JSON value.
         *
         * You should usually call [Builder.agentName] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun agentName(agentName: JsonField<String>) = apply { this.agentName = agentName }

        fun dnaId(dnaId: String) = dnaId(JsonField.of(dnaId))

        /**
         * Sets [Builder.dnaId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.dnaId] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun dnaId(dnaId: JsonField<String>) = apply { this.dnaId = dnaId }

        fun status(status: AgentStatus) = status(JsonField.of(status))

        /**
         * Sets [Builder.status] to an arbitrary JSON value.
         *
         * You should usually call [Builder.status] with a well-typed [AgentStatus] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun status(status: JsonField<AgentStatus>) = apply { this.status = status }

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
         * Returns an immutable instance of [AgentCreateResponse].
         *
         * Further updates to this [Builder] will not mutate the returned instance.
         *
         * The following fields are required:
         * ```java
         * .agentName()
         * .dnaId()
         * .status()
         * ```
         *
         * @throws IllegalStateException if any required field is unset.
         */
        fun build(): AgentCreateResponse =
            AgentCreateResponse(
                checkRequired("agentName", agentName),
                checkRequired("dnaId", dnaId),
                checkRequired("status", status),
                additionalProperties.toMutableMap(),
            )
    }

    private var validated: Boolean = false

    fun validate(): AgentCreateResponse = apply {
        if (validated) {
            return@apply
        }

        agentName()
        dnaId()
        status().validate()
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
        (if (agentName.asKnown().isPresent) 1 else 0) +
            (if (dnaId.asKnown().isPresent) 1 else 0) +
            (status.asKnown().getOrNull()?.validity() ?: 0)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return other is AgentCreateResponse &&
            agentName == other.agentName &&
            dnaId == other.dnaId &&
            status == other.status &&
            additionalProperties == other.additionalProperties
    }

    private val hashCode: Int by lazy {
        Objects.hash(agentName, dnaId, status, additionalProperties)
    }

    override fun hashCode(): Int = hashCode

    override fun toString() =
        "AgentCreateResponse{agentName=$agentName, dnaId=$dnaId, status=$status, additionalProperties=$additionalProperties}"
}
