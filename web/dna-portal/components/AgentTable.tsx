'use client'
import { useState } from 'react'
import type { Agent } from '@/lib/api'
import { activateAgent, suspendAgent, reinstateAgent, revokeAgent, isWorker, isManager, isExpired } from '@/lib/api'
import StatusBadge from './StatusBadge'
import { Shield, ShieldOff, RotateCcw, Zap, GitBranch, Users, Clock } from 'lucide-react'

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
      <div className="text-center py-20 text-gray-500">
        No agents registered yet. Click <span className="text-accent">+ Register Agent</span> to begin.
      </div>
    )
  }

  return (
    <div className="overflow-x-auto rounded-xl border border-border">
      <table className="w-full text-sm">
        <thead>
          <tr className="border-b border-border bg-panel text-gray-400 text-xs uppercase tracking-wider">
            <th className="px-4 py-3 text-left">Agent</th>
            <th className="px-4 py-3 text-left">Owner</th>
            <th className="px-4 py-3 text-left">Jurisdiction</th>
            <th className="px-4 py-3 text-left">Capabilities</th>
            <th className="px-4 py-3 text-left">Status</th>
            <th className="px-4 py-3 text-left">Actions</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-border">
          {agents.map(agent => {
            const worker  = isWorker(agent)
            const manager = isManager(agent, agents)
            const expired = isExpired(agent)

            return (
              <tr key={agent.dnaId} className="hover:bg-panel/50 transition-colors">
                <td className="px-4 py-3">
                  {/* Role badges */}
                  <div className="flex items-center gap-1.5 mb-0.5">
                    {manager && (
                      <span className="inline-flex items-center gap-1 bg-blue-500/10 border border-blue-500/30 text-blue-400 text-[10px] font-medium px-1.5 py-0.5 rounded">
                        <Users size={10} /> Manager
                      </span>
                    )}
                    {worker && (
                      <span className="inline-flex items-center gap-1 bg-purple-500/10 border border-purple-500/30 text-purple-400 text-[10px] font-medium px-1.5 py-0.5 rounded">
                        <GitBranch size={10} /> Worker
                      </span>
                    )}
                    {expired && (
                      <span className="inline-flex items-center gap-1 bg-orange-500/10 border border-orange-500/30 text-orange-400 text-[10px] font-medium px-1.5 py-0.5 rounded">
                        <Clock size={10} /> Expired
                      </span>
                    )}
                  </div>

                  <div className="font-medium text-white">{agent.agentName}</div>
                  <div className="text-xs text-gray-500 font-mono mt-0.5">
                    {agent.dnaId.slice(0, 8)}…
                  </div>

                  {/* Parent reference */}
                  {worker && agent.parentDnaId && (
                    <div className="text-[10px] text-purple-400/70 font-mono mt-0.5 flex items-center gap-1">
                      <GitBranch size={9} />
                      parent: {agent.parentDnaId.slice(0, 8)}…
                    </div>
                  )}

                  {/* TTL indicator */}
                  {agent.expiresAt && (
                    <div className={`text-[10px] font-mono mt-0.5 flex items-center gap-1 ${expired ? 'text-orange-400' : 'text-gray-500'}`}>
                      <Clock size={9} />
                      {expired ? 'expired' : 'expires'}: {new Date(agent.expiresAt).toLocaleString()}
                    </div>
                  )}
                </td>
                <td className="px-4 py-3 text-gray-300">{agent.ownerName}</td>
                <td className="px-4 py-3">
                  <span className="bg-muted/30 border border-border text-gray-300 text-xs px-2 py-0.5 rounded">
                    {agent.jurisdiction}
                  </span>
                </td>
                <td className="px-4 py-3">
                  <div className="flex flex-wrap gap-1">
                    {agent.capabilities.map(c => (
                      <span key={c} className="bg-accent/10 border border-accent/20 text-accent text-xs px-2 py-0.5 rounded-full">
                        {c}
                      </span>
                    ))}
                  </div>
                </td>
                <td className="px-4 py-3">
                  <StatusBadge status={agent.status} />
                  {agent.revokedReason && (
                    <div className="text-xs text-danger mt-1 max-w-[160px] truncate" title={agent.revokedReason}>
                      {agent.revokedReason}
                    </div>
                  )}
                </td>
                <td className="px-4 py-3">
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
  if (busy) return <span className="text-xs text-gray-500 animate-pulse">…</span>

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
      className={`p-1.5 rounded transition-colors ${color}`}
    >
      {children}
    </button>
  )
}
