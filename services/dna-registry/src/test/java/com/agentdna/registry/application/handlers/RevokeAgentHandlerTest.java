package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.commands.RevokeAgentCommand;
import com.agentdna.registry.domain.AgentRecord;
import com.agentdna.registry.domain.AgentStatus;
import com.agentdna.registry.domain.statemachine.AgentStateTransitionException;
import com.agentdna.registry.fixtures.AgentFixtures;
import com.agentdna.registry.infrastructure.events.OutboxPublisher;
import com.agentdna.registry.infrastructure.idempotency.IdempotencyStore;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RevokeAgentHandlerTest {

    // Distinct IDs so parent and child don't collide
    static final UUID PARENT_ID = AgentFixtures.DNA_ID;
    static final UUID CHILD_ID  = UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd");

    @Mock AgentRepository  repository;
    @Mock IdempotencyStore idempotency;
    @Mock OutboxPublisher  outbox;

    RevokeAgentHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new RevokeAgentHandler(repository, idempotency, outbox);
        when(idempotency.getOrExecute(any(), any(), any()))
                .thenAnswer(inv -> ((Supplier<?>) inv.getArgument(2)).get());
        when(repository.findByParentDnaId(any())).thenReturn(List.of());
    }

    @Test
    void handle_activeToRevoked_succeeds() {
        when(repository.findById(PARENT_ID)).thenReturn(AgentFixtures.active());
        var result = handler.handle(revokeCmd("idem-rev-1"));
        assertThat(result.previousStatus()).isEqualTo(AgentStatus.ACTIVE);
        assertThat(result.newStatus()).isEqualTo(AgentStatus.REVOKED);
    }

    @Test
    void handle_suspendedToRevoked_succeeds() {
        when(repository.findById(PARENT_ID)).thenReturn(AgentFixtures.suspended());
        var result = handler.handle(revokeCmd("idem-rev-2"));
        assertThat(result.previousStatus()).isEqualTo(AgentStatus.SUSPENDED);
        assertThat(result.newStatus()).isEqualTo(AgentStatus.REVOKED);
    }

    @Test
    void handle_activeToRevoked_callsRevoke() {
        when(repository.findById(PARENT_ID)).thenReturn(AgentFixtures.active());
        handler.handle(revokeCmd("idem-rev-3"));
        verify(repository).revoke(PARENT_ID, AgentStatus.ACTIVE, "Reason", 1);
    }

    @Test
    void handle_activeToRevoked_publishesEvent() {
        when(repository.findById(PARENT_ID)).thenReturn(AgentFixtures.active());
        handler.handle(revokeCmd("idem-rev-4"));
        verify(outbox).publish(eq("AGENT_REVOKED"), eq(PARENT_ID), any());
    }

    @Test
    void handle_alreadyRevoked_throwsStateTransitionException() {
        when(repository.findById(PARENT_ID)).thenReturn(AgentFixtures.revoked());
        assertThatThrownBy(() -> handler.handle(revokeCmd("idem-rev-5")))
                .isInstanceOf(AgentStateTransitionException.class);
    }

    // -- cascade --

    @Test
    void handle_revokeParent_cascadesToChildren() {
        var now   = Instant.now();
        var child = new AgentRecord(
                CHILD_ID, PARENT_ID, "ChildWorker", "deadbeef", "svc://child-worker", "build://fixture/child",
                AgentFixtures.OWNER_ID, "TurfOS", "ZA",
                List.of("mcp:filesystem"), AgentStatus.ACTIVE,
                now, now, now.minusSeconds(10), "GovTeam", "Fixture approval",
                null, null, null, 1);

        when(repository.findById(PARENT_ID)).thenReturn(AgentFixtures.active());
        when(repository.findByParentDnaId(PARENT_ID)).thenReturn(List.of(child));

        handler.handle(revokeCmd("idem-cascade-1"));

        verify(repository).revoke(eq(PARENT_ID), eq(AgentStatus.ACTIVE), eq("Reason"), eq(1));
        verify(repository).revoke(eq(CHILD_ID), eq(AgentStatus.ACTIVE), contains("cascade"), eq(1));
        verify(outbox).publish(eq("AGENT_REVOKED"), eq(PARENT_ID), any());
        verify(outbox).publish(eq("AGENT_REVOKED"), eq(CHILD_ID), any());
    }

    @Test
    void handle_revokeParent_alreadyRevokedChild_skipped() {
        var now          = Instant.now();
        var revokedChild = new AgentRecord(
                CHILD_ID, PARENT_ID, "ChildWorker", "deadbeef", "svc://child-worker", "build://fixture/child",
                AgentFixtures.OWNER_ID, "TurfOS", "ZA",
                List.of("mcp:filesystem"), AgentStatus.REVOKED,
                now, now, now.minusSeconds(10), "GovTeam", "Fixture approval",
                now, "Already gone", null, 2);

        when(repository.findById(PARENT_ID)).thenReturn(AgentFixtures.active());
        when(repository.findByParentDnaId(PARENT_ID)).thenReturn(List.of(revokedChild));

        handler.handle(revokeCmd("idem-cascade-2"));

        // Only the parent is revoked; already-revoked child is skipped
        verify(repository, times(1)).revoke(any(), any(), any(), anyInt());
    }

    // -- helpers --

    private RevokeAgentCommand revokeCmd(String idemKey) {
        return new RevokeAgentCommand(PARENT_ID, "Reason", idemKey);
    }
}
