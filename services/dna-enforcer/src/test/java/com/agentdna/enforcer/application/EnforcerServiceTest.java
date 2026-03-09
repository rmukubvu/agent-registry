package com.agentdna.enforcer.application;

import com.agentdna.enforcer.api.rest.EnforceRequest;
import com.agentdna.enforcer.infrastructure.registry.AgentVerification;
import com.agentdna.enforcer.infrastructure.registry.DnaRegistryClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class EnforcerServiceTest {

    static final UUID DNA_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    @Mock DnaRegistryClient registryClient;
    @Mock SignatureVerifier  signatureVerifier;

    EnforcerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new EnforcerService(registryClient, signatureVerifier);
    }

    @Test
    void enforce_activeAgent_returnsAllowed() {
        when(registryClient.verify(DNA_ID)).thenReturn(activeVerification());
        var result = service.enforce(request());
        assertThat(result.allowed()).isTrue();
        assertThat(result.agentName()).isEqualTo("ClaudeAgent");
        assertThat(result.toolName()).isEqualTo("mcp:filesystem:read");
        assertThat(result.reason()).isNull();
    }

    @Test
    void enforce_suspendedAgent_returnsDenied() {
        when(registryClient.verify(DNA_ID)).thenReturn(suspendedVerification());
        var result = service.enforce(request());
        assertThat(result.allowed()).isFalse();
        assertThat(result.reason()).contains("SUSPENDED");
    }

    @Test
    void enforce_revokedAgent_returnsDenied() {
        when(registryClient.verify(DNA_ID)).thenReturn(revokedVerification());
        var result = service.enforce(request());
        assertThat(result.allowed()).isFalse();
        assertThat(result.reason()).contains("REVOKED");
    }

    @Test
    void enforce_unknownAgent_returnsDeniedWithNotFoundReason() {
        when(registryClient.verify(DNA_ID))
                .thenThrow(new NoSuchElementException("Agent not found: " + DNA_ID));
        var result = service.enforce(request());
        assertThat(result.allowed()).isFalse();
        assertThat(result.reason()).contains("not found");
        assertThat(result.agentName()).isNull();
    }

    @Test
    void enforce_activeAgent_preservesToolName() {
        when(registryClient.verify(DNA_ID)).thenReturn(activeVerification());
        var result = service.enforce(request());
        assertThat(result.toolName()).isEqualTo("mcp:filesystem:read");
        assertThat(result.dnaId()).isEqualTo(DNA_ID);
    }

    @Test
    void enforce_cascadeDeniedWorker_returnsDeniedWithCascadeReason() {
        when(registryClient.verify(DNA_ID)).thenReturn(cascadeDeniedVerification());
        var result = service.enforce(request());
        assertThat(result.allowed()).isFalse();
        assertThat(result.reason()).contains("cascade");
    }

    @Test
    void enforce_withValidSignature_returnsAllowed() {
        when(registryClient.verify(DNA_ID)).thenReturn(activeVerification());
        when(signatureVerifier.verify("deadbeef", DNA_ID, "mcp:filesystem:read", "abc123"))
                .thenReturn(true);
        var result = service.enforce(requestWithSig("abc123"));
        assertThat(result.allowed()).isTrue();
    }

    @Test
    void enforce_withInvalidSignature_returnsDenied() {
        when(registryClient.verify(DNA_ID)).thenReturn(activeVerification());
        when(signatureVerifier.verify("deadbeef", DNA_ID, "mcp:filesystem:read", "badsig"))
                .thenReturn(false);
        var result = service.enforce(requestWithSig("badsig"));
        assertThat(result.allowed()).isFalse();
        assertThat(result.reason()).contains("Invalid signature");
    }

    // -- helpers --

    private EnforceRequest request() {
        return new EnforceRequest(DNA_ID, "mcp:filesystem:read",
                Map.of("path", "/tmp/test.txt"), null);
    }

    private EnforceRequest requestWithSig(String sig) {
        return new EnforceRequest(DNA_ID, "mcp:filesystem:read",
                Map.of("path", "/tmp/test.txt"), sig);
    }

    private AgentVerification activeVerification() {
        return new AgentVerification(
                DNA_ID, null, "ClaudeAgent", "deadbeef",
                "ACTIVE", List.of("mcp:filesystem"), true, null);
    }

    private AgentVerification suspendedVerification() {
        return new AgentVerification(
                DNA_ID, null, "ClaudeAgent", "deadbeef",
                "SUSPENDED", List.of("mcp:filesystem"), false,
                "Agent not authorized — status: SUSPENDED");
    }

    private AgentVerification revokedVerification() {
        return new AgentVerification(
                DNA_ID, null, "ClaudeAgent", "deadbeef",
                "REVOKED", List.of("mcp:filesystem"), false,
                "Agent not authorized — status: REVOKED");
    }

    private AgentVerification cascadeDeniedVerification() {
        return new AgentVerification(
                DNA_ID, UUID.randomUUID(), "ClaudeWorker", "deadbeef",
                "ACTIVE", List.of("mcp:filesystem"), false,
                "Parent agent blocked — cascade deny (parent status: REVOKED)");
    }
}
