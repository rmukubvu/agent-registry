'use client'
import { useState } from 'react'
import type { Agent } from '@/lib/api'
import { isWorker, isManager, isExpired } from '@/lib/api'
import StatusBadge from './StatusBadge'
import AgentDrawer from './AgentDrawer'
import { GitBranch, Users, Clock, Dna, ChevronRight } from 'lucide-react'

interface Props {
  agents: Agent[]
  onRefresh: () => Promise<void>
}

export default function AgentTable({ agents, onRefresh }: Props) {
  const [selected, setSelected] = useState<Agent | null>(null)

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
    <>
      <div className="overflow-x-auto rounded-[24px] border border-border bg-panel">
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-border bg-muted text-[11px] uppercase tracking-[0.18em] text-soft">
              <th className="px-5 py-3.5 text-left font-semibold">Agent</th>
              <th className="px-5 py-3.5 text-left font-semibold">Owner</th>
              <th className="px-5 py-3.5 text-left font-semibold">Capabilities</th>
              <th className="px-5 py-3.5 text-left font-semibold">Status</th>
              <th className="w-8 px-3 py-3.5" />
            </tr>
          </thead>
          <tbody className="divide-y divide-border">
            {agents.map(agent => {
              const worker  = isWorker(agent)
              const manager = isManager(agent, agents)
              const expired = isExpired(agent)
              const shown   = agent.capabilities.slice(0, 2)
              const extra   = agent.capabilities.length - shown.length

              return (
                <tr
                  key={agent.dnaId}
                  onClick={() => setSelected(agent)}
                  className="cursor-pointer transition-colors hover:bg-accentSoft/50"
                >
                  {/* Agent */}
                  <td className="px-5 py-3.5 align-middle">
                    <div className="mb-1 flex flex-wrap items-center gap-1">
                      {manager && (
                        <span className="inline-flex items-center gap-1 rounded-full border border-blue-200 bg-blue-50 px-2 py-0.5 text-[10px] font-semibold uppercase tracking-[0.12em] text-blue-700">
                          <Users size={9} /> Manager
                        </span>
                      )}
                      {worker && (
                        <span className="inline-flex items-center gap-1 rounded-full border border-indigo-200 bg-indigo-50 px-2 py-0.5 text-[10px] font-semibold uppercase tracking-[0.12em] text-indigo-700">
                          <GitBranch size={9} /> Worker
                        </span>
                      )}
                      {expired && (
                        <span className="inline-flex items-center gap-1 rounded-full border border-amber-200 bg-amber-50 px-2 py-0.5 text-[10px] font-semibold uppercase tracking-[0.12em] text-amber-700">
                          <Clock size={9} /> Expired
                        </span>
                      )}
                    </div>
                    <p className="font-semibold text-ink">{agent.agentName}</p>
                    <p className="mt-0.5 font-mono text-xs text-soft">{agent.dnaId.slice(0, 8)}…</p>
                  </td>

                  {/* Owner */}
                  <td className="px-5 py-3.5 align-middle text-sm text-soft">{agent.ownerName}</td>

                  {/* Capabilities */}
                  <td className="px-5 py-3.5 align-middle">
                    <div className="flex flex-wrap items-center gap-1">
                      {shown.map(c => (
                        <span key={c} className="rounded-full border border-blue-200 bg-blue-50 px-2.5 py-0.5 text-xs font-medium text-accent">
                          {c}
                        </span>
                      ))}
                      {extra > 0 && (
                        <span className="rounded-full border border-border bg-muted px-2.5 py-0.5 text-xs text-soft">
                          +{extra}
                        </span>
                      )}
                    </div>
                  </td>

                  {/* Status */}
                  <td className="px-5 py-3.5 align-middle">
                    <StatusBadge status={agent.status} />
                  </td>

                  {/* Arrow */}
                  <td className="px-3 py-3.5 align-middle text-soft">
                    <ChevronRight size={15} />
                  </td>
                </tr>
              )
            })}
          </tbody>
        </table>
      </div>

      {selected && (
        <AgentDrawer
          agent={selected}
          allAgents={agents}
          onClose={() => setSelected(null)}
          onRefresh={async () => { await onRefresh(); setSelected(null) }}
        />
      )}
    </>
  )
}
