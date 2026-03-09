package com.agentdna.enforcer.application;

import com.agentdna.enforcer.api.rest.EnforceRequest;
import com.agentdna.enforcer.api.rest.EnforceResponse;
import com.agentdna.enforcer.infrastructure.registry.DnaRegistryClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

@ApplicationScoped
public class EnforcerService {

    private static final Logger log = LoggerFactory.getLogger(EnforcerService.class);

    private final DnaRegistryClient registryClient;
    private final SignatureVerifier  signatureVerifier;

    @Inject
    public EnforcerService(DnaRegistryClient registryClient,
                           SignatureVerifier  signatureVerifier) {
        this.registryClient   = registryClient;
        this.signatureVerifier = signatureVerifier;
    }

    public EnforceResponse enforce(EnforceRequest request) {
        try {
            var verification = registryClient.verify(request.dnaId());

            if (!verification.isAuthorized()) {
                // Use the registry's specific denial reason (expiry, cascade, status)
                var reason = verification.deniedReason() != null
                        ? verification.deniedReason()
                        : "Agent not authorized — status: " + verification.status();
                log.warn("DENIED dnaId={} tool={} reason={}", request.dnaId(), request.toolName(), reason);
                return EnforceResponse.denied(
                        request.dnaId(), verification.agentName(), request.toolName(), reason);
            }

            // Optional Ed25519 signature verification — enforced when signature is present
            if (request.signature() != null && !request.signature().isBlank()) {
                boolean valid = signatureVerifier.verify(
                        verification.publicKeyHex(),
                        request.dnaId(), request.toolName(),
                        request.signature());
                if (!valid) {
                    var reason = "Invalid signature — request not authenticated";
                    log.warn("DENIED (bad sig) dnaId={} tool={}", request.dnaId(), request.toolName());
                    return EnforceResponse.denied(
                            request.dnaId(), verification.agentName(), request.toolName(), reason);
                }
                log.info("ALLOWED (sig verified) dnaId={} tool={}", request.dnaId(), request.toolName());
            } else {
                log.info("ALLOWED dnaId={} tool={}", request.dnaId(), request.toolName());
            }

            return EnforceResponse.allowed(
                    request.dnaId(), verification.agentName(), request.toolName());

        } catch (NoSuchElementException e) {
            log.warn("DENIED dnaId={} tool={} — not in registry", request.dnaId(), request.toolName());
            return EnforceResponse.denied(
                    request.dnaId(), null, request.toolName(),
                    "Agent DNA not found in registry");
        }
    }
}
