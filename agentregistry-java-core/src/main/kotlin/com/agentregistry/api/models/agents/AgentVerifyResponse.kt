// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import com.agentregistry.api.core.ExcludeMissing
import com.agentregistry.api.core.JsonField
import com.agentregistry.api.core.JsonMissing
import com.agentregistry.api.core.JsonValue
import com.agentregistry.api.core.checkKnown
import com.agentregistry.api.core.checkRequired
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

class AgentVerifyResponse
@JsonCreator(mode = JsonCreator.Mode.DISABLED)
private constructor(
    private val agentName: JsonField<String>,
    private val capabilities: JsonField<List<String>>,
    private val dnaId: JsonField<String>,
    private val isAuthorized: JsonField<Boolean>,
    private val publicKeyHex: JsonField<String>,
    private val status: JsonField<AgentStatus>,
    private val deniedReason: JsonField<String>,
    private val parentDnaId: JsonField<String>,
    private val additionalProperties: MutableMap<String, JsonValue>,
) {

    @JsonCreator
    private constructor(
        @JsonProperty("agentName") @ExcludeMissing agentName: JsonField<String> = JsonMissing.of(),
        @JsonProperty("capabilities")
        @ExcludeMissing
        capabilities: JsonField<List<String>> = JsonMissing.of(),
        @JsonProperty("dnaId") @ExcludeMissing dnaId: JsonField<String> = JsonMissing.of(),
        @JsonProperty("isAuthorized")
        @ExcludeMissing
        isAuthorized: JsonField<Boolean> = JsonMissing.of(),
        @JsonProperty("publicKeyHex")
        @ExcludeMissing
        publicKeyHex: JsonField<String> = JsonMissing.of(),
        @JsonProperty("status") @ExcludeMissing status: JsonField<AgentStatus> = JsonMissing.of(),
        @JsonProperty("deniedReason")
        @ExcludeMissing
        deniedReason: JsonField<String> = JsonMissing.of(),
        @JsonProperty("parentDnaId")
        @ExcludeMissing
        parentDnaId: JsonField<String> = JsonMissing.of(),
    ) : this(
        agentName,
        capabilities,
        dnaId,
        isAuthorized,
        publicKeyHex,
        status,
        deniedReason,
        parentDnaId,
        mutableMapOf(),
    )

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun agentName(): String = agentName.getRequired("agentName")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun capabilities(): List<String> = capabilities.getRequired("capabilities")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun dnaId(): String = dnaId.getRequired("dnaId")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun isAuthorized(): Boolean = isAuthorized.getRequired("isAuthorized")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun publicKeyHex(): String = publicKeyHex.getRequired("publicKeyHex")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun status(): AgentStatus = status.getRequired("status")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun deniedReason(): Optional<String> = deniedReason.getOptional("deniedReason")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun parentDnaId(): Optional<String> = parentDnaId.getOptional("parentDnaId")

    /**
     * Returns the raw JSON value of [agentName].
     *
     * Unlike [agentName], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("agentName") @ExcludeMissing fun _agentName(): JsonField<String> = agentName

    /**
     * Returns the raw JSON value of [capabilities].
     *
     * Unlike [capabilities], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("capabilities")
    @ExcludeMissing
    fun _capabilities(): JsonField<List<String>> = capabilities

    /**
     * Returns the raw JSON value of [dnaId].
     *
     * Unlike [dnaId], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("dnaId") @ExcludeMissing fun _dnaId(): JsonField<String> = dnaId

    /**
     * Returns the raw JSON value of [isAuthorized].
     *
     * Unlike [isAuthorized], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("isAuthorized")
    @ExcludeMissing
    fun _isAuthorized(): JsonField<Boolean> = isAuthorized

    /**
     * Returns the raw JSON value of [publicKeyHex].
     *
     * Unlike [publicKeyHex], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("publicKeyHex")
    @ExcludeMissing
    fun _publicKeyHex(): JsonField<String> = publicKeyHex

    /**
     * Returns the raw JSON value of [status].
     *
     * Unlike [status], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("status") @ExcludeMissing fun _status(): JsonField<AgentStatus> = status

    /**
     * Returns the raw JSON value of [deniedReason].
     *
     * Unlike [deniedReason], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("deniedReason")
    @ExcludeMissing
    fun _deniedReason(): JsonField<String> = deniedReason

    /**
     * Returns the raw JSON value of [parentDnaId].
     *
     * Unlike [parentDnaId], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("parentDnaId") @ExcludeMissing fun _parentDnaId(): JsonField<String> = parentDnaId

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
         * Returns a mutable builder for constructing an instance of [AgentVerifyResponse].
         *
         * The following fields are required:
         * ```java
         * .agentName()
         * .capabilities()
         * .dnaId()
         * .isAuthorized()
         * .publicKeyHex()
         * .status()
         * ```
         */
        @JvmStatic fun builder() = Builder()
    }

    /** A builder for [AgentVerifyResponse]. */
    class Builder internal constructor() {

        private var agentName: JsonField<String>? = null
        private var capabilities: JsonField<MutableList<String>>? = null
        private var dnaId: JsonField<String>? = null
        private var isAuthorized: JsonField<Boolean>? = null
        private var publicKeyHex: JsonField<String>? = null
        private var status: JsonField<AgentStatus>? = null
        private var deniedReason: JsonField<String> = JsonMissing.of()
        private var parentDnaId: JsonField<String> = JsonMissing.of()
        private var additionalProperties: MutableMap<String, JsonValue> = mutableMapOf()

        @JvmSynthetic
        internal fun from(agentVerifyResponse: AgentVerifyResponse) = apply {
            agentName = agentVerifyResponse.agentName
            capabilities = agentVerifyResponse.capabilities.map { it.toMutableList() }
            dnaId = agentVerifyResponse.dnaId
            isAuthorized = agentVerifyResponse.isAuthorized
            publicKeyHex = agentVerifyResponse.publicKeyHex
            status = agentVerifyResponse.status
            deniedReason = agentVerifyResponse.deniedReason
            parentDnaId = agentVerifyResponse.parentDnaId
            additionalProperties = agentVerifyResponse.additionalProperties.toMutableMap()
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

        fun capabilities(capabilities: List<String>) = capabilities(JsonField.of(capabilities))

        /**
         * Sets [Builder.capabilities] to an arbitrary JSON value.
         *
         * You should usually call [Builder.capabilities] with a well-typed `List<String>` value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun capabilities(capabilities: JsonField<List<String>>) = apply {
            this.capabilities = capabilities.map { it.toMutableList() }
        }

        /**
         * Adds a single [String] to [capabilities].
         *
         * @throws IllegalStateException if the field was previously set to a non-list.
         */
        fun addCapability(capability: String) = apply {
            capabilities =
                (capabilities ?: JsonField.of(mutableListOf())).also {
                    checkKnown("capabilities", it).add(capability)
                }
        }

        fun dnaId(dnaId: String) = dnaId(JsonField.of(dnaId))

        /**
         * Sets [Builder.dnaId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.dnaId] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun dnaId(dnaId: JsonField<String>) = apply { this.dnaId = dnaId }

        fun isAuthorized(isAuthorized: Boolean) = isAuthorized(JsonField.of(isAuthorized))

        /**
         * Sets [Builder.isAuthorized] to an arbitrary JSON value.
         *
         * You should usually call [Builder.isAuthorized] with a well-typed [Boolean] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun isAuthorized(isAuthorized: JsonField<Boolean>) = apply {
            this.isAuthorized = isAuthorized
        }

        fun publicKeyHex(publicKeyHex: String) = publicKeyHex(JsonField.of(publicKeyHex))

        /**
         * Sets [Builder.publicKeyHex] to an arbitrary JSON value.
         *
         * You should usually call [Builder.publicKeyHex] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun publicKeyHex(publicKeyHex: JsonField<String>) = apply {
            this.publicKeyHex = publicKeyHex
        }

        fun status(status: AgentStatus) = status(JsonField.of(status))

        /**
         * Sets [Builder.status] to an arbitrary JSON value.
         *
         * You should usually call [Builder.status] with a well-typed [AgentStatus] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun status(status: JsonField<AgentStatus>) = apply { this.status = status }

        fun deniedReason(deniedReason: String?) = deniedReason(JsonField.ofNullable(deniedReason))

        /** Alias for calling [Builder.deniedReason] with `deniedReason.orElse(null)`. */
        fun deniedReason(deniedReason: Optional<String>) = deniedReason(deniedReason.getOrNull())

        /**
         * Sets [Builder.deniedReason] to an arbitrary JSON value.
         *
         * You should usually call [Builder.deniedReason] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun deniedReason(deniedReason: JsonField<String>) = apply {
            this.deniedReason = deniedReason
        }

        fun parentDnaId(parentDnaId: String?) = parentDnaId(JsonField.ofNullable(parentDnaId))

        /** Alias for calling [Builder.parentDnaId] with `parentDnaId.orElse(null)`. */
        fun parentDnaId(parentDnaId: Optional<String>) = parentDnaId(parentDnaId.getOrNull())

        /**
         * Sets [Builder.parentDnaId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.parentDnaId] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun parentDnaId(parentDnaId: JsonField<String>) = apply { this.parentDnaId = parentDnaId }

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
         * Returns an immutable instance of [AgentVerifyResponse].
         *
         * Further updates to this [Builder] will not mutate the returned instance.
         *
         * The following fields are required:
         * ```java
         * .agentName()
         * .capabilities()
         * .dnaId()
         * .isAuthorized()
         * .publicKeyHex()
         * .status()
         * ```
         *
         * @throws IllegalStateException if any required field is unset.
         */
        fun build(): AgentVerifyResponse =
            AgentVerifyResponse(
                checkRequired("agentName", agentName),
                checkRequired("capabilities", capabilities).map { it.toImmutable() },
                checkRequired("dnaId", dnaId),
                checkRequired("isAuthorized", isAuthorized),
                checkRequired("publicKeyHex", publicKeyHex),
                checkRequired("status", status),
                deniedReason,
                parentDnaId,
                additionalProperties.toMutableMap(),
            )
    }

    private var validated: Boolean = false

    fun validate(): AgentVerifyResponse = apply {
        if (validated) {
            return@apply
        }

        agentName()
        capabilities()
        dnaId()
        isAuthorized()
        publicKeyHex()
        status().validate()
        deniedReason()
        parentDnaId()
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
            (capabilities.asKnown().getOrNull()?.size ?: 0) +
            (if (dnaId.asKnown().isPresent) 1 else 0) +
            (if (isAuthorized.asKnown().isPresent) 1 else 0) +
            (if (publicKeyHex.asKnown().isPresent) 1 else 0) +
            (status.asKnown().getOrNull()?.validity() ?: 0) +
            (if (deniedReason.asKnown().isPresent) 1 else 0) +
            (if (parentDnaId.asKnown().isPresent) 1 else 0)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return other is AgentVerifyResponse &&
            agentName == other.agentName &&
            capabilities == other.capabilities &&
            dnaId == other.dnaId &&
            isAuthorized == other.isAuthorized &&
            publicKeyHex == other.publicKeyHex &&
            status == other.status &&
            deniedReason == other.deniedReason &&
            parentDnaId == other.parentDnaId &&
            additionalProperties == other.additionalProperties
    }

    private val hashCode: Int by lazy {
        Objects.hash(
            agentName,
            capabilities,
            dnaId,
            isAuthorized,
            publicKeyHex,
            status,
            deniedReason,
            parentDnaId,
            additionalProperties,
        )
    }

    override fun hashCode(): Int = hashCode

    override fun toString() =
        "AgentVerifyResponse{agentName=$agentName, capabilities=$capabilities, dnaId=$dnaId, isAuthorized=$isAuthorized, publicKeyHex=$publicKeyHex, status=$status, deniedReason=$deniedReason, parentDnaId=$parentDnaId, additionalProperties=$additionalProperties}"
}
