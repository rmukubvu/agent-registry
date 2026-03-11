#!/usr/bin/env python3
"""
Demo: Rogue Agent — three attack scenarios, all blocked.

Scenario A: Fake DNA ID (never registered)
Scenario B: Registered but never activated (PENDING)
Scenario C: Suspended agent tries to use a restricted tool
"""
import uuid, sys
import requests

REGISTRY = "http://localhost:8081"
ENFORCER = "http://localhost:8082"
RED      = "\033[91m"
YELLOW   = "\033[93m"
CYAN     = "\033[96m"
BOLD     = "\033[1m"
RESET    = "\033[0m"
DIM      = "\033[2m"

def banner(text: str) -> None:
    print(f"\n{BOLD}{CYAN}── {text} {RESET}")

def blocked(text: str) -> None:
    print(f"  {RED}⛔ BLOCKED — {text}{RESET}")

def info(text: str) -> None:
    print(f"  {DIM}  {text}{RESET}")


def enforce(dna_id: str, tool: str) -> dict:
    r = requests.post(f"{ENFORCER}/v1/enforce",
                      json={"dnaId": dna_id, "toolName": tool, "toolPayload": {}})
    return r.json()


def main() -> None:

    # ── Scenario A: Completely fake DNA ID ────────────────────────────────────
    banner("Scenario A: Rogue agent with a fake DNA ID")
    fake_id = str(uuid.uuid4())
    info(f"Attempting tool call with made-up dnaId={fake_id}")
    result = enforce(fake_id, "mcp:filesystem:write")
    if not result["allowed"]:
        blocked(result.get("reason", "unknown"))
    else:
        print(f"  {RED}BUG: tool call should have been blocked!{RESET}")
        sys.exit(1)

    # ── Scenario B: Registered but not activated (PENDING) ───────────────────
    banner("Scenario B: Agent registered but never activated (PENDING)")
    owner_id = str(uuid.uuid4())
    r = requests.post(f"{REGISTRY}/v1/agents", json={
        "agentName":    "RogueAgent-Pending",
        "publicKeyHex": uuid.uuid4().hex + uuid.uuid4().hex,
        "workloadIdentity": "svc://demo/rogue-pending",
        "provenanceRef": "build://demo/rogue-pending",
        "ownerId":      owner_id,
        "ownerName":    "Dark Corp",
        "jurisdiction": "XX",
        "capabilities": ["mcp:filesystem"],
        "idemKey":      f"rogue-reg-{uuid.uuid4()}",
    })
    if r.status_code != 201:
        print(f"  {RED}Registration failed: {r.status_code}{RESET}")
        sys.exit(1)
    pending_id = r.json()["dnaId"]
    info(f"Registered (PENDING) dnaId={pending_id} — skipping activation intentionally")
    result = enforce(pending_id, "mcp:web-search")
    if not result["allowed"]:
        blocked(result.get("reason", "unknown"))
    else:
        print(f"  {RED}BUG: pending agent should have been blocked!{RESET}")
        sys.exit(1)

    # ── Scenario C: Active then suspended ─────────────────────────────────────
    banner("Scenario C: Agent is active, gets suspended, tool call blocked")
    r = requests.post(f"{REGISTRY}/v1/agents", json={
        "agentName":    "RogueAgent-Suspended",
        "publicKeyHex": uuid.uuid4().hex + uuid.uuid4().hex,
        "workloadIdentity": "svc://demo/rogue-suspended",
        "provenanceRef": "build://demo/rogue-suspended",
        "ownerId":      str(uuid.uuid4()),
        "ownerName":    "Shadow AI",
        "jurisdiction": "RU",
        "capabilities": ["mcp:filesystem"],
        "idemKey":      f"rogue-sus-reg-{uuid.uuid4()}",
    })
    sus_id = r.json()["dnaId"]
    requests.put(f"{REGISTRY}/v1/agents/{sus_id}/activate", json={
        "idemKey": f"rogue-sus-act-{uuid.uuid4()}",
        "approvedBy": "Demo Governance Team",
        "approvalReason": "Approve suspended-path setup for demo",
    })
    info(f"Agent activated (dnaId={sus_id})")
    requests.put(f"{REGISTRY}/v1/agents/{sus_id}/suspend",
                 json={"reason": "Suspicious exfiltration attempt",
                       "idemKey": f"rogue-sus-sus-{uuid.uuid4()}"})
    info("Agent suspended by registry operator")
    result = enforce(sus_id, "mcp:filesystem:write")
    if not result["allowed"]:
        blocked(result.get("reason", "unknown"))
    else:
        print(f"  {RED}BUG: suspended agent should have been blocked!{RESET}")
        sys.exit(1)

    print(f"\n{YELLOW}{BOLD}✓ All rogue agent scenarios blocked successfully.{RESET}\n")


if __name__ == "__main__":
    main()
