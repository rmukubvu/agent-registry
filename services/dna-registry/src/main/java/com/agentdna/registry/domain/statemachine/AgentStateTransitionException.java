package com.agentdna.registry.domain.statemachine;

import com.agentdna.registry.domain.AgentStatus;

public class AgentStateTransitionException extends RuntimeException {

    public AgentStateTransitionException(AgentStatus from, AgentStatus to) {
        super("Invalid agent state transition: %s → %s".formatted(from, to));
    }
}
