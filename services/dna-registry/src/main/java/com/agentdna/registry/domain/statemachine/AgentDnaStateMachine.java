package com.agentdna.registry.domain.statemachine;

import com.agentdna.registry.domain.AgentStatus;

import java.util.Set;
import java.util.Map;

public final class AgentDnaStateMachine {

    private static final Map<AgentStatus, Set<AgentStatus>> ALLOWED =
            Map.of(
                AgentStatus.PENDING,   Set.of(AgentStatus.ACTIVE),
                AgentStatus.ACTIVE,    Set.of(AgentStatus.SUSPENDED, AgentStatus.REVOKED),
                AgentStatus.SUSPENDED, Set.of(AgentStatus.ACTIVE, AgentStatus.REVOKED),
                AgentStatus.REVOKED,   Set.of()
            );

    private AgentDnaStateMachine() {}

    public static void transition(AgentStatus from, AgentStatus to) {
        var allowed = ALLOWED.getOrDefault(from, Set.of());
        if (!allowed.contains(to)) {
            throw new AgentStateTransitionException(from, to);
        }
    }
}
