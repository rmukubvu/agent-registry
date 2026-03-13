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
import java.time.OffsetDateTime
import java.util.Collections
import java.util.Objects
import java.util.Optional
import kotlin.jvm.optionals.getOrNull

class AgentListResponse
@JsonCreator(mode = JsonCreator.Mode.DISABLED)
private constructor(
    private val agentName: JsonField<String>,
    private val capabilities: JsonField<List<String>>,
    private val createdAt: JsonField<OffsetDateTime>,
    private val dnaId: JsonField<String>,
    private val jurisdiction: JsonField<String>,
    private val ownerId: JsonField<String>,
    private val ownerName: JsonField<String>,
    private val publicKeyHex: JsonField<String>,
    private val status: JsonField<AgentStatus>,
    private val updatedAt: JsonField<OffsetDateTime>,
    private val version: JsonField<Int>,
    private val approvalReason: JsonField<String>,
    private val approvedAt: JsonField<OffsetDateTime>,
    private val approvedBy: JsonField<String>,
    private val expiresAt: JsonField<OffsetDateTime>,
    private val parentDnaId: JsonField<String>,
    private val provenanceRef: JsonField<String>,
    private val revokedAt: JsonField<OffsetDateTime>,
    private val revokedReason: JsonField<String>,
    private val workloadIdentity: JsonField<String>,
    private val additionalProperties: MutableMap<String, JsonValue>,
) {

    @JsonCreator
    private constructor(
        @JsonProperty("agentName") @ExcludeMissing agentName: JsonField<String> = JsonMissing.of(),
        @JsonProperty("capabilities")
        @ExcludeMissing
        capabilities: JsonField<List<String>> = JsonMissing.of(),
        @JsonProperty("createdAt")
        @ExcludeMissing
        createdAt: JsonField<OffsetDateTime> = JsonMissing.of(),
        @JsonProperty("dnaId") @ExcludeMissing dnaId: JsonField<String> = JsonMissing.of(),
        @JsonProperty("jurisdiction")
        @ExcludeMissing
        jurisdiction: JsonField<String> = JsonMissing.of(),
        @JsonProperty("ownerId") @ExcludeMissing ownerId: JsonField<String> = JsonMissing.of(),
        @JsonProperty("ownerName") @ExcludeMissing ownerName: JsonField<String> = JsonMissing.of(),
        @JsonProperty("publicKeyHex")
        @ExcludeMissing
        publicKeyHex: JsonField<String> = JsonMissing.of(),
        @JsonProperty("status") @ExcludeMissing status: JsonField<AgentStatus> = JsonMissing.of(),
        @JsonProperty("updatedAt")
        @ExcludeMissing
        updatedAt: JsonField<OffsetDateTime> = JsonMissing.of(),
        @JsonProperty("version") @ExcludeMissing version: JsonField<Int> = JsonMissing.of(),
        @JsonProperty("approvalReason")
        @ExcludeMissing
        approvalReason: JsonField<String> = JsonMissing.of(),
        @JsonProperty("approvedAt")
        @ExcludeMissing
        approvedAt: JsonField<OffsetDateTime> = JsonMissing.of(),
        @JsonProperty("approvedBy")
        @ExcludeMissing
        approvedBy: JsonField<String> = JsonMissing.of(),
        @JsonProperty("expiresAt")
        @ExcludeMissing
        expiresAt: JsonField<OffsetDateTime> = JsonMissing.of(),
        @JsonProperty("parentDnaId")
        @ExcludeMissing
        parentDnaId: JsonField<String> = JsonMissing.of(),
        @JsonProperty("provenanceRef")
        @ExcludeMissing
        provenanceRef: JsonField<String> = JsonMissing.of(),
        @JsonProperty("revokedAt")
        @ExcludeMissing
        revokedAt: JsonField<OffsetDateTime> = JsonMissing.of(),
        @JsonProperty("revokedReason")
        @ExcludeMissing
        revokedReason: JsonField<String> = JsonMissing.of(),
        @JsonProperty("workloadIdentity")
        @ExcludeMissing
        workloadIdentity: JsonField<String> = JsonMissing.of(),
    ) : this(
        agentName,
        capabilities,
        createdAt,
        dnaId,
        jurisdiction,
        ownerId,
        ownerName,
        publicKeyHex,
        status,
        updatedAt,
        version,
        approvalReason,
        approvedAt,
        approvedBy,
        expiresAt,
        parentDnaId,
        provenanceRef,
        revokedAt,
        revokedReason,
        workloadIdentity,
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
    fun createdAt(): OffsetDateTime = createdAt.getRequired("createdAt")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun dnaId(): String = dnaId.getRequired("dnaId")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun jurisdiction(): String = jurisdiction.getRequired("jurisdiction")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun ownerId(): String = ownerId.getRequired("ownerId")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun ownerName(): String = ownerName.getRequired("ownerName")

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
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun updatedAt(): OffsetDateTime = updatedAt.getRequired("updatedAt")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun version(): Int = version.getRequired("version")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun approvalReason(): Optional<String> = approvalReason.getOptional("approvalReason")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun approvedAt(): Optional<OffsetDateTime> = approvedAt.getOptional("approvedAt")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun approvedBy(): Optional<String> = approvedBy.getOptional("approvedBy")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun expiresAt(): Optional<OffsetDateTime> = expiresAt.getOptional("expiresAt")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun parentDnaId(): Optional<String> = parentDnaId.getOptional("parentDnaId")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun provenanceRef(): Optional<String> = provenanceRef.getOptional("provenanceRef")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun revokedAt(): Optional<OffsetDateTime> = revokedAt.getOptional("revokedAt")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun revokedReason(): Optional<String> = revokedReason.getOptional("revokedReason")

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun workloadIdentity(): Optional<String> = workloadIdentity.getOptional("workloadIdentity")

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
     * Returns the raw JSON value of [createdAt].
     *
     * Unlike [createdAt], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("createdAt")
    @ExcludeMissing
    fun _createdAt(): JsonField<OffsetDateTime> = createdAt

    /**
     * Returns the raw JSON value of [dnaId].
     *
     * Unlike [dnaId], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("dnaId") @ExcludeMissing fun _dnaId(): JsonField<String> = dnaId

    /**
     * Returns the raw JSON value of [jurisdiction].
     *
     * Unlike [jurisdiction], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("jurisdiction")
    @ExcludeMissing
    fun _jurisdiction(): JsonField<String> = jurisdiction

    /**
     * Returns the raw JSON value of [ownerId].
     *
     * Unlike [ownerId], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("ownerId") @ExcludeMissing fun _ownerId(): JsonField<String> = ownerId

    /**
     * Returns the raw JSON value of [ownerName].
     *
     * Unlike [ownerName], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("ownerName") @ExcludeMissing fun _ownerName(): JsonField<String> = ownerName

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
     * Returns the raw JSON value of [updatedAt].
     *
     * Unlike [updatedAt], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("updatedAt")
    @ExcludeMissing
    fun _updatedAt(): JsonField<OffsetDateTime> = updatedAt

    /**
     * Returns the raw JSON value of [version].
     *
     * Unlike [version], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("version") @ExcludeMissing fun _version(): JsonField<Int> = version

    /**
     * Returns the raw JSON value of [approvalReason].
     *
     * Unlike [approvalReason], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("approvalReason")
    @ExcludeMissing
    fun _approvalReason(): JsonField<String> = approvalReason

    /**
     * Returns the raw JSON value of [approvedAt].
     *
     * Unlike [approvedAt], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("approvedAt")
    @ExcludeMissing
    fun _approvedAt(): JsonField<OffsetDateTime> = approvedAt

    /**
     * Returns the raw JSON value of [approvedBy].
     *
     * Unlike [approvedBy], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("approvedBy") @ExcludeMissing fun _approvedBy(): JsonField<String> = approvedBy

    /**
     * Returns the raw JSON value of [expiresAt].
     *
     * Unlike [expiresAt], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("expiresAt")
    @ExcludeMissing
    fun _expiresAt(): JsonField<OffsetDateTime> = expiresAt

    /**
     * Returns the raw JSON value of [parentDnaId].
     *
     * Unlike [parentDnaId], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("parentDnaId") @ExcludeMissing fun _parentDnaId(): JsonField<String> = parentDnaId

    /**
     * Returns the raw JSON value of [provenanceRef].
     *
     * Unlike [provenanceRef], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("provenanceRef")
    @ExcludeMissing
    fun _provenanceRef(): JsonField<String> = provenanceRef

    /**
     * Returns the raw JSON value of [revokedAt].
     *
     * Unlike [revokedAt], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("revokedAt")
    @ExcludeMissing
    fun _revokedAt(): JsonField<OffsetDateTime> = revokedAt

    /**
     * Returns the raw JSON value of [revokedReason].
     *
     * Unlike [revokedReason], this method doesn't throw if the JSON field has an unexpected type.
     */
    @JsonProperty("revokedReason")
    @ExcludeMissing
    fun _revokedReason(): JsonField<String> = revokedReason

    /**
     * Returns the raw JSON value of [workloadIdentity].
     *
     * Unlike [workloadIdentity], this method doesn't throw if the JSON field has an unexpected
     * type.
     */
    @JsonProperty("workloadIdentity")
    @ExcludeMissing
    fun _workloadIdentity(): JsonField<String> = workloadIdentity

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
         * Returns a mutable builder for constructing an instance of [AgentListResponse].
         *
         * The following fields are required:
         * ```java
         * .agentName()
         * .capabilities()
         * .createdAt()
         * .dnaId()
         * .jurisdiction()
         * .ownerId()
         * .ownerName()
         * .publicKeyHex()
         * .status()
         * .updatedAt()
         * .version()
         * ```
         */
        @JvmStatic fun builder() = Builder()
    }

    /** A builder for [AgentListResponse]. */
    class Builder internal constructor() {

        private var agentName: JsonField<String>? = null
        private var capabilities: JsonField<MutableList<String>>? = null
        private var createdAt: JsonField<OffsetDateTime>? = null
        private var dnaId: JsonField<String>? = null
        private var jurisdiction: JsonField<String>? = null
        private var ownerId: JsonField<String>? = null
        private var ownerName: JsonField<String>? = null
        private var publicKeyHex: JsonField<String>? = null
        private var status: JsonField<AgentStatus>? = null
        private var updatedAt: JsonField<OffsetDateTime>? = null
        private var version: JsonField<Int>? = null
        private var approvalReason: JsonField<String> = JsonMissing.of()
        private var approvedAt: JsonField<OffsetDateTime> = JsonMissing.of()
        private var approvedBy: JsonField<String> = JsonMissing.of()
        private var expiresAt: JsonField<OffsetDateTime> = JsonMissing.of()
        private var parentDnaId: JsonField<String> = JsonMissing.of()
        private var provenanceRef: JsonField<String> = JsonMissing.of()
        private var revokedAt: JsonField<OffsetDateTime> = JsonMissing.of()
        private var revokedReason: JsonField<String> = JsonMissing.of()
        private var workloadIdentity: JsonField<String> = JsonMissing.of()
        private var additionalProperties: MutableMap<String, JsonValue> = mutableMapOf()

        @JvmSynthetic
        internal fun from(agentListResponse: AgentListResponse) = apply {
            agentName = agentListResponse.agentName
            capabilities = agentListResponse.capabilities.map { it.toMutableList() }
            createdAt = agentListResponse.createdAt
            dnaId = agentListResponse.dnaId
            jurisdiction = agentListResponse.jurisdiction
            ownerId = agentListResponse.ownerId
            ownerName = agentListResponse.ownerName
            publicKeyHex = agentListResponse.publicKeyHex
            status = agentListResponse.status
            updatedAt = agentListResponse.updatedAt
            version = agentListResponse.version
            approvalReason = agentListResponse.approvalReason
            approvedAt = agentListResponse.approvedAt
            approvedBy = agentListResponse.approvedBy
            expiresAt = agentListResponse.expiresAt
            parentDnaId = agentListResponse.parentDnaId
            provenanceRef = agentListResponse.provenanceRef
            revokedAt = agentListResponse.revokedAt
            revokedReason = agentListResponse.revokedReason
            workloadIdentity = agentListResponse.workloadIdentity
            additionalProperties = agentListResponse.additionalProperties.toMutableMap()
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

        fun createdAt(createdAt: OffsetDateTime) = createdAt(JsonField.of(createdAt))

        /**
         * Sets [Builder.createdAt] to an arbitrary JSON value.
         *
         * You should usually call [Builder.createdAt] with a well-typed [OffsetDateTime] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun createdAt(createdAt: JsonField<OffsetDateTime>) = apply { this.createdAt = createdAt }

        fun dnaId(dnaId: String) = dnaId(JsonField.of(dnaId))

        /**
         * Sets [Builder.dnaId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.dnaId] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun dnaId(dnaId: JsonField<String>) = apply { this.dnaId = dnaId }

        fun jurisdiction(jurisdiction: String) = jurisdiction(JsonField.of(jurisdiction))

        /**
         * Sets [Builder.jurisdiction] to an arbitrary JSON value.
         *
         * You should usually call [Builder.jurisdiction] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun jurisdiction(jurisdiction: JsonField<String>) = apply {
            this.jurisdiction = jurisdiction
        }

        fun ownerId(ownerId: String) = ownerId(JsonField.of(ownerId))

        /**
         * Sets [Builder.ownerId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.ownerId] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun ownerId(ownerId: JsonField<String>) = apply { this.ownerId = ownerId }

        fun ownerName(ownerName: String) = ownerName(JsonField.of(ownerName))

        /**
         * Sets [Builder.ownerName] to an arbitrary JSON value.
         *
         * You should usually call [Builder.ownerName] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun ownerName(ownerName: JsonField<String>) = apply { this.ownerName = ownerName }

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

        fun updatedAt(updatedAt: OffsetDateTime) = updatedAt(JsonField.of(updatedAt))

        /**
         * Sets [Builder.updatedAt] to an arbitrary JSON value.
         *
         * You should usually call [Builder.updatedAt] with a well-typed [OffsetDateTime] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun updatedAt(updatedAt: JsonField<OffsetDateTime>) = apply { this.updatedAt = updatedAt }

        fun version(version: Int) = version(JsonField.of(version))

        /**
         * Sets [Builder.version] to an arbitrary JSON value.
         *
         * You should usually call [Builder.version] with a well-typed [Int] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun version(version: JsonField<Int>) = apply { this.version = version }

        fun approvalReason(approvalReason: String?) =
            approvalReason(JsonField.ofNullable(approvalReason))

        /** Alias for calling [Builder.approvalReason] with `approvalReason.orElse(null)`. */
        fun approvalReason(approvalReason: Optional<String>) =
            approvalReason(approvalReason.getOrNull())

        /**
         * Sets [Builder.approvalReason] to an arbitrary JSON value.
         *
         * You should usually call [Builder.approvalReason] with a well-typed [String] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun approvalReason(approvalReason: JsonField<String>) = apply {
            this.approvalReason = approvalReason
        }

        fun approvedAt(approvedAt: OffsetDateTime?) = approvedAt(JsonField.ofNullable(approvedAt))

        /** Alias for calling [Builder.approvedAt] with `approvedAt.orElse(null)`. */
        fun approvedAt(approvedAt: Optional<OffsetDateTime>) = approvedAt(approvedAt.getOrNull())

        /**
         * Sets [Builder.approvedAt] to an arbitrary JSON value.
         *
         * You should usually call [Builder.approvedAt] with a well-typed [OffsetDateTime] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun approvedAt(approvedAt: JsonField<OffsetDateTime>) = apply {
            this.approvedAt = approvedAt
        }

        fun approvedBy(approvedBy: String?) = approvedBy(JsonField.ofNullable(approvedBy))

        /** Alias for calling [Builder.approvedBy] with `approvedBy.orElse(null)`. */
        fun approvedBy(approvedBy: Optional<String>) = approvedBy(approvedBy.getOrNull())

        /**
         * Sets [Builder.approvedBy] to an arbitrary JSON value.
         *
         * You should usually call [Builder.approvedBy] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun approvedBy(approvedBy: JsonField<String>) = apply { this.approvedBy = approvedBy }

        fun expiresAt(expiresAt: OffsetDateTime?) = expiresAt(JsonField.ofNullable(expiresAt))

        /** Alias for calling [Builder.expiresAt] with `expiresAt.orElse(null)`. */
        fun expiresAt(expiresAt: Optional<OffsetDateTime>) = expiresAt(expiresAt.getOrNull())

        /**
         * Sets [Builder.expiresAt] to an arbitrary JSON value.
         *
         * You should usually call [Builder.expiresAt] with a well-typed [OffsetDateTime] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun expiresAt(expiresAt: JsonField<OffsetDateTime>) = apply { this.expiresAt = expiresAt }

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

        fun provenanceRef(provenanceRef: String?) =
            provenanceRef(JsonField.ofNullable(provenanceRef))

        /** Alias for calling [Builder.provenanceRef] with `provenanceRef.orElse(null)`. */
        fun provenanceRef(provenanceRef: Optional<String>) =
            provenanceRef(provenanceRef.getOrNull())

        /**
         * Sets [Builder.provenanceRef] to an arbitrary JSON value.
         *
         * You should usually call [Builder.provenanceRef] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun provenanceRef(provenanceRef: JsonField<String>) = apply {
            this.provenanceRef = provenanceRef
        }

        fun revokedAt(revokedAt: OffsetDateTime?) = revokedAt(JsonField.ofNullable(revokedAt))

        /** Alias for calling [Builder.revokedAt] with `revokedAt.orElse(null)`. */
        fun revokedAt(revokedAt: Optional<OffsetDateTime>) = revokedAt(revokedAt.getOrNull())

        /**
         * Sets [Builder.revokedAt] to an arbitrary JSON value.
         *
         * You should usually call [Builder.revokedAt] with a well-typed [OffsetDateTime] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun revokedAt(revokedAt: JsonField<OffsetDateTime>) = apply { this.revokedAt = revokedAt }

        fun revokedReason(revokedReason: String?) =
            revokedReason(JsonField.ofNullable(revokedReason))

        /** Alias for calling [Builder.revokedReason] with `revokedReason.orElse(null)`. */
        fun revokedReason(revokedReason: Optional<String>) =
            revokedReason(revokedReason.getOrNull())

        /**
         * Sets [Builder.revokedReason] to an arbitrary JSON value.
         *
         * You should usually call [Builder.revokedReason] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun revokedReason(revokedReason: JsonField<String>) = apply {
            this.revokedReason = revokedReason
        }

        fun workloadIdentity(workloadIdentity: String?) =
            workloadIdentity(JsonField.ofNullable(workloadIdentity))

        /** Alias for calling [Builder.workloadIdentity] with `workloadIdentity.orElse(null)`. */
        fun workloadIdentity(workloadIdentity: Optional<String>) =
            workloadIdentity(workloadIdentity.getOrNull())

        /**
         * Sets [Builder.workloadIdentity] to an arbitrary JSON value.
         *
         * You should usually call [Builder.workloadIdentity] with a well-typed [String] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun workloadIdentity(workloadIdentity: JsonField<String>) = apply {
            this.workloadIdentity = workloadIdentity
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
         * Returns an immutable instance of [AgentListResponse].
         *
         * Further updates to this [Builder] will not mutate the returned instance.
         *
         * The following fields are required:
         * ```java
         * .agentName()
         * .capabilities()
         * .createdAt()
         * .dnaId()
         * .jurisdiction()
         * .ownerId()
         * .ownerName()
         * .publicKeyHex()
         * .status()
         * .updatedAt()
         * .version()
         * ```
         *
         * @throws IllegalStateException if any required field is unset.
         */
        fun build(): AgentListResponse =
            AgentListResponse(
                checkRequired("agentName", agentName),
                checkRequired("capabilities", capabilities).map { it.toImmutable() },
                checkRequired("createdAt", createdAt),
                checkRequired("dnaId", dnaId),
                checkRequired("jurisdiction", jurisdiction),
                checkRequired("ownerId", ownerId),
                checkRequired("ownerName", ownerName),
                checkRequired("publicKeyHex", publicKeyHex),
                checkRequired("status", status),
                checkRequired("updatedAt", updatedAt),
                checkRequired("version", version),
                approvalReason,
                approvedAt,
                approvedBy,
                expiresAt,
                parentDnaId,
                provenanceRef,
                revokedAt,
                revokedReason,
                workloadIdentity,
                additionalProperties.toMutableMap(),
            )
    }

    private var validated: Boolean = false

    fun validate(): AgentListResponse = apply {
        if (validated) {
            return@apply
        }

        agentName()
        capabilities()
        createdAt()
        dnaId()
        jurisdiction()
        ownerId()
        ownerName()
        publicKeyHex()
        status().validate()
        updatedAt()
        version()
        approvalReason()
        approvedAt()
        approvedBy()
        expiresAt()
        parentDnaId()
        provenanceRef()
        revokedAt()
        revokedReason()
        workloadIdentity()
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
            (if (createdAt.asKnown().isPresent) 1 else 0) +
            (if (dnaId.asKnown().isPresent) 1 else 0) +
            (if (jurisdiction.asKnown().isPresent) 1 else 0) +
            (if (ownerId.asKnown().isPresent) 1 else 0) +
            (if (ownerName.asKnown().isPresent) 1 else 0) +
            (if (publicKeyHex.asKnown().isPresent) 1 else 0) +
            (status.asKnown().getOrNull()?.validity() ?: 0) +
            (if (updatedAt.asKnown().isPresent) 1 else 0) +
            (if (version.asKnown().isPresent) 1 else 0) +
            (if (approvalReason.asKnown().isPresent) 1 else 0) +
            (if (approvedAt.asKnown().isPresent) 1 else 0) +
            (if (approvedBy.asKnown().isPresent) 1 else 0) +
            (if (expiresAt.asKnown().isPresent) 1 else 0) +
            (if (parentDnaId.asKnown().isPresent) 1 else 0) +
            (if (provenanceRef.asKnown().isPresent) 1 else 0) +
            (if (revokedAt.asKnown().isPresent) 1 else 0) +
            (if (revokedReason.asKnown().isPresent) 1 else 0) +
            (if (workloadIdentity.asKnown().isPresent) 1 else 0)

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return other is AgentListResponse &&
            agentName == other.agentName &&
            capabilities == other.capabilities &&
            createdAt == other.createdAt &&
            dnaId == other.dnaId &&
            jurisdiction == other.jurisdiction &&
            ownerId == other.ownerId &&
            ownerName == other.ownerName &&
            publicKeyHex == other.publicKeyHex &&
            status == other.status &&
            updatedAt == other.updatedAt &&
            version == other.version &&
            approvalReason == other.approvalReason &&
            approvedAt == other.approvedAt &&
            approvedBy == other.approvedBy &&
            expiresAt == other.expiresAt &&
            parentDnaId == other.parentDnaId &&
            provenanceRef == other.provenanceRef &&
            revokedAt == other.revokedAt &&
            revokedReason == other.revokedReason &&
            workloadIdentity == other.workloadIdentity &&
            additionalProperties == other.additionalProperties
    }

    private val hashCode: Int by lazy {
        Objects.hash(
            agentName,
            capabilities,
            createdAt,
            dnaId,
            jurisdiction,
            ownerId,
            ownerName,
            publicKeyHex,
            status,
            updatedAt,
            version,
            approvalReason,
            approvedAt,
            approvedBy,
            expiresAt,
            parentDnaId,
            provenanceRef,
            revokedAt,
            revokedReason,
            workloadIdentity,
            additionalProperties,
        )
    }

    override fun hashCode(): Int = hashCode

    override fun toString() =
        "AgentListResponse{agentName=$agentName, capabilities=$capabilities, createdAt=$createdAt, dnaId=$dnaId, jurisdiction=$jurisdiction, ownerId=$ownerId, ownerName=$ownerName, publicKeyHex=$publicKeyHex, status=$status, updatedAt=$updatedAt, version=$version, approvalReason=$approvalReason, approvedAt=$approvedAt, approvedBy=$approvedBy, expiresAt=$expiresAt, parentDnaId=$parentDnaId, provenanceRef=$provenanceRef, revokedAt=$revokedAt, revokedReason=$revokedReason, workloadIdentity=$workloadIdentity, additionalProperties=$additionalProperties}"
}
