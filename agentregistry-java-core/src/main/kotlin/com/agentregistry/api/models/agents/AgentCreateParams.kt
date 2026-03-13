// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.models.agents

import com.agentregistry.api.core.ExcludeMissing
import com.agentregistry.api.core.JsonField
import com.agentregistry.api.core.JsonMissing
import com.agentregistry.api.core.JsonValue
import com.agentregistry.api.core.Params
import com.agentregistry.api.core.checkKnown
import com.agentregistry.api.core.checkRequired
import com.agentregistry.api.core.http.Headers
import com.agentregistry.api.core.http.QueryParams
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

/** Register a new agent */
class AgentCreateParams
private constructor(
    private val body: Body,
    private val additionalHeaders: Headers,
    private val additionalQueryParams: QueryParams,
) : Params {

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun agentName(): String = body.agentName()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun capabilities(): List<String> = body.capabilities()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun idemKey(): String = body.idemKey()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun jurisdiction(): String = body.jurisdiction()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun ownerId(): String = body.ownerId()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun ownerName(): String = body.ownerName()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type or is
     *   unexpectedly missing or null (e.g. if the server responded with an unexpected value).
     */
    fun publicKeyHex(): String = body.publicKeyHex()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun expiresAt(): Optional<OffsetDateTime> = body.expiresAt()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun parentDnaId(): Optional<String> = body.parentDnaId()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun provenanceRef(): Optional<String> = body.provenanceRef()

    /**
     * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g. if
     *   the server responded with an unexpected value).
     */
    fun workloadIdentity(): Optional<String> = body.workloadIdentity()

    /**
     * Returns the raw JSON value of [agentName].
     *
     * Unlike [agentName], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _agentName(): JsonField<String> = body._agentName()

    /**
     * Returns the raw JSON value of [capabilities].
     *
     * Unlike [capabilities], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _capabilities(): JsonField<List<String>> = body._capabilities()

    /**
     * Returns the raw JSON value of [idemKey].
     *
     * Unlike [idemKey], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _idemKey(): JsonField<String> = body._idemKey()

    /**
     * Returns the raw JSON value of [jurisdiction].
     *
     * Unlike [jurisdiction], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _jurisdiction(): JsonField<String> = body._jurisdiction()

    /**
     * Returns the raw JSON value of [ownerId].
     *
     * Unlike [ownerId], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _ownerId(): JsonField<String> = body._ownerId()

    /**
     * Returns the raw JSON value of [ownerName].
     *
     * Unlike [ownerName], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _ownerName(): JsonField<String> = body._ownerName()

    /**
     * Returns the raw JSON value of [publicKeyHex].
     *
     * Unlike [publicKeyHex], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _publicKeyHex(): JsonField<String> = body._publicKeyHex()

    /**
     * Returns the raw JSON value of [expiresAt].
     *
     * Unlike [expiresAt], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _expiresAt(): JsonField<OffsetDateTime> = body._expiresAt()

    /**
     * Returns the raw JSON value of [parentDnaId].
     *
     * Unlike [parentDnaId], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _parentDnaId(): JsonField<String> = body._parentDnaId()

    /**
     * Returns the raw JSON value of [provenanceRef].
     *
     * Unlike [provenanceRef], this method doesn't throw if the JSON field has an unexpected type.
     */
    fun _provenanceRef(): JsonField<String> = body._provenanceRef()

    /**
     * Returns the raw JSON value of [workloadIdentity].
     *
     * Unlike [workloadIdentity], this method doesn't throw if the JSON field has an unexpected
     * type.
     */
    fun _workloadIdentity(): JsonField<String> = body._workloadIdentity()

    fun _additionalBodyProperties(): Map<String, JsonValue> = body._additionalProperties()

    /** Additional headers to send with the request. */
    fun _additionalHeaders(): Headers = additionalHeaders

    /** Additional query param to send with the request. */
    fun _additionalQueryParams(): QueryParams = additionalQueryParams

    fun toBuilder() = Builder().from(this)

    companion object {

        /**
         * Returns a mutable builder for constructing an instance of [AgentCreateParams].
         *
         * The following fields are required:
         * ```java
         * .agentName()
         * .capabilities()
         * .idemKey()
         * .jurisdiction()
         * .ownerId()
         * .ownerName()
         * .publicKeyHex()
         * ```
         */
        @JvmStatic fun builder() = Builder()
    }

    /** A builder for [AgentCreateParams]. */
    class Builder internal constructor() {

        private var body: Body.Builder = Body.builder()
        private var additionalHeaders: Headers.Builder = Headers.builder()
        private var additionalQueryParams: QueryParams.Builder = QueryParams.builder()

        @JvmSynthetic
        internal fun from(agentCreateParams: AgentCreateParams) = apply {
            body = agentCreateParams.body.toBuilder()
            additionalHeaders = agentCreateParams.additionalHeaders.toBuilder()
            additionalQueryParams = agentCreateParams.additionalQueryParams.toBuilder()
        }

        /**
         * Sets the entire request body.
         *
         * This is generally only useful if you are already constructing the body separately.
         * Otherwise, it's more convenient to use the top-level setters instead:
         * - [agentName]
         * - [capabilities]
         * - [idemKey]
         * - [jurisdiction]
         * - [ownerId]
         * - etc.
         */
        fun body(body: Body) = apply { this.body = body.toBuilder() }

        fun agentName(agentName: String) = apply { body.agentName(agentName) }

        /**
         * Sets [Builder.agentName] to an arbitrary JSON value.
         *
         * You should usually call [Builder.agentName] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun agentName(agentName: JsonField<String>) = apply { body.agentName(agentName) }

        fun capabilities(capabilities: List<String>) = apply { body.capabilities(capabilities) }

        /**
         * Sets [Builder.capabilities] to an arbitrary JSON value.
         *
         * You should usually call [Builder.capabilities] with a well-typed `List<String>` value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun capabilities(capabilities: JsonField<List<String>>) = apply {
            body.capabilities(capabilities)
        }

        /**
         * Adds a single [String] to [capabilities].
         *
         * @throws IllegalStateException if the field was previously set to a non-list.
         */
        fun addCapability(capability: String) = apply { body.addCapability(capability) }

        fun idemKey(idemKey: String) = apply { body.idemKey(idemKey) }

        /**
         * Sets [Builder.idemKey] to an arbitrary JSON value.
         *
         * You should usually call [Builder.idemKey] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun idemKey(idemKey: JsonField<String>) = apply { body.idemKey(idemKey) }

        fun jurisdiction(jurisdiction: String) = apply { body.jurisdiction(jurisdiction) }

        /**
         * Sets [Builder.jurisdiction] to an arbitrary JSON value.
         *
         * You should usually call [Builder.jurisdiction] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun jurisdiction(jurisdiction: JsonField<String>) = apply {
            body.jurisdiction(jurisdiction)
        }

        fun ownerId(ownerId: String) = apply { body.ownerId(ownerId) }

        /**
         * Sets [Builder.ownerId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.ownerId] with a well-typed [String] value instead. This
         * method is primarily for setting the field to an undocumented or not yet supported value.
         */
        fun ownerId(ownerId: JsonField<String>) = apply { body.ownerId(ownerId) }

        fun ownerName(ownerName: String) = apply { body.ownerName(ownerName) }

        /**
         * Sets [Builder.ownerName] to an arbitrary JSON value.
         *
         * You should usually call [Builder.ownerName] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun ownerName(ownerName: JsonField<String>) = apply { body.ownerName(ownerName) }

        fun publicKeyHex(publicKeyHex: String) = apply { body.publicKeyHex(publicKeyHex) }

        /**
         * Sets [Builder.publicKeyHex] to an arbitrary JSON value.
         *
         * You should usually call [Builder.publicKeyHex] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun publicKeyHex(publicKeyHex: JsonField<String>) = apply {
            body.publicKeyHex(publicKeyHex)
        }

        fun expiresAt(expiresAt: OffsetDateTime?) = apply { body.expiresAt(expiresAt) }

        /** Alias for calling [Builder.expiresAt] with `expiresAt.orElse(null)`. */
        fun expiresAt(expiresAt: Optional<OffsetDateTime>) = expiresAt(expiresAt.getOrNull())

        /**
         * Sets [Builder.expiresAt] to an arbitrary JSON value.
         *
         * You should usually call [Builder.expiresAt] with a well-typed [OffsetDateTime] value
         * instead. This method is primarily for setting the field to an undocumented or not yet
         * supported value.
         */
        fun expiresAt(expiresAt: JsonField<OffsetDateTime>) = apply { body.expiresAt(expiresAt) }

        fun parentDnaId(parentDnaId: String?) = apply { body.parentDnaId(parentDnaId) }

        /** Alias for calling [Builder.parentDnaId] with `parentDnaId.orElse(null)`. */
        fun parentDnaId(parentDnaId: Optional<String>) = parentDnaId(parentDnaId.getOrNull())

        /**
         * Sets [Builder.parentDnaId] to an arbitrary JSON value.
         *
         * You should usually call [Builder.parentDnaId] with a well-typed [String] value instead.
         * This method is primarily for setting the field to an undocumented or not yet supported
         * value.
         */
        fun parentDnaId(parentDnaId: JsonField<String>) = apply { body.parentDnaId(parentDnaId) }

        fun provenanceRef(provenanceRef: String?) = apply { body.provenanceRef(provenanceRef) }

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
            body.provenanceRef(provenanceRef)
        }

        fun workloadIdentity(workloadIdentity: String?) = apply {
            body.workloadIdentity(workloadIdentity)
        }

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
            body.workloadIdentity(workloadIdentity)
        }

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
         * Returns an immutable instance of [AgentCreateParams].
         *
         * Further updates to this [Builder] will not mutate the returned instance.
         *
         * The following fields are required:
         * ```java
         * .agentName()
         * .capabilities()
         * .idemKey()
         * .jurisdiction()
         * .ownerId()
         * .ownerName()
         * .publicKeyHex()
         * ```
         *
         * @throws IllegalStateException if any required field is unset.
         */
        fun build(): AgentCreateParams =
            AgentCreateParams(
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
        private val agentName: JsonField<String>,
        private val capabilities: JsonField<List<String>>,
        private val idemKey: JsonField<String>,
        private val jurisdiction: JsonField<String>,
        private val ownerId: JsonField<String>,
        private val ownerName: JsonField<String>,
        private val publicKeyHex: JsonField<String>,
        private val expiresAt: JsonField<OffsetDateTime>,
        private val parentDnaId: JsonField<String>,
        private val provenanceRef: JsonField<String>,
        private val workloadIdentity: JsonField<String>,
        private val additionalProperties: MutableMap<String, JsonValue>,
    ) {

        @JsonCreator
        private constructor(
            @JsonProperty("agentName")
            @ExcludeMissing
            agentName: JsonField<String> = JsonMissing.of(),
            @JsonProperty("capabilities")
            @ExcludeMissing
            capabilities: JsonField<List<String>> = JsonMissing.of(),
            @JsonProperty("idemKey") @ExcludeMissing idemKey: JsonField<String> = JsonMissing.of(),
            @JsonProperty("jurisdiction")
            @ExcludeMissing
            jurisdiction: JsonField<String> = JsonMissing.of(),
            @JsonProperty("ownerId") @ExcludeMissing ownerId: JsonField<String> = JsonMissing.of(),
            @JsonProperty("ownerName")
            @ExcludeMissing
            ownerName: JsonField<String> = JsonMissing.of(),
            @JsonProperty("publicKeyHex")
            @ExcludeMissing
            publicKeyHex: JsonField<String> = JsonMissing.of(),
            @JsonProperty("expiresAt")
            @ExcludeMissing
            expiresAt: JsonField<OffsetDateTime> = JsonMissing.of(),
            @JsonProperty("parentDnaId")
            @ExcludeMissing
            parentDnaId: JsonField<String> = JsonMissing.of(),
            @JsonProperty("provenanceRef")
            @ExcludeMissing
            provenanceRef: JsonField<String> = JsonMissing.of(),
            @JsonProperty("workloadIdentity")
            @ExcludeMissing
            workloadIdentity: JsonField<String> = JsonMissing.of(),
        ) : this(
            agentName,
            capabilities,
            idemKey,
            jurisdiction,
            ownerId,
            ownerName,
            publicKeyHex,
            expiresAt,
            parentDnaId,
            provenanceRef,
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
        fun idemKey(): String = idemKey.getRequired("idemKey")

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
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g.
         *   if the server responded with an unexpected value).
         */
        fun expiresAt(): Optional<OffsetDateTime> = expiresAt.getOptional("expiresAt")

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g.
         *   if the server responded with an unexpected value).
         */
        fun parentDnaId(): Optional<String> = parentDnaId.getOptional("parentDnaId")

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g.
         *   if the server responded with an unexpected value).
         */
        fun provenanceRef(): Optional<String> = provenanceRef.getOptional("provenanceRef")

        /**
         * @throws AgentregistryInvalidDataException if the JSON field has an unexpected type (e.g.
         *   if the server responded with an unexpected value).
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
         * Unlike [capabilities], this method doesn't throw if the JSON field has an unexpected
         * type.
         */
        @JsonProperty("capabilities")
        @ExcludeMissing
        fun _capabilities(): JsonField<List<String>> = capabilities

        /**
         * Returns the raw JSON value of [idemKey].
         *
         * Unlike [idemKey], this method doesn't throw if the JSON field has an unexpected type.
         */
        @JsonProperty("idemKey") @ExcludeMissing fun _idemKey(): JsonField<String> = idemKey

        /**
         * Returns the raw JSON value of [jurisdiction].
         *
         * Unlike [jurisdiction], this method doesn't throw if the JSON field has an unexpected
         * type.
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
         * Unlike [publicKeyHex], this method doesn't throw if the JSON field has an unexpected
         * type.
         */
        @JsonProperty("publicKeyHex")
        @ExcludeMissing
        fun _publicKeyHex(): JsonField<String> = publicKeyHex

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
        @JsonProperty("parentDnaId")
        @ExcludeMissing
        fun _parentDnaId(): JsonField<String> = parentDnaId

        /**
         * Returns the raw JSON value of [provenanceRef].
         *
         * Unlike [provenanceRef], this method doesn't throw if the JSON field has an unexpected
         * type.
         */
        @JsonProperty("provenanceRef")
        @ExcludeMissing
        fun _provenanceRef(): JsonField<String> = provenanceRef

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
             * Returns a mutable builder for constructing an instance of [Body].
             *
             * The following fields are required:
             * ```java
             * .agentName()
             * .capabilities()
             * .idemKey()
             * .jurisdiction()
             * .ownerId()
             * .ownerName()
             * .publicKeyHex()
             * ```
             */
            @JvmStatic fun builder() = Builder()
        }

        /** A builder for [Body]. */
        class Builder internal constructor() {

            private var agentName: JsonField<String>? = null
            private var capabilities: JsonField<MutableList<String>>? = null
            private var idemKey: JsonField<String>? = null
            private var jurisdiction: JsonField<String>? = null
            private var ownerId: JsonField<String>? = null
            private var ownerName: JsonField<String>? = null
            private var publicKeyHex: JsonField<String>? = null
            private var expiresAt: JsonField<OffsetDateTime> = JsonMissing.of()
            private var parentDnaId: JsonField<String> = JsonMissing.of()
            private var provenanceRef: JsonField<String> = JsonMissing.of()
            private var workloadIdentity: JsonField<String> = JsonMissing.of()
            private var additionalProperties: MutableMap<String, JsonValue> = mutableMapOf()

            @JvmSynthetic
            internal fun from(body: Body) = apply {
                agentName = body.agentName
                capabilities = body.capabilities.map { it.toMutableList() }
                idemKey = body.idemKey
                jurisdiction = body.jurisdiction
                ownerId = body.ownerId
                ownerName = body.ownerName
                publicKeyHex = body.publicKeyHex
                expiresAt = body.expiresAt
                parentDnaId = body.parentDnaId
                provenanceRef = body.provenanceRef
                workloadIdentity = body.workloadIdentity
                additionalProperties = body.additionalProperties.toMutableMap()
            }

            fun agentName(agentName: String) = agentName(JsonField.of(agentName))

            /**
             * Sets [Builder.agentName] to an arbitrary JSON value.
             *
             * You should usually call [Builder.agentName] with a well-typed [String] value instead.
             * This method is primarily for setting the field to an undocumented or not yet
             * supported value.
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

            fun idemKey(idemKey: String) = idemKey(JsonField.of(idemKey))

            /**
             * Sets [Builder.idemKey] to an arbitrary JSON value.
             *
             * You should usually call [Builder.idemKey] with a well-typed [String] value instead.
             * This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun idemKey(idemKey: JsonField<String>) = apply { this.idemKey = idemKey }

            fun jurisdiction(jurisdiction: String) = jurisdiction(JsonField.of(jurisdiction))

            /**
             * Sets [Builder.jurisdiction] to an arbitrary JSON value.
             *
             * You should usually call [Builder.jurisdiction] with a well-typed [String] value
             * instead. This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun jurisdiction(jurisdiction: JsonField<String>) = apply {
                this.jurisdiction = jurisdiction
            }

            fun ownerId(ownerId: String) = ownerId(JsonField.of(ownerId))

            /**
             * Sets [Builder.ownerId] to an arbitrary JSON value.
             *
             * You should usually call [Builder.ownerId] with a well-typed [String] value instead.
             * This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun ownerId(ownerId: JsonField<String>) = apply { this.ownerId = ownerId }

            fun ownerName(ownerName: String) = ownerName(JsonField.of(ownerName))

            /**
             * Sets [Builder.ownerName] to an arbitrary JSON value.
             *
             * You should usually call [Builder.ownerName] with a well-typed [String] value instead.
             * This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun ownerName(ownerName: JsonField<String>) = apply { this.ownerName = ownerName }

            fun publicKeyHex(publicKeyHex: String) = publicKeyHex(JsonField.of(publicKeyHex))

            /**
             * Sets [Builder.publicKeyHex] to an arbitrary JSON value.
             *
             * You should usually call [Builder.publicKeyHex] with a well-typed [String] value
             * instead. This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun publicKeyHex(publicKeyHex: JsonField<String>) = apply {
                this.publicKeyHex = publicKeyHex
            }

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
            fun expiresAt(expiresAt: JsonField<OffsetDateTime>) = apply {
                this.expiresAt = expiresAt
            }

            fun parentDnaId(parentDnaId: String?) = parentDnaId(JsonField.ofNullable(parentDnaId))

            /** Alias for calling [Builder.parentDnaId] with `parentDnaId.orElse(null)`. */
            fun parentDnaId(parentDnaId: Optional<String>) = parentDnaId(parentDnaId.getOrNull())

            /**
             * Sets [Builder.parentDnaId] to an arbitrary JSON value.
             *
             * You should usually call [Builder.parentDnaId] with a well-typed [String] value
             * instead. This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun parentDnaId(parentDnaId: JsonField<String>) = apply {
                this.parentDnaId = parentDnaId
            }

            fun provenanceRef(provenanceRef: String?) =
                provenanceRef(JsonField.ofNullable(provenanceRef))

            /** Alias for calling [Builder.provenanceRef] with `provenanceRef.orElse(null)`. */
            fun provenanceRef(provenanceRef: Optional<String>) =
                provenanceRef(provenanceRef.getOrNull())

            /**
             * Sets [Builder.provenanceRef] to an arbitrary JSON value.
             *
             * You should usually call [Builder.provenanceRef] with a well-typed [String] value
             * instead. This method is primarily for setting the field to an undocumented or not yet
             * supported value.
             */
            fun provenanceRef(provenanceRef: JsonField<String>) = apply {
                this.provenanceRef = provenanceRef
            }

            fun workloadIdentity(workloadIdentity: String?) =
                workloadIdentity(JsonField.ofNullable(workloadIdentity))

            /**
             * Alias for calling [Builder.workloadIdentity] with `workloadIdentity.orElse(null)`.
             */
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
             * Returns an immutable instance of [Body].
             *
             * Further updates to this [Builder] will not mutate the returned instance.
             *
             * The following fields are required:
             * ```java
             * .agentName()
             * .capabilities()
             * .idemKey()
             * .jurisdiction()
             * .ownerId()
             * .ownerName()
             * .publicKeyHex()
             * ```
             *
             * @throws IllegalStateException if any required field is unset.
             */
            fun build(): Body =
                Body(
                    checkRequired("agentName", agentName),
                    checkRequired("capabilities", capabilities).map { it.toImmutable() },
                    checkRequired("idemKey", idemKey),
                    checkRequired("jurisdiction", jurisdiction),
                    checkRequired("ownerId", ownerId),
                    checkRequired("ownerName", ownerName),
                    checkRequired("publicKeyHex", publicKeyHex),
                    expiresAt,
                    parentDnaId,
                    provenanceRef,
                    workloadIdentity,
                    additionalProperties.toMutableMap(),
                )
        }

        private var validated: Boolean = false

        fun validate(): Body = apply {
            if (validated) {
                return@apply
            }

            agentName()
            capabilities()
            idemKey()
            jurisdiction()
            ownerId()
            ownerName()
            publicKeyHex()
            expiresAt()
            parentDnaId()
            provenanceRef()
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
         * Returns a score indicating how many valid values are contained in this object
         * recursively.
         *
         * Used for best match union deserialization.
         */
        @JvmSynthetic
        internal fun validity(): Int =
            (if (agentName.asKnown().isPresent) 1 else 0) +
                (capabilities.asKnown().getOrNull()?.size ?: 0) +
                (if (idemKey.asKnown().isPresent) 1 else 0) +
                (if (jurisdiction.asKnown().isPresent) 1 else 0) +
                (if (ownerId.asKnown().isPresent) 1 else 0) +
                (if (ownerName.asKnown().isPresent) 1 else 0) +
                (if (publicKeyHex.asKnown().isPresent) 1 else 0) +
                (if (expiresAt.asKnown().isPresent) 1 else 0) +
                (if (parentDnaId.asKnown().isPresent) 1 else 0) +
                (if (provenanceRef.asKnown().isPresent) 1 else 0) +
                (if (workloadIdentity.asKnown().isPresent) 1 else 0)

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }

            return other is Body &&
                agentName == other.agentName &&
                capabilities == other.capabilities &&
                idemKey == other.idemKey &&
                jurisdiction == other.jurisdiction &&
                ownerId == other.ownerId &&
                ownerName == other.ownerName &&
                publicKeyHex == other.publicKeyHex &&
                expiresAt == other.expiresAt &&
                parentDnaId == other.parentDnaId &&
                provenanceRef == other.provenanceRef &&
                workloadIdentity == other.workloadIdentity &&
                additionalProperties == other.additionalProperties
        }

        private val hashCode: Int by lazy {
            Objects.hash(
                agentName,
                capabilities,
                idemKey,
                jurisdiction,
                ownerId,
                ownerName,
                publicKeyHex,
                expiresAt,
                parentDnaId,
                provenanceRef,
                workloadIdentity,
                additionalProperties,
            )
        }

        override fun hashCode(): Int = hashCode

        override fun toString() =
            "Body{agentName=$agentName, capabilities=$capabilities, idemKey=$idemKey, jurisdiction=$jurisdiction, ownerId=$ownerId, ownerName=$ownerName, publicKeyHex=$publicKeyHex, expiresAt=$expiresAt, parentDnaId=$parentDnaId, provenanceRef=$provenanceRef, workloadIdentity=$workloadIdentity, additionalProperties=$additionalProperties}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        return other is AgentCreateParams &&
            body == other.body &&
            additionalHeaders == other.additionalHeaders &&
            additionalQueryParams == other.additionalQueryParams
    }

    override fun hashCode(): Int = Objects.hash(body, additionalHeaders, additionalQueryParams)

    override fun toString() =
        "AgentCreateParams{body=$body, additionalHeaders=$additionalHeaders, additionalQueryParams=$additionalQueryParams}"
}
