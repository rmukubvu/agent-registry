package com.agentregistry.api.errors

class AgentregistryInvalidDataException
@JvmOverloads
constructor(message: String? = null, cause: Throwable? = null) :
    AgentregistryException(message, cause)
