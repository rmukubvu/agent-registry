package com.agentregistry.api.errors

open class AgentregistryException
@JvmOverloads
constructor(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)
