#!/usr/bin/env python3
"""
Seed stable demo data into the registry for presentation and portal testing.

Creates:
  - one ACTIVE standalone agent
  - one PENDING standalone agent
  - one SUSPENDED standalone agent
  - one REVOKED standalone agent
  - one ACTIVE manager + ACTIVE worker pair
  - one REVOKED manager + REVOKED worker pair (cascade)
"""
from __future__ import annotations

import os
import sys
import time
import uuid
from dataclasses import dataclass
from datetime import datetime, timezone

import requests


REGISTRY = os.environ.get("REGISTRY_URL", "http://localhost:8081").rstrip("/")
ENFORCER = os.environ.get("ENFORCER_URL", "http://localhost:8082").rstrip("/")
SEED_LABEL = os.environ.get(
    "SEED_LABEL",
    f"Presentation {datetime.now(timezone.utc).strftime('%Y-%m-%d %H:%M UTC')}",
)

GREEN = "\033[92m"
RED = "\033[91m"
CYAN = "\033[96m"
BOLD = "\033[1m"
RESET = "\033[0m"
DIM = "\033[2m"


@dataclass
class SeededAgent:
    label: str
    dna_id: str
    expected_status: str
    expected_authorized: bool
    parent_dna_id: str | None = None


def banner(text: str) -> None:
    print(f"\n{BOLD}{CYAN}-- {text}{RESET}")


def ok(text: str) -> None:
    print(f"  {GREEN}OK{RESET} {text}")


def fail(text: str) -> None:
    print(f"  {RED}FAIL{RESET} {text}")
    sys.exit(1)


def info(text: str) -> None:
    print(f"  {DIM}{text}{RESET}")


def idem(prefix: str) -> str:
    return f"{prefix}-{uuid.uuid4()}"


def ensure_up() -> None:
    for name, url in [
        ("registry", f"{REGISTRY}/q/health/ready"),
        ("enforcer", f"{ENFORCER}/q/health/live"),
    ]:
        response = requests.get(url, timeout=10)
        if response.status_code != 200:
            fail(f"{name} health check failed: {response.status_code} {response.text}")
        ok(f"{name} reachable at {url}")


def register_agent(
    agent_name: str,
    owner_id: str,
    *,
    capabilities: list[str],
    parent_dna_id: str | None = None,
) -> str:
    slug = agent_name.lower().replace(" ", "-")
    payload = {
        "agentName": agent_name,
        "publicKeyHex": uuid.uuid4().hex + uuid.uuid4().hex,
        "workloadIdentity": f"svc://presentation/{slug}",
        "provenanceRef": f"build://presentation/{slug}",
        "ownerId": owner_id,
        "ownerName": SEED_LABEL,
        "jurisdiction": "CA",
        "capabilities": capabilities,
        "idemKey": idem("seed-reg"),
    }
    if parent_dna_id is not None:
        payload["parentDnaId"] = parent_dna_id

    response = requests.post(f"{REGISTRY}/v1/agents", json=payload, timeout=15)
    if response.status_code != 201:
        fail(f"register {agent_name}: {response.status_code} {response.text}")
    dna_id = response.json()["dnaId"]
    ok(f"registered {agent_name} -> {dna_id}")
    return dna_id


def activate_agent(dna_id: str, label: str) -> None:
    response = requests.put(
        f"{REGISTRY}/v1/agents/{dna_id}/activate",
        json={
            "idemKey": idem("seed-activate"),
            "approvedBy": "Presentation Governance Team",
            "approvalReason": f"Approved for presentation scenario: {label}",
        },
        timeout=15,
    )
    if response.status_code != 200:
        fail(f"activate {label}: {response.status_code} {response.text}")
    ok(f"activated {label}")


def suspend_agent(dna_id: str, label: str, reason: str) -> None:
    response = requests.put(
        f"{REGISTRY}/v1/agents/{dna_id}/suspend",
        json={"reason": reason, "idemKey": idem("seed-suspend")},
        timeout=15,
    )
    if response.status_code != 200:
        fail(f"suspend {label}: {response.status_code} {response.text}")
    ok(f"suspended {label}")


def revoke_agent(dna_id: str, label: str, reason: str) -> None:
    response = requests.put(
        f"{REGISTRY}/v1/agents/{dna_id}/revoke",
        json={"reason": reason, "idemKey": idem("seed-revoke")},
        timeout=15,
    )
    if response.status_code != 200:
        fail(f"revoke {label}: {response.status_code} {response.text}")
    ok(f"revoked {label}")


def verify_agent(
    dna_id: str,
    label: str,
    *,
    expected_status: str,
    expected_authorized: bool,
) -> dict:
    last_body: dict | None = None
    for _ in range(10):
        response = requests.get(f"{REGISTRY}/v1/agents/{dna_id}/verify", timeout=15)
        if response.status_code != 200:
            fail(f"verify {label}: {response.status_code} {response.text}")
        body = response.json()
        last_body = body
        if (
            body.get("status") == expected_status
            and body.get("isAuthorized") == expected_authorized
        ):
            ok(
                f"verified {label} -> status={body['status']} authorized={body['isAuthorized']}"
            )
            return body
        time.sleep(0.4)

    fail(
        f"verify {label}: expected status={expected_status} authorized={expected_authorized}, "
        f"got {last_body}"
    )
    raise AssertionError("unreachable")


def enforce_agent(dna_id: str, label: str, *, expected_allowed: bool) -> dict:
    response = requests.post(
        f"{ENFORCER}/v1/enforce",
        json={"dnaId": dna_id, "toolName": "mcp:web-search", "toolPayload": {}},
        timeout=15,
    )
    if response.status_code != 200:
        fail(f"enforce {label}: {response.status_code} {response.text}")
    body = response.json()
    if body.get("allowed") != expected_allowed:
        fail(f"enforce {label}: expected allowed={expected_allowed}, got {body}")
    ok(f"enforcer returned allowed={body['allowed']} for {label}")
    return body


def main() -> None:
    print(f"\n{BOLD}{CYAN}Agent DNA presentation seed{RESET}\n")
    info(f"registry={REGISTRY}")
    info(f"enforcer={ENFORCER}")
    info(f"label={SEED_LABEL}")

    ensure_up()

    owner_id = str(uuid.uuid4())
    seeded: list[SeededAgent] = []

    banner("Standalone states")
    active_id = register_agent(
        f"{SEED_LABEL} Active Agent",
        owner_id,
        capabilities=["mcp:web-search", "mcp:filesystem"],
    )
    activate_agent(active_id, "Active Agent")
    verify_agent(active_id, "Active Agent", expected_status="ACTIVE", expected_authorized=True)
    enforce_agent(active_id, "Active Agent", expected_allowed=True)
    seeded.append(SeededAgent("Active Agent", active_id, "ACTIVE", True))

    pending_id = register_agent(
        f"{SEED_LABEL} Pending Agent",
        owner_id,
        capabilities=["mcp:web-search"],
    )
    verify_agent(
        pending_id,
        "Pending Agent",
        expected_status="PENDING",
        expected_authorized=False,
    )
    enforce_agent(pending_id, "Pending Agent", expected_allowed=False)
    seeded.append(SeededAgent("Pending Agent", pending_id, "PENDING", False))

    suspended_id = register_agent(
        f"{SEED_LABEL} Suspended Agent",
        owner_id,
        capabilities=["mcp:filesystem"],
    )
    activate_agent(suspended_id, "Suspended Agent")
    suspend_agent(suspended_id, "Suspended Agent", "Presentation seed suspended state")
    verify_agent(
        suspended_id,
        "Suspended Agent",
        expected_status="SUSPENDED",
        expected_authorized=False,
    )
    enforce_agent(suspended_id, "Suspended Agent", expected_allowed=False)
    seeded.append(SeededAgent("Suspended Agent", suspended_id, "SUSPENDED", False))

    revoked_id = register_agent(
        f"{SEED_LABEL} Revoked Agent",
        owner_id,
        capabilities=["mcp:web-search"],
    )
    activate_agent(revoked_id, "Revoked Agent")
    revoke_agent(revoked_id, "Revoked Agent", "Presentation seed revoked state")
    verify_agent(
        revoked_id,
        "Revoked Agent",
        expected_status="REVOKED",
        expected_authorized=False,
    )
    enforce_agent(revoked_id, "Revoked Agent", expected_allowed=False)
    seeded.append(SeededAgent("Revoked Agent", revoked_id, "REVOKED", False))

    banner("Hierarchy states")
    manager_active_id = register_agent(
        f"{SEED_LABEL} Manager Active",
        owner_id,
        capabilities=["mcp:web-search", "mcp:filesystem"],
    )
    activate_agent(manager_active_id, "Manager Active")
    worker_active_id = register_agent(
        f"{SEED_LABEL} Worker Active",
        owner_id,
        capabilities=["mcp:web-search"],
        parent_dna_id=manager_active_id,
    )
    activate_agent(worker_active_id, "Worker Active")
    verify_agent(
        manager_active_id,
        "Manager Active",
        expected_status="ACTIVE",
        expected_authorized=True,
    )
    verify_agent(
        worker_active_id,
        "Worker Active",
        expected_status="ACTIVE",
        expected_authorized=True,
    )
    enforce_agent(worker_active_id, "Worker Active", expected_allowed=True)
    seeded.append(SeededAgent("Manager Active", manager_active_id, "ACTIVE", True))
    seeded.append(
        SeededAgent(
            "Worker Active",
            worker_active_id,
            "ACTIVE",
            True,
            parent_dna_id=manager_active_id,
        )
    )

    manager_revoked_id = register_agent(
        f"{SEED_LABEL} Manager Revoked",
        owner_id,
        capabilities=["mcp:web-search", "mcp:filesystem"],
    )
    activate_agent(manager_revoked_id, "Manager Revoked")
    worker_cascade_id = register_agent(
        f"{SEED_LABEL} Worker Cascade",
        owner_id,
        capabilities=["mcp:filesystem"],
        parent_dna_id=manager_revoked_id,
    )
    activate_agent(worker_cascade_id, "Worker Cascade")
    revoke_agent(
        manager_revoked_id,
        "Manager Revoked",
        "Presentation seed cascade revocation",
    )
    verify_agent(
        manager_revoked_id,
        "Manager Revoked",
        expected_status="REVOKED",
        expected_authorized=False,
    )
    verify_agent(
        worker_cascade_id,
        "Worker Cascade",
        expected_status="REVOKED",
        expected_authorized=False,
    )
    enforce_agent(worker_cascade_id, "Worker Cascade", expected_allowed=False)
    seeded.append(SeededAgent("Manager Revoked", manager_revoked_id, "REVOKED", False))
    seeded.append(
        SeededAgent(
            "Worker Cascade",
            worker_cascade_id,
            "REVOKED",
            False,
            parent_dna_id=manager_revoked_id,
        )
    )

    banner("Seed summary")
    for agent in seeded:
        relation = f" parent={agent.parent_dna_id}" if agent.parent_dna_id else ""
        print(
            f"  {agent.label}: {agent.dna_id} "
            f"(status={agent.expected_status}, authorized={agent.expected_authorized}{relation})"
        )

    print(f"\n{GREEN}{BOLD}Presentation seed complete.{RESET}\n")


if __name__ == "__main__":
    main()
