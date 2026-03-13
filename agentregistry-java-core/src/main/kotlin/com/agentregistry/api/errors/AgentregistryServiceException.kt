// File generated from our OpenAPI spec by Stainless.

package com.agentregistry.api.errors

import com.agentregistry.api.core.JsonValue
import com.agentregistry.api.core.http.Headers

abstract class AgentregistryServiceException
protected constructor(message: String, cause: Throwable? = null) :
    AgentregistryException(message, cause) {

    abstract fun statusCode(): Int

    abstract fun headers(): Headers

    abstract fun body(): JsonValue
}
