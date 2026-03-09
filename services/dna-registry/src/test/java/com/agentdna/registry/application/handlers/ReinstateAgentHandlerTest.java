package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.commands.ReinstateAgentCommand;
import com.agentdna.registry.domain.AgentStatus;
import com.agentdna.registry.domain.statemachine.AgentStateTransitionException;
import com.agentdna.registry.fixtures.AgentFixtures;
import com.agentdna.registry.infrastructure.idempotency.IdempotencyStore;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReinstateAgentHandlerTest {

    @Mock AgentRepository  repository;
    @Mock IdempotencyStore idempotency;

    ReinstateAgentHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new ReinstateAgentHandler(repository, idempotency);
        when(idempotency.getOrExecute(any(), any(), any()))
                .thenAnswer(inv -> ((Supplier<?>) inv.getArgument(2)).get());
    }

    @Test
    void handle_suspendedToActive_succeeds() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.suspended());

        var result = handler.handle(new ReinstateAgentCommand(AgentFixtures.DNA_ID, "idem-rei-1"));

        assertThat(result.previousStatus()).isEqualTo(AgentStatus.SUSPENDED);
        assertThat(result.newStatus()).isEqualTo(AgentStatus.ACTIVE);
    }

    @Test
    void handle_suspendedToActive_callsUpdateStatus() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.suspended());

        handler.handle(new ReinstateAgentCommand(AgentFixtures.DNA_ID, "idem-rei-2"));

        verify(repository).updateStatus(
                AgentFixtures.DNA_ID, AgentStatus.SUSPENDED, AgentStatus.ACTIVE, 1);
    }

    @Test
    void handle_revokedAgent_throwsStateTransitionException() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.revoked());

        assertThatThrownBy(() ->
                handler.handle(new ReinstateAgentCommand(AgentFixtures.DNA_ID, "idem-rei-3")))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    @Test
    void handle_activeAgent_throwsStateTransitionException() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.active());

        assertThatThrownBy(() ->
                handler.handle(new ReinstateAgentCommand(AgentFixtures.DNA_ID, "idem-rei-4")))
                .isInstanceOf(AgentStateTransitionException.class);
    }
}
