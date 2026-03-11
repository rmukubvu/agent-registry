'use client'
import { useState } from 'react'
import type { Agent } from '@/lib/api'
import { activateAgent, suspendAgent, reinstateAgent, revokeAgent, isWorker, isManager, isExpired } from '@/lib/api'
import StatusBadge from './StatusBadge'
import { Shield, ShieldOff, RotateCcw, Zap, GitBranch, Users, Clock, Dna } from 'lucide-react'

interface Props {
  agents: Agent[]
  onRefresh: () => void
}

export default function AgentTable({ agents, onRefresh }: Props) {
  const [loading, setLoading] = useState<string | null>(null)

  async function act(dnaId: string, action: () => Promise<void>) {
    setLoading(dnaId)
    try { await action(); await onRefresh() }
    finally { setLoading(null) }
  }

  if (agents.length === 0) {
    return (
      <div className="rounded-[24px] border border-dashed border-border bg-panel px-8 py-16 text-center">
        <div className="mx-auto mb-4 flex h-12 w-12 items-center justify-center rounded-2xl border border-blue-200 bg-accentSoft">
          <Dna size={22} className="text-accent" />
        </div>
        <p className="text-lg font-semibold text-ink">No agents registered yet.</p>
        <p className="mt-2 text-sm leading-6 text-soft">
          Create a manager or worker record to start demonstrating the registry lifecycle.
        </p>
      </div>
    )
  }

  return (
    <div className="overflow-x-auto rounded-[24px] border border-border bg-panel">
      <table className="w-full text-sm">
        <thead>
          <tr className="border-b border-border bg-muted text-[11px] uppercase tracking-[0.18em] text-soft">
            <th className="px-5 py-3.5 text-left font-semibold">Agent</th>
            <th className="px-5 py-3.5 text-left font-semibold">Owner</th>
            <th className="px-5 py-3.5 text-left font-semibold">Jurisdiction</th>
            <th className="px-5 py-3.5 text-left font-semibold">Capabilities</th>
            <th className="px-5 py-3.5 text-left font-semibold">Status</th>
            <th className="px-5 py-3.5 text-left font-semibold">Actions</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-border">
          {agents.map(agent => {
            const worker  = isWorker(agent)
            const manager = isManager(agent, agents)
            const expired = isExpired(agent)

            return (
              <tr key={agent.dnaId} className="transition-colors hover:bg-slate-50/70">
                <td className="px-5 py-3.5 align-top">
                  {/* Role badges */}
                  <div className="mb-1.5 flex flex-wrap items-center gap-1.5">
                    {manager && (
                      <span className="inline-flex items-center gap-1 rounded-full border border-blue-200 bg-blue-50 px-2 py-1 text-[10px] font-semibold uppercase tracking-[0.14em] text-blue-700">
                        <Users size={10} /> Manager
                      </span>
                    )}
                    {worker && (
                      <span className="inline-flex items-center gap-1 rounded-full border border-indigo-200 bg-indigo-50 px-2 py-1 text-[10px] font-semibold uppercase tracking-[0.14em] text-indigo-700">
                        <GitBranch size={10} /> Worker
                      </span>
                    )}
                    {expired && (
                      <span className="inline-flex items-center gap-1 rounded-full border border-amber-200 bg-amber-50 px-2 py-1 text-[10px] font-semibold uppercase tracking-[0.14em] text-amber-700">
                        <Clock size={10} /> Expired
                      </span>
                    )}
                  </div>

                  <div className="font-semibold text-ink">{agent.agentName}</div>
                  <div className="mt-1 text-xs font-mono text-soft">
                    {agent.dnaId.slice(0, 8)}…
                  </div>

                  {/* Parent reference */}
                  {worker && agent.parentDnaId && (
                    <div className="mt-1 flex items-center gap-1 text-[11px] font-mono text-indigo-700/80">
                      <GitBranch size={9} />
                      parent: {agent.parentDnaId.slice(0, 8)}…
                    </div>
                  )}

                  {/* TTL indicator */}
                  {agent.expiresAt && (
                    <div className={`mt-1 flex items-center gap-1 text-[11px] font-mono ${expired ? 'text-amber-700' : 'text-soft'}`}>
                      <Clock size={9} />
                      {expired ? 'expired' : 'expires'}: {new Date(agent.expiresAt).toLocaleString()}
                    </div>
                  )}
                </td>
                <td className="px-5 py-3.5 align-top text-sm text-soft">{agent.ownerName}</td>
                <td className="px-5 py-3.5 align-top">
                  <span className="rounded-full border border-slate-200 bg-slate-50 px-2.5 py-1 text-xs font-semibold uppercase tracking-[0.12em] text-neutral">
                    {agent.jurisdiction}
                  </span>
                </td>
                <td className="px-5 py-3.5 align-top">
                  <div className="flex flex-wrap gap-1">
                    {agent.capabilities.map(c => (
                      <span key={c} className="rounded-full border border-blue-200 bg-blue-50 px-2.5 py-1 text-xs font-medium text-accent">
                        {c}
                      </span>
                    ))}
                  </div>
                </td>
                <td className="px-5 py-3.5 align-top">
                  <StatusBadge status={agent.status} />
                  {agent.revokedReason && (
                    <div className="mt-2 max-w-[200px] text-xs text-danger" title={agent.revokedReason}>
                      {agent.revokedReason}
                    </div>
                  )}
                </td>
                <td className="px-5 py-3.5 align-top">
                  <div className="flex items-center gap-1.5">
                    <ActionButtons
                      agent={agent}
                      busy={loading === agent.dnaId}
                      onActivate={() => act(agent.dnaId, () => activateAgent(agent.dnaId))}
                      onSuspend={() => {
                        const r = prompt('Suspend reason:')
                        if (r) act(agent.dnaId, () => suspendAgent(agent.dnaId, r))
                      }}
                      onReinstate={() => act(agent.dnaId, () => reinstateAgent(agent.dnaId))}
                      onRevoke={() => {
                        const r = prompt('Revoke reason (required):')
                        if (r) act(agent.dnaId, () => revokeAgent(agent.dnaId, r))
                      }}
                    />
                  </div>
                </td>
              </tr>
            )
          })}
        </tbody>
      </table>
    </div>
  )
}

function ActionButtons({ agent, busy, onActivate, onSuspend, onReinstate, onRevoke }: {
  agent: Agent; busy: boolean
  onActivate: () => void; onSuspend: () => void
  onReinstate: () => void; onRevoke: () => void
}) {
  if (busy) return <span className="text-xs text-soft animate-pulse">Working…</span>

  return (
    <>
      {agent.status === 'PENDING' && (
        <Btn onClick={onActivate} title="Activate" color="text-positive hover:bg-positive/10">
          <Zap size={14} />
        </Btn>
      )}
      {agent.status === 'ACTIVE' && (<>
        <Btn onClick={onSuspend} title="Suspend" color="text-caution hover:bg-caution/10">
          <ShieldOff size={14} />
        </Btn>
        <Btn onClick={onRevoke} title="Revoke" color="text-danger hover:bg-danger/10">
          <Shield size={14} />
        </Btn>
      </>)}
      {agent.status === 'SUSPENDED' && (<>
        <Btn onClick={onReinstate} title="Reinstate" color="text-positive hover:bg-positive/10">
          <RotateCcw size={14} />
        </Btn>
        <Btn onClick={onRevoke} title="Revoke" color="text-danger hover:bg-danger/10">
          <Shield size={14} />
        </Btn>
      </>)}
    </>
  )
}

function Btn({ onClick, title, color, children }: {
  onClick: () => void; title: string; color: string; children: React.ReactNode
}) {
  return (
    <button
      onClick={onClick} title={title}
      className={`rounded-xl border border-border bg-white p-1.5 transition-colors ${color}`}
    >
      {children}
    </button>
  )
}
