package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.commands.SuspendAgentCommand;
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

class SuspendAgentHandlerTest {

    @Mock AgentRepository  repository;
    @Mock IdempotencyStore idempotency;

    SuspendAgentHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new SuspendAgentHandler(repository, idempotency);
        when(idempotency.getOrExecute(any(), any(), any()))
                .thenAnswer(inv -> ((Supplier<?>) inv.getArgument(2)).get());
    }

    @Test
    void handle_activeToSuspended_succeeds() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.active());

        var result = handler.handle(new SuspendAgentCommand(
                AgentFixtures.DNA_ID, "Suspected misbehaviour", "idem-sus-1"));

        assertThat(result.previousStatus()).isEqualTo(AgentStatus.ACTIVE);
        assertThat(result.newStatus()).isEqualTo(AgentStatus.SUSPENDED);
    }

    @Test
    void handle_activeToSuspended_callsUpdateStatus() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.active());

        handler.handle(new SuspendAgentCommand(
                AgentFixtures.DNA_ID, "Policy violation", "idem-sus-2"));

        verify(repository).updateStatus(
                AgentFixtures.DNA_ID, AgentStatus.ACTIVE, AgentStatus.SUSPENDED, 1);
    }

    @Test
    void handle_alreadySuspended_throwsStateTransitionException() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.suspended());

        assertThatThrownBy(() ->
                handler.handle(new SuspendAgentCommand(
                        AgentFixtures.DNA_ID, "again", "idem-sus-3")))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    @Test
    void handle_revokedAgent_throwsStateTransitionException() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.revoked());

        assertThatThrownBy(() ->
                handler.handle(new SuspendAgentCommand(
                        AgentFixtures.DNA_ID, "late suspension", "idem-sus-4")))
                .isInstanceOf(AgentStateTransitionException.class);
    }
}
