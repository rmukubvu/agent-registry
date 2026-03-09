const REGISTRY = process.env.NEXT_PUBLIC_REGISTRY_URL ?? 'http://localhost:8081'
const ENFORCER = process.env.NEXT_PUBLIC_ENFORCER_URL ?? 'http://localhost:8082'

export type AgentStatus = 'PENDING' | 'ACTIVE' | 'SUSPENDED' | 'REVOKED'

export interface Agent {
  dnaId:          string
  parentDnaId?:   string        // null for manager agents
  agentName:      string
  ownerName:      string
  ownerId:        string
  jurisdiction:   string
  capabilities:   string[]
  status:         AgentStatus
  createdAt:      string
  updatedAt:      string
  revokedAt?:     string
  revokedReason?: string
  expiresAt?:     string        // TTL for ephemeral worker agents
  version:        number
}

export interface VerifyResult {
  dnaId:          string
  parentDnaId?:   string
  agentName:      string
  publicKeyHex:   string
  status:         AgentStatus
  capabilities:   string[]
  isAuthorized:   boolean
  deniedReason?:  string
}

export interface EnforceResult {
  allowed:    boolean
  dnaId:      string
  agentName?: string
  toolName:   string
  reason?:    string
}

// ── Registry calls ────────────────────────────────────────────────────────────

export async function listAgents(): Promise<Agent[]> {
  const r = await fetch(`${REGISTRY}/v1/agents`)
  if (!r.ok) throw new Error('Failed to load agents')
  return r.json()
}

export async function registerAgent(payload: {
  agentName: string; publicKeyHex: string; ownerId: string
  ownerName: string; jurisdiction: string; capabilities: string[]
  parentDnaId?: string | null; expiresAt?: string | null; idemKey: string
}): Promise<{ dnaId: string; agentName: string; status: AgentStatus }> {
  const r = await fetch(`${REGISTRY}/v1/agents`, {
    method: 'POST', headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  })
  if (!r.ok) throw new Error(await r.text())
  return r.json()
}

export async function activateAgent(dnaId: string): Promise<void> {
  const key = `activate-${dnaId}-${Date.now()}`
  await fetch(`${REGISTRY}/v1/agents/${dnaId}/activate?idemKey=${key}`, { method: 'PUT' })
}

export async function suspendAgent(dnaId: string, reason: string): Promise<void> {
  const key = `suspend-${dnaId}-${Date.now()}`
  await fetch(`${REGISTRY}/v1/agents/${dnaId}/suspend`, {
    method: 'PUT', headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ reason, idemKey: key }),
  })
}

export async function reinstateAgent(dnaId: string): Promise<void> {
  const key = `reinstate-${dnaId}-${Date.now()}`
  await fetch(`${REGISTRY}/v1/agents/${dnaId}/reinstate?idemKey=${key}`, { method: 'PUT' })
}

export async function revokeAgent(dnaId: string, reason: string): Promise<void> {
  const key = `revoke-${dnaId}-${Date.now()}`
  await fetch(`${REGISTRY}/v1/agents/${dnaId}/revoke`, {
    method: 'PUT', headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ reason, idemKey: key }),
  })
}

export async function verifyAgent(dnaId: string): Promise<VerifyResult> {
  const r = await fetch(`${REGISTRY}/v1/agents/${dnaId}/verify`)
  if (!r.ok) throw new Error(await r.text())
  return r.json()
}

// ── Enforcer calls ────────────────────────────────────────────────────────────

export async function enforceToolCall(
  dnaId: string, toolName: string
): Promise<EnforceResult> {
  const r = await fetch(`${ENFORCER}/v1/enforce`, {
    method: 'POST', headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ dnaId, toolName, toolPayload: {} }),
  })
  if (!r.ok) throw new Error(await r.text())
  return r.json()
}

// ── Helpers ───────────────────────────────────────────────────────────────────

/** True if the agent is a worker (has a parent). */
export function isWorker(agent: Agent): boolean {
  return !!agent.parentDnaId
}

/** True if the agent has at least one worker child in the list. */
export function isManager(agent: Agent, allAgents: Agent[]): boolean {
  return allAgents.some(a => a.parentDnaId === agent.dnaId)
}

/** True if the agent has a TTL set and it has passed. */
export function isExpired(agent: Agent): boolean {
  return !!agent.expiresAt && new Date(agent.expiresAt) < new Date()
}
