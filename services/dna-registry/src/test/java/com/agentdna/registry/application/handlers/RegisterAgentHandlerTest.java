package com.agentdna.registry.application.handlers;

import com.agentdna.registry.application.commands.RegisterAgentCommand;
import com.agentdna.registry.application.results.RegisterAgentResult;
import com.agentdna.registry.domain.AgentStatus;
import com.agentdna.registry.fixtures.AgentFixtures;
import com.agentdna.registry.infrastructure.events.OutboxPublisher;
import com.agentdna.registry.infrastructure.idempotency.IdempotencyStore;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterAgentHandlerTest {

    @Mock AgentRepository  repository;
    @Mock IdempotencyStore idempotency;
    @Mock OutboxPublisher  outbox;

    RegisterAgentHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new RegisterAgentHandler(repository, idempotency, outbox);
        // Stub idempotency to immediately execute the Supplier
        when(idempotency.getOrExecute(any(), any(), any()))
                .thenAnswer(inv -> ((Supplier<?>) inv.getArgument(2)).get());
    }

    @Test
    void handle_createsAgentInPendingStatus() {
        var result = handler.handle(registerCommand());

        assertThat(result.agentName()).isEqualTo("ClaudeAgent");
        assertThat(result.status()).isEqualTo(AgentStatus.PENDING);
        assertThat(result.dnaId()).isNotNull();
    }

    @Test
    void handle_savesRecordToRepository() {
        handler.handle(registerCommand());

        verify(repository).save(any());
    }

    @Test
    void handle_publishesRegisteredEvent() {
        handler.handle(registerCommand());

        verify(outbox).publish(eq("AGENT_REGISTERED"), any(), any());
    }

    @Test
    void handle_idempotencyCacheHit_skipsExecution() {
        var cached = new RegisterAgentResult(UUID.randomUUID(), "ClaudeAgent", AgentStatus.PENDING);

        // Use doReturn to avoid triggering the existing thenAnswer stub during when() setup
        doReturn(cached).when(idempotency).getOrExecute(any(), any(), any());

        var result = handler.handle(registerCommand());

        assertThat(result).isSameAs(cached);
        verify(repository, never()).save(any());
        verify(outbox,      never()).publish(any(), any(), any());
    }

    @Test
    void handle_withActiveParent_registersWorkerSuccessfully() {
        when(repository.findById(AgentFixtures.PARENT_ID)).thenReturn(AgentFixtures.activeParent());

        var result = handler.handle(workerCommand(AgentFixtures.PARENT_ID));

        assertThat(result.status()).isEqualTo(AgentStatus.PENDING);
        verify(repository).save(any());
    }

    @Test
    void handle_withInactiveParent_throwsIllegalStateException() {
        when(repository.findById(AgentFixtures.PARENT_ID)).thenReturn(AgentFixtures.revokedParent());

        assertThatThrownBy(() -> handler.handle(workerCommand(AgentFixtures.PARENT_ID)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not ACTIVE");
    }

    @Test
    void handle_withRevokedPublicKey_throwsIllegalStateException() {
        when(repository.existsRevokedPublicKeyHex("deadbeef0123")).thenReturn(true);

        assertThatThrownBy(() -> handler.handle(registerCommand()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("revoked lineage");
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private RegisterAgentCommand registerCommand() {
        return new RegisterAgentCommand(
                "ClaudeAgent", "deadbeef0123", "svc://claude-agent", "build://claude-agent/1",
                UUID.randomUUID(), "TurfOS", "ZA",
                List.of("mcp:filesystem"), null, null, "idem-001");
    }

    private RegisterAgentCommand workerCommand(UUID parentDnaId) {
        return new RegisterAgentCommand(
                "ClaudeWorker", "deadbeef0123", "svc://claude-worker", "build://claude-worker/1",
                UUID.randomUUID(), "TurfOS", "ZA",
                List.of("mcp:filesystem"), parentDnaId, null, "idem-worker-001");
    }
}
