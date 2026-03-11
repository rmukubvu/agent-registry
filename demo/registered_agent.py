#!/usr/bin/env python3
"""
Demo: Registered Agent — full happy path.

1. Registers itself with the DNA registry
2. Activates the registration
3. Verifies it is ACTIVE (authorized)
4. Calls the enforcer to use a tool — ALLOWED
5. Gets revoked to demonstrate the revocation path
6. Tries the tool again — BLOCKED
"""
import uuid, sys
import requests

REGISTRY = "http://localhost:8081"
ENFORCER = "http://localhost:8082"
GREEN    = "\033[92m"
RED      = "\033[91m"
CYAN     = "\033[96m"
BOLD     = "\033[1m"
RESET    = "\033[0m"
DIM      = "\033[2m"

def banner(text: str) -> None:
    print(f"\n{BOLD}{CYAN}── {text} {RESET}")

def ok(text: str) -> None:
    print(f"  {GREEN}✓ {text}{RESET}")

def fail(text: str) -> None:
    print(f"  {RED}✗ {text}{RESET}")

def info(text: str) -> None:
    print(f"  {DIM}  {text}{RESET}")


def main() -> None:
    owner_id = str(uuid.uuid4())
    idem_reg = f"demo-register-{uuid.uuid4()}"
    public_key = uuid.uuid4().hex + uuid.uuid4().hex

    # ── Step 1: Register ──────────────────────────────────────────────────────
    banner("Step 1: Register agent with DNA registry")
    payload = {
        "agentName":    "ClaudeIntakeAgent",
        "publicKeyHex": public_key,
        "workloadIdentity": "svc://demo/claude-intake-agent",
        "provenanceRef": "build://demo/registered-agent",
        "ownerId":      owner_id,
        "ownerName":    "TurfOS Demo",
        "jurisdiction": "ZA",
        "capabilities": ["mcp:filesystem", "mcp:web-search"],
        "idemKey":      idem_reg,
    }
    r = requests.post(f"{REGISTRY}/v1/agents", json=payload)
    if r.status_code != 201:
        fail(f"Registration failed: {r.status_code} {r.text}")
        sys.exit(1)

    data    = r.json()
    dna_id  = data["dnaId"]
    ok(f"Registered — dnaId={dna_id}")
    info(f"status={data['status']}")

    # ── Step 2: Activate ──────────────────────────────────────────────────────
    banner("Step 2: Activate the agent")
    idem_act = f"demo-activate-{uuid.uuid4()}"
    r = requests.put(f"{REGISTRY}/v1/agents/{dna_id}/activate", json={
        "idemKey": idem_act,
        "approvedBy": "Demo Governance Team",
        "approvalReason": "Presentation approval for registered-agent flow",
    })
    if r.status_code != 200:
        fail(f"Activation failed: {r.status_code} {r.text}")
        sys.exit(1)

    data = r.json()
    ok(f"Activated — {data['previousStatus']} → {data['newStatus']}")

    # ── Step 3: Verify ────────────────────────────────────────────────────────
    banner("Step 3: Verify authorization status")
    r = requests.get(f"{REGISTRY}/v1/agents/{dna_id}/verify")
    data = r.json()
    if data["isAuthorized"]:
        ok(f"AUTHORIZED — status={data['status']}")
        info(f"capabilities={data['capabilities']}")
    else:
        fail(f"Not authorized — status={data['status']}")
        sys.exit(1)

    # ── Step 4: Call tool via enforcer ────────────────────────────────────────
    banner("Step 4: Call tool via DNA enforcer")
    tool_payload = {"dnaId": dna_id, "toolName": "mcp:filesystem:read",
                    "toolPayload": {"path": "/var/data/report.csv"}}
    r = requests.post(f"{ENFORCER}/v1/enforce", json=tool_payload)
    data = r.json()
    if data["allowed"]:
        ok(f"TOOL CALL ALLOWED — tool={data['toolName']}")
        info(f"agentName={data['agentName']}")
    else:
        fail(f"TOOL CALL BLOCKED — reason={data.get('reason')}")
        sys.exit(1)

    # ── Step 5: Revoke the agent ──────────────────────────────────────────────
    banner("Step 5: Revoke the agent (simulating misbehaviour)")
    idem_rev = f"demo-revoke-{uuid.uuid4()}"
    r = requests.put(f"{REGISTRY}/v1/agents/{dna_id}/revoke",
                     json={"reason": "Demo revocation", "idemKey": idem_rev})
    data = r.json()
    ok(f"Revoked — {data['previousStatus']} → {data['newStatus']}")

    # ── Step 6: Try tool again — should be BLOCKED ────────────────────────────
    banner("Step 6: Retry tool call — should now be BLOCKED")
    r = requests.post(f"{ENFORCER}/v1/enforce", json=tool_payload)
    data = r.json()
    if not data["allowed"]:
        ok(f"TOOL CALL BLOCKED as expected")
        info(f"reason={data.get('reason')}")
    else:
        fail("Tool call was allowed after revocation — this should not happen!")
        sys.exit(1)

    print(f"\n{GREEN}{BOLD}✓ Registered agent demo complete.{RESET}\n")


if __name__ == "__main__":
    main()
