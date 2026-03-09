package com.agentdna.registry.domain.statemachine;

import com.agentdna.registry.domain.AgentStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AgentDnaStateMachineTest {

    // ── Valid transitions ─────────────────────────────────────────────────────

    @Test
    void pendingToActive_isAllowed() {
        assertThatCode(() -> AgentDnaStateMachine.transition(AgentStatus.PENDING, AgentStatus.ACTIVE))
                .doesNotThrowAnyException();
    }

    @Test
    void activeToSuspended_isAllowed() {
        assertThatCode(() -> AgentDnaStateMachine.transition(AgentStatus.ACTIVE, AgentStatus.SUSPENDED))
                .doesNotThrowAnyException();
    }

    @Test
    void activeToRevoked_isAllowed() {
        assertThatCode(() -> AgentDnaStateMachine.transition(AgentStatus.ACTIVE, AgentStatus.REVOKED))
                .doesNotThrowAnyException();
    }

    @Test
    void suspendedToActive_isAllowed() {
        assertThatCode(() -> AgentDnaStateMachine.transition(AgentStatus.SUSPENDED, AgentStatus.ACTIVE))
                .doesNotThrowAnyException();
    }

    @Test
    void suspendedToRevoked_isAllowed() {
        assertThatCode(() -> AgentDnaStateMachine.transition(AgentStatus.SUSPENDED, AgentStatus.REVOKED))
                .doesNotThrowAnyException();
    }

    // ── Invalid transitions ───────────────────────────────────────────────────

    @Test
    void pendingToSuspended_throws() {
        assertThatThrownBy(() -> AgentDnaStateMachine.transition(AgentStatus.PENDING, AgentStatus.SUSPENDED))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    @Test
    void pendingToRevoked_throws() {
        assertThatThrownBy(() -> AgentDnaStateMachine.transition(AgentStatus.PENDING, AgentStatus.REVOKED))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    @Test
    void activeToPending_throws() {
        assertThatThrownBy(() -> AgentDnaStateMachine.transition(AgentStatus.ACTIVE, AgentStatus.PENDING))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    @Test
    void suspendedToPending_throws() {
        assertThatThrownBy(() -> AgentDnaStateMachine.transition(AgentStatus.SUSPENDED, AgentStatus.PENDING))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    @Test
    void revokedToActive_throws() {
        assertThatThrownBy(() -> AgentDnaStateMachine.transition(AgentStatus.REVOKED, AgentStatus.ACTIVE))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    @Test
    void revokedToSuspended_throws() {
        assertThatThrownBy(() -> AgentDnaStateMachine.transition(AgentStatus.REVOKED, AgentStatus.SUSPENDED))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    @Test
    void revokedToPending_throws() {
        assertThatThrownBy(() -> AgentDnaStateMachine.transition(AgentStatus.REVOKED, AgentStatus.PENDING))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    @Test
    void exceptionMessage_containsFromAndTo() {
        assertThatThrownBy(() -> AgentDnaStateMachine.transition(AgentStatus.REVOKED, AgentStatus.ACTIVE))
                .isInstanceOf(AgentStateTransitionException.class)
                .hasMessageContaining("REVOKED")
                .hasMessageContaining("ACTIVE");
    }
}
