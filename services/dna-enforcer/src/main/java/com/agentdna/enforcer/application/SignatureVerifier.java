package com.agentdna.enforcer.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.HexFormat;
import java.util.UUID;

/**
 * Ed25519 signature verifier — no third-party crypto libraries required.
 *
 * <p>Signed message: UTF-8 bytes of "{dnaId}:{toolName}"
 *
 * <p>Public keys in the registry are stored as hex-encoded raw 32-byte Ed25519 keys.
 * Java's KeyFactory requires SubjectPublicKeyInfo (DER) format, so we prepend the
 * fixed 12-byte OID prefix for Ed25519 (OID 1.3.101.112) before parsing.
 */
@ApplicationScoped
public class SignatureVerifier {

    private static final Logger log = LoggerFactory.getLogger(SignatureVerifier.class);

    // DER SubjectPublicKeyInfo prefix for Ed25519 (OID 1.3.101.112):
    //   30 2a  SEQUENCE
    //     30 05  SEQUENCE
    //       06 03 2b 65 70  OID 1.3.101.112
    //     03 21 00  BIT STRING (33 bytes incl. padding)
    // + 32 bytes raw key = 44 bytes total
    private static final byte[] ED25519_DER_PREFIX =
            HexFormat.of().parseHex("302a300506032b6570032100");

    /**
     * Returns {@code true} iff {@code signatureHex} is a valid Ed25519 signature
     * over "{@code dnaId}:{@code toolName}" by the private key paired with
     * {@code publicKeyHex} (raw 32-byte hex).
     */
    public boolean verify(String publicKeyHex, UUID dnaId, String toolName, String signatureHex) {
        try {
            byte[] rawKey  = HexFormat.of().parseHex(publicKeyHex);
            byte[] sigBytes = HexFormat.of().parseHex(signatureHex);
            byte[] message = (dnaId + ":" + toolName).getBytes(StandardCharsets.UTF_8);

            var keySpec    = new X509EncodedKeySpec(buildDerKey(rawKey));
            var keyFactory = KeyFactory.getInstance("Ed25519");
            var pubKey     = keyFactory.generatePublic(keySpec);

            var sig = Signature.getInstance("Ed25519");
            sig.initVerify(pubKey);
            sig.update(message);
            return sig.verify(sigBytes);
        } catch (Exception e) {
            log.warn("Signature verification failed: {}", e.getMessage());
            return false;
        }
    }

    private byte[] buildDerKey(byte[] rawKey) {
        var der = new byte[ED25519_DER_PREFIX.length + rawKey.length];
        System.arraycopy(ED25519_DER_PREFIX, 0, der, 0, ED25519_DER_PREFIX.length);
        System.arraycopy(rawKey,             0, der, ED25519_DER_PREFIX.length, rawKey.length);
        return der;
    }
}
