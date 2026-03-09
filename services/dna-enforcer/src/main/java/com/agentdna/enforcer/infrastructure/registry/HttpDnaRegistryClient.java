package com.agentdna.enforcer.infrastructure.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;
import java.util.UUID;

@ApplicationScoped
public class HttpDnaRegistryClient implements DnaRegistryClient {

    private static final Logger log = LoggerFactory.getLogger(HttpDnaRegistryClient.class);

    private final String       registryUrl;
    private final ObjectMapper mapper;
    private final HttpClient   http;

    @Inject
    public HttpDnaRegistryClient(
            @ConfigProperty(name = "agentdna.registry.url") String registryUrl,
            ObjectMapper mapper) {
        this.registryUrl = registryUrl;
        this.mapper      = mapper;
        this.http        = HttpClient.newHttpClient();
    }

    @Override
    public AgentVerification verify(UUID dnaId) {
        var uri     = URI.create(registryUrl + "/v1/agents/" + dnaId + "/verify");
        var request = HttpRequest.newBuilder(uri).GET().build();
        try {
            var response = http.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                throw new NoSuchElementException("Agent not found in registry: " + dnaId);
            }
            log.debug("Registry verify dnaId={} status={}", dnaId, response.statusCode());
            return mapper.readValue(response.body(), AgentVerification.class);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Registry call failed for dnaId=" + dnaId, e);
        }
    }
}
