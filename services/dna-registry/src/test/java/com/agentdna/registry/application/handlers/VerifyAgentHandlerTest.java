package com.agentdna.registry.application.handlers;

import com.agentdna.registry.domain.AgentRecord;
import com.agentdna.registry.domain.AgentStatus;
import com.agentdna.registry.fixtures.AgentFixtures;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class VerifyAgentHandlerTest {

    @Mock AgentRepository repository;

    VerifyAgentHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new VerifyAgentHandler(repository);
    }

    // -- basic status checks --

    @Test
    void verify_activeAgent_returnsAuthorizedTrue() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.active());
        var result = handler.handle(AgentFixtures.DNA_ID);
        assertThat(result.isAuthorized()).isTrue();
        assertThat(result.agentName()).isEqualTo("TestAgent");
        assertThat(result.dnaId()).isEqualTo(AgentFixtures.DNA_ID);
        assertThat(result.deniedReason()).isNull();
    }

    @Test
    void verify_suspendedAgent_returnsAuthorizedFalse() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.suspended());
        var result = handler.handle(AgentFixtures.DNA_ID);
        assertThat(result.isAuthorized()).isFalse();
        assertThat(result.deniedReason()).contains("SUSPENDED");
    }

    @Test
    void verify_revokedAgent_returnsAuthorizedFalse() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.revoked());
        var result = handler.handle(AgentFixtures.DNA_ID);
        assertThat(result.isAuthorized()).isFalse();
        assertThat(result.deniedReason()).contains("REVOKED");
    }

    @Test
    void verify_pendingAgent_returnsAuthorizedFalse() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.pending());
        var result = handler.handle(AgentFixtures.DNA_ID);
        assertThat(result.isAuthorized()).isFalse();
        assertThat(result.deniedReason()).contains("PENDING");
    }

    @Test
    void verify_activeAgent_includesCapabilities() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.active());
        var result = handler.handle(AgentFixtures.DNA_ID);
        assertThat(result.capabilities()).containsExactly("mcp:filesystem");
    }

    // -- ephemeral TTL --

    @Test
    void verify_expiredWorker_returnsAuthorizedFalse() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.expiredWorker());
        var result = handler.handle(AgentFixtures.DNA_ID);
        assertThat(result.isAuthorized()).isFalse();
        assertThat(result.deniedReason()).contains("expired");
    }

    // -- parent cascade --

    @Test
    void verify_workerWithActiveParent_returnsAuthorizedTrue() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.activeWorker());
        when(repository.findById(AgentFixtures.PARENT_ID)).thenReturn(AgentFixtures.activeParent());
        var result = handler.handle(AgentFixtures.DNA_ID);
        assertThat(result.isAuthorized()).isTrue();
        assertThat(result.parentDnaId()).isEqualTo(AgentFixtures.PARENT_ID);
    }

    @Test
    void verify_workerWithRevokedParent_returnsCascadeDeny() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.activeWorker());
        when(repository.findById(AgentFixtures.PARENT_ID)).thenReturn(AgentFixtures.revokedParent());
        var result = handler.handle(AgentFixtures.DNA_ID);
        assertThat(result.isAuthorized()).isFalse();
        assertThat(result.deniedReason()).contains("cascade");
        assertThat(result.deniedReason()).contains("REVOKED");
    }

    @Test
    void verify_workerWithSuspendedParent_returnsCascadeDeny() {
        when(repository.findById(AgentFixtures.DNA_ID)).thenReturn(AgentFixtures.activeWorker());
        when(repository.findById(AgentFixtures.PARENT_ID)).thenReturn(suspendedParentFixture());
        var result = handler.handle(AgentFixtures.DNA_ID);
        assertThat(result.isAuthorized()).isFalse();
        assertThat(result.deniedReason()).contains("SUSPENDED");
    }

    private AgentRecord suspendedParentFixture() {
        var ref = AgentFixtures.activeParent();
        return new AgentRecord(
                AgentFixtures.PARENT_ID, null, "ParentManager", "cafebabe",
                AgentFixtures.OWNER_ID, "TurfOS", "ZA",
                List.of("mcp:filesystem"), AgentStatus.SUSPENDED,
                ref.createdAt(), ref.updatedAt(), null, null, null, 1);
    }
}
