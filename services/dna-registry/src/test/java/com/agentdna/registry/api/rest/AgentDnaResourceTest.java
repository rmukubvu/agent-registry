package com.agentdna.registry.api.rest;

import com.agentdna.registry.application.commands.*;
import com.agentdna.registry.application.handlers.*;
import com.agentdna.registry.application.results.AgentTransitionResult;
import com.agentdna.registry.application.results.RegisterAgentResult;
import com.agentdna.registry.application.results.VerifyAgentResult;
import com.agentdna.registry.domain.AgentStatus;
import com.agentdna.registry.domain.statemachine.AgentStateTransitionException;
import com.agentdna.registry.fixtures.AgentFixtures;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import jakarta.ws.rs.ext.RuntimeDelegate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AgentDnaResourceTest {

    @BeforeAll
    static void initJaxRs() {
        RuntimeDelegate.setInstance(new StubRuntimeDelegate());
    }

    @Mock RegisterAgentHandler  registerHandler;
    @Mock ActivateAgentHandler  activateHandler;
    @Mock SuspendAgentHandler   suspendHandler;
    @Mock ReinstateAgentHandler reinstateHandler;
    @Mock RevokeAgentHandler    revokeHandler;
    @Mock VerifyAgentHandler    verifyHandler;
    @Mock AgentRepository       repository;

    AgentDnaResource resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource = new AgentDnaResource(
                registerHandler, activateHandler, suspendHandler,
                reinstateHandler, revokeHandler, verifyHandler, repository);
    }

    // -- POST /v1/agents --

    @Test
    void register_returns201WithResult() {
        var expected = new RegisterAgentResult(AgentFixtures.DNA_ID, "ClaudeAgent", AgentStatus.PENDING);
        when(registerHandler.handle(any(RegisterAgentCommand.class))).thenReturn(expected);

        // parentDnaId=null, expiresAt=null for a standard top-level agent
        var payload = new RegisterAgentPayload(
                "ClaudeAgent", "deadbeef", "svc://claude-agent", "build://claude-agent/1",
                AgentFixtures.OWNER_ID, "TurfOS", "ZA",
                List.of("mcp:filesystem"), null, null, "idem-001");

        var response = resource.register(payload);

        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(response.getEntity()).isEqualTo(expected);
    }

    // -- GET /v1/agents --

    @Test
    void listAll_returns200WithAgents() {
        when(repository.findAll()).thenReturn(List.of(AgentFixtures.active()));
        var response = resource.listAll();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat((List<?>) response.getEntity()).hasSize(1);
    }

    // -- GET /v1/agents/{dnaId}/verify --

    @Test
    void verify_activeAgent_returns200WithAuthorizedTrue() {
        var result = new VerifyAgentResult(
                AgentFixtures.DNA_ID, null, "TestAgent", "deadbeef",
                AgentStatus.ACTIVE, List.of("mcp:filesystem"), true, null);
        when(verifyHandler.handle(AgentFixtures.DNA_ID)).thenReturn(result);

        var response = resource.verify(AgentFixtures.DNA_ID);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((VerifyAgentResult) response.getEntity()).isAuthorized()).isTrue();
    }

    @Test
    void verify_revokedAgent_returns200WithAuthorizedFalse() {
        var result = new VerifyAgentResult(
                AgentFixtures.DNA_ID, null, "TestAgent", "deadbeef",
                AgentStatus.REVOKED, List.of(), false, "Agent not authorized — status: REVOKED");
        when(verifyHandler.handle(AgentFixtures.DNA_ID)).thenReturn(result);

        var response = resource.verify(AgentFixtures.DNA_ID);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((VerifyAgentResult) response.getEntity()).isAuthorized()).isFalse();
    }

    @Test
    void verify_unknownDnaId_returns404() {
        var unknown = UUID.fromString("99999999-9999-9999-9999-999999999999");
        when(verifyHandler.handle(unknown))
                .thenThrow(new NoSuchElementException("Agent not found: " + unknown));

        var response = resource.verify(unknown);

        assertThat(response.getStatus()).isEqualTo(404);
    }

    // -- PUT /v1/agents/{dnaId}/activate --

    @Test
    void activate_happyPath_returns200() {
        var result = new AgentTransitionResult(AgentFixtures.DNA_ID, AgentStatus.PENDING, AgentStatus.ACTIVE);
        when(activateHandler.handle(any(ActivateAgentCommand.class))).thenReturn(result);

        var payload = new ActivateAgentPayload("idem-act", "GovTeam", "Manual governance approval");
        assertThat(resource.activate(AgentFixtures.DNA_ID, null, null, null, payload).getStatus()).isEqualTo(200);
    }

    @Test
    void activate_invalidState_returns422() {
        when(activateHandler.handle(any(ActivateAgentCommand.class)))
                .thenThrow(new AgentStateTransitionException(AgentStatus.REVOKED, AgentStatus.ACTIVE));

        var payload = new ActivateAgentPayload("idem-act-bad", "GovTeam", "Manual governance approval");
        assertThat(resource.activate(AgentFixtures.DNA_ID, null, null, null, payload).getStatus()).isEqualTo(422);
    }

    @Test
    void activate_unknownAgent_returns404() {
        when(activateHandler.handle(any(ActivateAgentCommand.class)))
                .thenThrow(new NoSuchElementException("Agent not found"));

        var payload = new ActivateAgentPayload("idem-act-404", "GovTeam", "Manual governance approval");
        assertThat(resource.activate(UUID.randomUUID(), null, null, null, payload).getStatus()).isEqualTo(404);
    }

    @Test
    void activate_missingGovernanceMetadata_returns400() {
        when(activateHandler.handle(any(ActivateAgentCommand.class)))
                .thenThrow(new IllegalArgumentException("approvedBy is required for activation"));

        var payload = new ActivateAgentPayload("idem-act-400", "", "Manual governance approval");
        assertThat(resource.activate(AgentFixtures.DNA_ID, null, null, null, payload).getStatus()).isEqualTo(400);
    }

    // -- PUT /v1/agents/{dnaId}/revoke --

    @Test
    void revoke_happyPath_returns200() {
        var result = new AgentTransitionResult(AgentFixtures.DNA_ID, AgentStatus.ACTIVE, AgentStatus.REVOKED);
        when(revokeHandler.handle(any(RevokeAgentCommand.class))).thenReturn(result);

        var response = resource.revoke(AgentFixtures.DNA_ID, new RevokeAgentPayload("Spam", "idem-rev"));

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(((AgentTransitionResult) response.getEntity()).newStatus()).isEqualTo(AgentStatus.REVOKED);
    }

    @Test
    void revoke_alreadyRevoked_returns422() {
        when(revokeHandler.handle(any(RevokeAgentCommand.class)))
                .thenThrow(new AgentStateTransitionException(AgentStatus.REVOKED, AgentStatus.REVOKED));

        assertThat(resource.revoke(AgentFixtures.DNA_ID, new RevokeAgentPayload("Again", "idem-rev-bad"))
                .getStatus()).isEqualTo(422);
    }
}
