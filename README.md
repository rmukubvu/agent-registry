# Agent DNA

> **A global identity and revocation registry for AI agents — analogous to DNS for the internet.**

Every agent that wants to use MCP tools must hold a registered DNA record. Misbehaving agents can be blocked worldwide in seconds by revoking their record.

---

## The Problem

AI agents are being deployed at scale with no shared identity layer. Today, any process can claim to be any agent, call any tool, and leave no auditable trail. There is no equivalent of:

- A **certificate authority** that says "this agent is who it claims to be"
- A **revocation list** that propagates a ban globally the moment an agent misbehaves
- A **chokepoint** that MCP-compatible tool servers can check before executing a dangerous operation

The result is an environment where rogue agents — whether misconfigured, compromised, or deliberately malicious — can call filesystem tools, web APIs, and databases with no gatekeeping. The industry is building powerful tools and handing them to agents with no identity infrastructure underneath.

---

## What Agent DNA Is

Agent DNA is a **neutral, open registry** that any AI framework can integrate with. It is intentionally not owned by any single model provider. Its design mirrors how the internet solved a similar problem with DNS and PKI:

| Internet | Agent DNA |
|---|---|
| DNS — maps names to addresses | DNA registry — maps agent identities to verified records |
| Certificate Authority | DNA registry as root of trust |
| Certificate revocation (CRL/OCSP) | DNA revocation — instant global ban |
| Firewall / WAF | DNA enforcer — blocks unauthorised tool calls |

An agent registers once. Its record carries a cryptographic public key (Ed25519), its owner, jurisdiction, and the specific MCP capabilities it is permitted to use. When the agent attempts a tool call, the enforcer checks the registry in real time. If the record is revoked or suspended, the call is blocked — everywhere, instantly.

---

## How It Works

```
  ┌─────────────────────────────────────────────────────────┐
  │                      AI Agent                           │
  │  (Claude, GPT-4o, Gemini, custom — any framework)       │
  └────────────────────────┬────────────────────────────────┘
                           │  POST /v1/enforce
                           │  { dnaId, toolName, payload }
                           ▼
  ┌─────────────────────────────────────────────────────────┐
  │               dna-enforcer  :8082                       │
  │                                                         │
  │  1. Extract dnaId from request                          │
  │  2. Call dna-registry /v1/agents/{dnaId}/verify         │
  │  3. If ACTIVE  → allowed: true                          │
  │     If anything else → allowed: false + reason          │
  └────────────────────────┬────────────────────────────────┘
                           │  GET /v1/agents/{dnaId}/verify
                           ▼
  ┌─────────────────────────────────────────────────────────┐
  │              dna-registry  :8081                        │
  │                                                         │
  │  PostgreSQL-backed registry of all agent records        │
  │  State machine: PENDING → ACTIVE → SUSPENDED → REVOKED  │
  │  Idempotent writes · Optimistic locking · Audit log     │
  └─────────────────────────────────────────────────────────┘
                           ▲
                           │  REST API (register / activate /
                           │  suspend / reinstate / revoke)
  ┌─────────────────────────────────────────────────────────┐
  │              dna-portal  :3002                          │
  │                                                         │
  │  Next.js dashboard — register agents, manage lifecycle, │
  │  live verification panel, enforcer test                 │
  └─────────────────────────────────────────────────────────┘
```

### Agent Lifecycle (State Machine)

```
  PENDING ──activate──► ACTIVE ──suspend──► SUSPENDED
                          │                    │
                        revoke               revoke / reinstate
                          │                    │
                          ▼                    ▼
                       REVOKED ◄──────────────┘   (terminal)
                                  ACTIVE
```

| Transition | Who triggers it | Effect |
|---|---|---|
| `PENDING → ACTIVE` | Registry operator / owner | Agent may now use tools |
| `ACTIVE → SUSPENDED` | Registry operator | Agent blocked; appeal possible |
| `SUSPENDED → ACTIVE` | Registry operator | Reinstated after review |
| `ACTIVE / SUSPENDED → REVOKED` | Registry operator | Permanent global ban |

---

## Project Structure

```
agent-dna/
├── services/
│   ├── dna-registry/          # Core registry — REST API + PostgreSQL
│   │   ├── domain/            # AgentRecord, AgentStatus, state machine
│   │   ├── application/       # Command handlers, idempotency
│   │   ├── infrastructure/    # jOOQ repository, outbox publisher
│   │   └── api/rest/          # JAX-RS resource (7 endpoints)
│   │
│   └── dna-enforcer/          # MCP middleware — checks DNA before tool calls
│       ├── application/       # EnforcerService
│       ├── infrastructure/    # HttpDnaRegistryClient
│       └── api/rest/          # EnforcerResource (POST /v1/enforce)
│
├── web/
│   └── dna-portal/            # Next.js 15 management dashboard
│       ├── components/        # AgentTable, RegisterModal, VerifyPanel, StatsBar
│       └── lib/api.ts         # Registry + enforcer client
│
└── demo/
    ├── registered_agent.py    # Happy path: register → activate → use tools → revoke
    ├── rogue_agent.py         # 3 blocked scenarios: fake ID, unactivated, suspended
    └── run_demo.sh            # Full demo orchestrator with coloured output
```

---

## REST API — dna-registry (:8081)

| Method | Path | Description | Response |
|---|---|---|---|
| `POST` | `/v1/agents` | Register a new agent | `201` with `dnaId` |
| `GET` | `/v1/agents` | List all agents | `200` array |
| `PUT` | `/v1/agents/{id}/activate` | Transition PENDING → ACTIVE | `200` |
| `PUT` | `/v1/agents/{id}/suspend` | Transition ACTIVE → SUSPENDED | `200` |
| `PUT` | `/v1/agents/{id}/reinstate` | Transition SUSPENDED → ACTIVE | `200` |
| `PUT` | `/v1/agents/{id}/revoke` | Permanently revoke (any → REVOKED) | `200` |
| `GET` | `/v1/agents/{id}/verify` | Check authorization status | `200` always |

**Verify response:**
```json
{
  "dnaId":        "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "agentName":    "ClaudeIntakeAgent",
  "status":       "ACTIVE",
  "capabilities": ["mcp:filesystem", "mcp:web-search"],
  "isAuthorized": true
}
```

## REST API — dna-enforcer (:8082)

| Method | Path | Description |
|---|---|---|
| `POST` | `/v1/enforce` | Check DNA and allow or block a tool call |

**Request:**
```json
{
  "dnaId":       "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "toolName":    "mcp:filesystem:read",
  "toolPayload": { "path": "/var/data/report.csv" }
}
```

**Response (allowed):**
```json
{ "allowed": true,  "dnaId": "...", "agentName": "ClaudeIntakeAgent", "toolName": "mcp:filesystem:read" }
```

**Response (blocked):**
```json
{ "allowed": false, "dnaId": "...", "agentName": null, "toolName": "mcp:filesystem:read", "reason": "Agent DNA not found in registry" }
```

---

## Agent DNA Record

```json
{
  "dnaId":         "uuid — globally unique, immutable",
  "agentName":     "ClaudeIntakeAgent",
  "publicKeyHex":  "ed25519 public key — agent proves identity",
  "ownerId":       "uuid of the owning organisation",
  "ownerName":     "TurfOS",
  "jurisdiction":  "ZA",
  "capabilities":  ["mcp:filesystem", "mcp:web-search"],
  "status":        "ACTIVE",
  "createdAt":     "2026-03-08T09:00:00Z",
  "updatedAt":     "2026-03-08T09:01:00Z",
  "revokedAt":     null,
  "revokedReason": null,
  "version":       1
}
```

---

## Running the POC

### Prerequisites
- Java 23 (`JAVA_HOME=/Users/robson/Library/Java/JavaVirtualMachines/openjdk-23.0.1/Contents/Home`)
- Docker Desktop (Quarkus Dev Services auto-starts PostgreSQL)
- Node.js 20+ and npm
- Python 3.9+ with `requests` (`pip install requests`)

### Start the services

```bash
# Terminal 1 — DNA Registry (port 8081)
cd /Users/robson/code/agent-dna
JAVA_HOME=.../openjdk-23.0.1/Contents/Home \
  ./gradlew :services:dna-registry:quarkusDev

# Terminal 2 — DNA Enforcer (port 8082)
JAVA_HOME=.../openjdk-23.0.1/Contents/Home \
  ./gradlew :services:dna-enforcer:quarkusDev

# Terminal 3 — Portal (port 3002)
cd web/dna-portal && npm run dev
```

### Run the demo

```bash
bash demo/run_demo.sh
```

Expected output:

```
── Step 1: Register agent with DNA registry
  ✓ Registered — dnaId=3fa85f64-…
── Step 2: Activate the agent
  ✓ Activated — PENDING → ACTIVE
── Step 3: Verify authorization status
  ✓ AUTHORIZED — status=ACTIVE
── Step 4: Call tool via DNA enforcer
  ✓ TOOL CALL ALLOWED — tool=mcp:filesystem:read
── Step 5: Revoke the agent
  ✓ Revoked — ACTIVE → REVOKED
── Step 6: Retry tool call — should now be BLOCKED
  ✓ TOOL CALL BLOCKED as expected

── Scenario A: Rogue agent with a fake DNA ID
  ⛔ BLOCKED — Agent DNA not found in registry
── Scenario B: Agent registered but never activated
  ⛔ BLOCKED — Agent not authorized — status: PENDING
── Scenario C: Active then suspended
  ⛔ BLOCKED — Agent not authorized — status: SUSPENDED
```

### Run the tests

```bash
cd /Users/robson/code/agent-dna
JAVA_HOME=.../openjdk-23.0.1/Contents/Home ./gradlew test
# 57 tests, 0 failures
```

---

## What This Is Trying to Address

### The immediate gap
MCP (Model Context Protocol) gives agents structured access to tools — filesystems, browsers, APIs, databases. There is no standard that says *which agents* are allowed to call those tools. Any process with network access can call an MCP server today.

### The broader risk
As agents become autonomous and long-running, the attack surface expands:
- **Prompt injection** causes a legitimate agent to act maliciously — it should be suspendable without stopping the whole system
- **Compromised agents** can be revoked at the identity layer before they cause further damage
- **Rogue third-party agents** claim capabilities they haven't been granted

### What Agent DNA provides
1. **Identity** — every agent has a unique, unforgeable record anchored to a public key
2. **Authorisation scope** — the `capabilities` field limits which MCP tools an agent is allowed to call
3. **Real-time revocation** — revoking a record blocks that agent at every enforcer in the network, globally, without redeploying anything
4. **Jurisdiction and ownership** — governments and enterprises can see which agents are operating in their space and who owns them
5. **Audit trail** — every state transition is timestamped and immutable

### The analogy that matters
When a TLS certificate is compromised, you revoke it. Certificate Transparency logs let anyone verify revocation. Browsers check CRL/OCSP before trusting a connection.

Agent DNA is the equivalent infrastructure layer for AI agents. The registry is the CA. The enforcer is the OCSP check. The revocation is global and instant.

### Who needs to agree on this
For this to work at internet scale, the major AI platforms need to agree on:
1. A standard DNA record schema (this POC proposes one)
2. A shared or federated registry (or a protocol for registry interoperability)
3. An enforcer interface that MCP tool servers implement natively

This POC is the working proof of concept to open that conversation with Anthropic, OpenAI, Microsoft, and Google.

---

## Technical Stack

| Layer | Technology |
|---|---|
| Services | Java 21, Quarkus 3.17.6, JAX-RS |
| Persistence | PostgreSQL via Quarkus Dev Services, jOOQ 3.19.6, Flyway |
| Patterns | CQRS command handlers, idempotency keys, optimistic locking, outbox |
| Portal | Next.js 15, React 19, Tailwind CSS, TypeScript |
| Tests | JUnit 5, Mockito, AssertJ — 57 tests, 0 failures |
| Demo | Python 3 + requests |

---

## Roadmap (beyond this POC)

- **Ed25519 signature verification** — agents sign requests; enforcer verifies the signature matches the registered public key
- **Capability enforcement** — enforcer checks requested tool against the agent's declared `capabilities` list
- **Federated registries** — multiple org-level registries with cross-registry trust (like DNS zones)
- **Event streaming** — revocation events published to Kafka so enforcers update in near-real-time without polling
- **Agent SDK** — one-line integration for LangChain4j, LangGraph, AutoGen, CrewAI
- **Governance dashboard** — jurisdiction-level views for regulatory bodies

---

*Built as a working proof of concept — not slideware.*
