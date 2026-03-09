#!/usr/bin/env python3
"""
Demo: Hierarchical DNA — Delegated Identity, Cascade Revocation, Ephemeral Workers.

Scenario 1 — Manager sponsors a Worker
  1.  Register a Manager Agent (no parent)
  2.  Activate Manager
  3.  Register a Worker Agent (parentDnaId = Manager's dnaId)
  4.  Activate Worker
  5.  Verify Worker → AUTHORIZED (Manager is ACTIVE)
  6.  Worker calls a tool via enforcer → ALLOWED

Scenario 2 — Cascade Revocation
  7.  Revoke the Manager
  8.  Verify Worker → BLOCKED (cascade: parent is now REVOKED)
  9.  Worker tries the tool again → BLOCKED

Scenario 3 — Ephemeral Worker (TTL)
  10. Register a fresh Manager + ephemeral Worker (TTL = ~3 seconds)
  11. Verify Worker immediately → AUTHORIZED
  12. Wait for TTL to expire
  13. Verify Worker → BLOCKED (TTL expired)
"""
import uuid, sys, time
from datetime import datetime, timezone, timedelta

try:
    import requests
except ImportError:
    print("Install requests:  pip install requests")
    sys.exit(1)

REGISTRY = "http://localhost:8081"
ENFORCER = "http://localhost:8082"

GREEN  = "\033[92m"
RED    = "\033[91m"
YELLOW = "\033[93m"
CYAN   = "\033[96m"
PURPLE = "\033[95m"
BOLD   = "\033[1m"
DIM    = "\033[2m"
RESET  = "\033[0m"
HR     = "─" * 66


def banner(text: str) -> None:
    print(f"\n{BOLD}{CYAN}── {text} {RESET}")


def ok(text: str) -> None:
    print(f"  {GREEN}✓ {text}{RESET}")


def fail(text: str) -> None:
    print(f"  {RED}✗ {text}{RESET}")
    sys.exit(1)


def warn(text: str) -> None:
    print(f"  {YELLOW}⚠ {text}{RESET}")


def info(text: str) -> None:
    print(f"  {DIM}  {text}{RESET}")


def section(title: str) -> None:
    print(f"\n{BOLD}{PURPLE}╔{'═' * (len(title) + 4)}╗")
    print(f"║  {title}  ║")
    print(f"╚{'═' * (len(title) + 4)}╝{RESET}")


# ── Registry helpers ──────────────────────────────────────────────────────────

def register(agent_name: str, owner_id: str, parent_dna_id: str | None = None,
             expires_at: str | None = None) -> str:
    """Register an agent and return its dnaId."""
    payload = {
        "agentName":    agent_name,
        "publicKeyHex": "deadbeef" * 8,
        "ownerId":      owner_id,
        "ownerName":    "Hierarchical Demo",
        "jurisdiction": "ZA",
        "capabilities": ["mcp:filesystem", "mcp:web-search"],
        "idemKey":      f"hier-{uuid.uuid4()}",
    }
    if parent_dna_id:
        payload["parentDnaId"] = parent_dna_id
    if expires_at:
        payload["expiresAt"] = expires_at

    r = requests.post(f"{REGISTRY}/v1/agents", json=payload)
    if r.status_code != 201:
        fail(f"Registration of {agent_name} failed: {r.status_code} {r.text}")
    data = r.json()
    ok(f"Registered {agent_name} — dnaId={data['dnaId']}")
    if parent_dna_id:
        info(f"parentDnaId={parent_dna_id}")
    if expires_at:
        info(f"expiresAt={expires_at}")
    return data["dnaId"]


def activate(dna_id: str, label: str) -> None:
    r = requests.put(f"{REGISTRY}/v1/agents/{dna_id}/activate?idemKey=hier-act-{uuid.uuid4()}")
    if r.status_code != 200:
        fail(f"Activation of {label} failed: {r.status_code} {r.text}")
    data = r.json()
    ok(f"Activated {label} — {data['previousStatus']} → {data['newStatus']}")


def verify(dna_id: str, label: str) -> dict:
    r = requests.get(f"{REGISTRY}/v1/agents/{dna_id}/verify")
    if r.status_code != 200:
        fail(f"Verify call failed for {label}: {r.status_code} {r.text}")
    data = r.json()
    authorized = data["isAuthorized"]
    reason     = data.get("deniedReason") or data.get("status")
    if authorized:
        ok(f"AUTHORIZED   {label} — status={data['status']}")
    else:
        ok(f"NOT authorized {label} — {reason}")
    return data


def revoke(dna_id: str, label: str, reason: str) -> None:
    r = requests.put(
        f"{REGISTRY}/v1/agents/{dna_id}/revoke",
        json={"reason": reason, "idemKey": f"hier-rev-{uuid.uuid4()}"},
    )
    if r.status_code != 200:
        fail(f"Revoke of {label} failed: {r.status_code} {r.text}")
    data = r.json()
    ok(f"Revoked {label} — {data['previousStatus']} → {data['newStatus']}")
    info(f"reason={reason}")


def enforce(dna_id: str, tool: str, label: str) -> dict:
    r = requests.post(f"{ENFORCER}/v1/enforce",
                      json={"dnaId": dna_id, "toolName": tool, "toolPayload": {}})
    if r.status_code != 200:
        fail(f"Enforce call failed for {label}: {r.status_code} {r.text}")
    return r.json()


# ── Scenario 1 + 2: Manager / Worker + Cascade ───────────────────────────────

def scenario_cascade() -> None:
    section("Scenario 1 & 2 — Manager→Worker Chain + Cascade Revocation")
    owner_id = str(uuid.uuid4())

    # — Register + activate Manager —
    banner("Step 1: Register Manager Agent")
    mgr_id = register("ManagerAgent", owner_id)

    banner("Step 2: Activate Manager")
    activate(mgr_id, "ManagerAgent")

    # — Register + activate Worker under Manager —
    banner("Step 3: Register Worker Agent (parentDnaId = Manager)")
    wrk_id = register("WorkerAgent", owner_id, parent_dna_id=mgr_id)

    banner("Step 4: Activate Worker")
    activate(wrk_id, "WorkerAgent")

    # — Verify Worker is authorized —
    banner("Step 5: Verify Worker — Manager is ACTIVE ⇒ Worker should be AUTHORIZED")
    result = verify(wrk_id, "WorkerAgent")
    if not result["isAuthorized"]:
        fail("Expected Worker to be AUTHORIZED while Manager is ACTIVE")
    info(f"parentDnaId={result.get('parentDnaId')}")

    # — Worker calls a tool —
    banner("Step 6: Worker calls a tool via enforcer → should be ALLOWED")
    data = enforce(wrk_id, "mcp:filesystem:read", "WorkerAgent")
    if data["allowed"]:
        ok(f"TOOL CALL ALLOWED — tool={data['toolName']}")
        info(f"agentName={data.get('agentName')}")
    else:
        fail(f"Expected ALLOWED but got BLOCKED: {data.get('reason')}")

    print(f"\n  {DIM}{HR}{RESET}")

    # — Revoke Manager (cascade triggers) —
    banner("Step 7: Revoke Manager — cascade should block Worker automatically")
    revoke(mgr_id, "ManagerAgent", "Manager violated governance policy")

    # — Verify Worker is now blocked (cascade) —
    banner("Step 8: Verify Worker — Manager is REVOKED ⇒ Worker should be BLOCKED (cascade)")
    result = verify(wrk_id, "WorkerAgent")
    if result["isAuthorized"]:
        fail("Expected Worker to be BLOCKED after Manager was revoked")
    ok(f"Cascade deny confirmed — deniedReason: {result.get('deniedReason')}")

    # — Worker tries tool — should be blocked —
    banner("Step 9: Worker retries tool → should be BLOCKED by enforcer")
    data = enforce(wrk_id, "mcp:filesystem:read", "WorkerAgent")
    if not data["allowed"]:
        ok(f"TOOL CALL BLOCKED as expected")
        info(f"reason={data.get('reason')}")
    else:
        fail("Tool call was ALLOWED after Manager was revoked — this should not happen!")

    print(f"\n{GREEN}{BOLD}  ✓ Cascade revocation scenario complete.{RESET}")


# ── Scenario 3: Ephemeral Worker (TTL) ───────────────────────────────────────

def scenario_ttl() -> None:
    section("Scenario 3 — Ephemeral Worker (TTL / expiresAt)")
    owner_id = str(uuid.uuid4())
    ttl_secs = 4  # worker lives for 4 seconds

    # — Register + activate a fresh Manager —
    banner("Step 10: Register + activate a fresh Manager")
    mgr_id = register("EphemeralManager", owner_id)
    activate(mgr_id, "EphemeralManager")

    # — Register ephemeral Worker with short TTL —
    expires_at = (datetime.now(timezone.utc) + timedelta(seconds=ttl_secs)).isoformat()
    banner(f"Step 11: Register ephemeral Worker — TTL = {ttl_secs}s")
    wrk_id = register("EphemeralWorker", owner_id,
                      parent_dna_id=mgr_id, expires_at=expires_at)
    activate(wrk_id, "EphemeralWorker")

    # — Verify immediately — should be AUTHORIZED —
    banner("Step 12: Verify immediately — should be AUTHORIZED (TTL has not elapsed)")
    result = verify(wrk_id, "EphemeralWorker")
    if not result["isAuthorized"]:
        fail(f"Expected AUTHORIZED before TTL — got: {result.get('deniedReason')}")

    # — Wait for TTL to expire —
    banner(f"Step 13: Waiting {ttl_secs + 1}s for TTL to expire…")
    for i in range(ttl_secs + 1, 0, -1):
        print(f"  {DIM}  {i}s remaining…{RESET}", end='\r')
        time.sleep(1)
    print()

    # — Verify again — should be BLOCKED —
    banner("Step 14: Verify after TTL — should be BLOCKED")
    result = verify(wrk_id, "EphemeralWorker")
    if result["isAuthorized"]:
        fail("Expected BLOCKED after TTL expired but Worker is still AUTHORIZED")
    ok(f"TTL expiry confirmed — deniedReason: {result.get('deniedReason')}")

    print(f"\n{GREEN}{BOLD}  ✓ Ephemeral worker scenario complete.{RESET}")


# ── Main ──────────────────────────────────────────────────────────────────────

def main() -> None:
    print(f"\n{BOLD}{CYAN}{HR}")
    print("  Agent DNA — Hierarchical Identity Demo")
    print("  Delegated Identity · Cascade Revocation · Ephemeral Workers")
    print(f"{HR}{RESET}\n")

    # Check services are up
    print("Checking services…")
    for name, url in [
        ("dna-registry (port 8081)", f"{REGISTRY}/v1/agents"),
        ("dna-enforcer (port 8082)", f"{ENFORCER}/v1/enforce"),
    ]:
        try:
            r = requests.get(url, timeout=3)
            # enforce returns 405 on GET — that still means it's up
            print(f"  {GREEN}✓ {name}{RESET}")
        except requests.exceptions.ConnectionError:
            print(f"  {RED}✗ {name} — not responding{RESET}")
            print("    Start both services before running this demo.")
            sys.exit(1)

    scenario_cascade()
    print()
    scenario_ttl()

    print(f"\n{BOLD}{GREEN}{HR}")
    print("  All hierarchical scenarios passed.")
    print(f"  Portal: http://localhost:3002")
    print(f"{HR}{RESET}\n")


if __name__ == "__main__":
    main()
