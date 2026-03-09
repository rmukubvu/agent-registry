package com.agentdna.enforcer.api.rest;

import com.agentdna.enforcer.application.EnforcerService;
import jakarta.ws.rs.ext.RuntimeDelegate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EnforcerResourceTest {

    static final UUID DNA_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    @BeforeAll
    static void initJaxRs() {
        RuntimeDelegate.setInstance(new StubRuntimeDelegate());
    }

    @Mock EnforcerService service;

    EnforcerResource resource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resource = new EnforcerResource(service);
    }

    @Test
    void enforce_allowed_returns200WithAllowedTrue() {
        var allowed = EnforceResponse.allowed(DNA_ID, "ClaudeAgent", "mcp:filesystem:read");
        when(service.enforce(any())).thenReturn(allowed);

        var response = resource.enforce(new EnforceRequest(DNA_ID, "mcp:filesystem:read", Map.of(), null));

        assertThat(response.getStatus()).isEqualTo(200);
        var body = (EnforceResponse) response.getEntity();
        assertThat(body.allowed()).isTrue();
        assertThat(body.agentName()).isEqualTo("ClaudeAgent");
    }

    @Test
    void enforce_denied_returns200WithAllowedFalse() {
        var denied = EnforceResponse.denied(
                DNA_ID, "RogueAgent", "mcp:filesystem:write", "Agent not authorized — status: REVOKED");
        when(service.enforce(any())).thenReturn(denied);

        var response = resource.enforce(new EnforceRequest(DNA_ID, "mcp:filesystem:write", Map.of(), null));

        assertThat(response.getStatus()).isEqualTo(200);
        var body = (EnforceResponse) response.getEntity();
        assertThat(body.allowed()).isFalse();
        assertThat(body.reason()).contains("REVOKED");
    }

    @Test
    void enforce_notFound_returns200WithDenied() {
        var denied = EnforceResponse.denied(
                DNA_ID, null, "mcp:web-search", "Agent DNA not found in registry");
        when(service.enforce(any())).thenReturn(denied);

        var response = resource.enforce(new EnforceRequest(DNA_ID, "mcp:web-search", Map.of(), null));

        assertThat(response.getStatus()).isEqualTo(200);
        var body = (EnforceResponse) response.getEntity();
        assertThat(body.allowed()).isFalse();
        assertThat(body.agentName()).isNull();
    }
}
