# Agent DNA OpenAPI Specs

This folder contains OpenAPI definitions for generating SDKs and API clients.

Files:

- `agent-dna.openapi.yaml`
  - Combined spec for one SDK with two logical surfaces:
    - `Registry`
    - `Enforcer`
  - Best when you want a single package with separate namespaces or clients.

- `registry.openapi.yaml`
  - Registry-only spec
  - Covers registration, governance, lifecycle, and verification.

- `enforcer.openapi.yaml`
  - Enforcer-only spec
  - Covers runtime tool-call authorization checks.

Recommended SDK shape:

- One package: `agent-dna`
- Two clients or namespaces:
  - `registry`
  - `enforcer`

Example shape:

```ts
client.registry.listAgents()
client.registry.registerAgent(...)
client.registry.activateAgent(...)

client.enforcer.enforceToolCall(...)
```

Notes:

- The combined spec uses path-level `servers` because the registry and enforcer
  are currently deployed on different base URLs.
- If your SDK generator expects a single base URL, keep the registry and enforcer
  as separate generated clients, or place both services behind one gateway later.
