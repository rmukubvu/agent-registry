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

class AgentTransition
@JsonCreator(mode = JsonCreator.Mode.DISABLED)
private constructor(
    private val dnaId: JsonField<String>,
    private val newStatus: JsonField<AgentStatus>,
    private val previousStatus: JsonField<AgentStatus>,
    private val additionalProperties: MutableMap<String, JsonValue>,
) {

    @JsonCreator
    private constructor(
        @JsonProperty("dnaId") @ExcludeMissing dnaId: JsonField<String> = JsonMissing.of(),
        @JsonProperty("newStatus")
        @ExcludeMissing
        newStatus: JsonField<AgentStatus> = JsonMissing.of(),
        @JsonProperty("previousStatus")
        @ExcludeMissing
        previousStatus: JsonField<AgentStatus> = JsonMissing.of(),
    ) : this(dnaId, newStatus, previousStatus, mutableMapOf())

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun dnaId(): String = dnaId.getRequired("dnaId")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun newStatus(): AgentStatus = newStatus.getRequired("newStatus")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun previousStatus(): AgentStatus = previousStatus.getRequired("previousStatus")

    /**
     * Returns the raw JSON value of [dnaId].
     *
     * Unlike [dnaId], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("dnaId") @ExcludeMissing fun _dnaId(): JsonField<String> = dnaId

    /**
     * Returns the raw JSON value of [newStatus].
     *
     * Unlike [newStatus], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("newStatus") @ExcludeMissing fun _newStatus(): JsonField<AgentStatus> = newStatus

    /**
     * Returns the raw JSON value of [previousStatus].
     *
     * Unlike [previousStatus], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("previousStatus")
    @ExcludeMissing
    fun _previousStatus(): JsonField<AgentStatus> = previousStatus

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
         * Returns a mutable builder for constructing an instance of [AgentTransition].
         *
         * The following fields are required:
         * ```java
         * .dnaId()
         * .newStatus()
         * .previousStatus()
         * ```
         */
        @JvmStatic fun builder() = Builder()
    }

    /** A builder for [AgentTransition]. */
    class Builder internal constructor() {

        private var dnaId: JsonField<String>? = null
        private var newStatus: JsonField<AgentStatus>? = null
        private var previousStatus: JsonField<AgentStatus>? = null
        private var additionalProperties: MutableMap<String, JsonValue> = mutableMapOf()

        @JvmSynthetic
        internal fun from(agentTransition: AgentTransition) = apply {
            dnaId = agentTransition.dnaId
            newStatus = agentTransition.newStatus
            previousStatus = agentTransition.previousStatus
            additionalProperties = agentTransition.additionalProperties.toMutableMap()
        }

        fun dnaId(dnaId: String) = dnaId(JsonField.of(dnaId))

        /**
         * Sets [Builder.dnaId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.dnaId] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun dnaId(dnaId: JsonField<String>) = apply { this.dnaId = dnaId }

        fun newStatus(newStatus: AgentStatus) = newStatus(JsonField.of(newStatus))

        /**
         * Sets [Builder.newStatus] to an arbitrary JSON value.
         *
         * You should usually call [Builder.newStatus] with a well-typed [AgentStatus] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun newStatus(newStatus: JsonField<AgentStatus>) = apply { this.newStatus = newStatus }

        fun previousStatus(previousStatus: AgentStatus) =
            previousStatus(JsonField.of(previousStatus))

        /**
         * Sets [Builder.previousStatus] to an arbitrary JSON value.
         *
         * You should usually call [Builder.previousStatus] with a well-typed [AgentStatus] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun previousStatus(previousStatus: JsonField<AgentStatus>) = apply {
            this.previousStatus = previousStatus
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
         * Returns an immutable instance of [AgentTransition].
         *
         * Further updates to this [Builder] will not mutate the returned instance.
         *
         * The following fields are required:
         * ```java
         * .dnaId()
         * .newStatus()
         * .previousStatus()
         * ```
         *
         * @throws IllegalStateException if any required field is unset.
         */
        fun build(): AgentTransition =
            AgentTransition(
                checkRequired("dnaId", dnaId),
                checkRequired("newStatus", newStatus),
                checkRequired("previousStatus", previousStatus),
                additionalProperties.toMutableMap(),
            )
    }

    private var validated: Boolean = false

    fun validate(): AgentTransition = apply {
        if (validated) {
            return@apply
        }

        dnaId()
        newStatus().validate()
        previousStatus().validate()
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
        (if (dnaId.asKnown().isPresent) 1 else 0) +
            (newStatus.asKnown().getOrNull()?.validity() ?: 0) +
            (previousStatus.asKnown().getOrNull()?.validity() ?: 0)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return other is AgentTransition &&
            dnaId == other.dnaId &&
            newStatus == other.newStatus &&
            previousStatus == other.previousStatus &&
            additionalProperties == other.additionalProperties
    }

    private val hashCode: Int by lazy {
        Objects.hash(dnaId, newStatus, previousStatus, additionalProperties)
    }

    override fun hashCode(): Int = hashCode

    override fun toString() =
        "AgentTransition{dnaId=$dnaId, newStatus=$newStatus, previousStatus=$previousStatus, additionalProperties=$additionalProperties}"
}
