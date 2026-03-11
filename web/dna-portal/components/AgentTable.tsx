'use client'
import { useState } from 'react'
import type { Agent, AgentStatus } from '@/lib/api'
import { isWorker, isManager, isExpired } from '@/lib/api'
import StatusBadge from './StatusBadge'
import AgentDrawer from './AgentDrawer'
import { GitBranch, Users, Clock, Dna, ChevronRight, Search, X } from 'lucide-react'

interface Props {
  agents: Agent[]
  onRefresh: () => Promise<void>
  onVerify: (id: string) => void
}

const STATUS_FILTERS = ['ALL', 'ACTIVE', 'PENDING', 'SUSPENDED', 'REVOKED'] as const
type FilterValue = typeof STATUS_FILTERS[number]

export default function AgentTable({ agents, onRefresh, onVerify }: Props) {
  const [selected, setSelected] = useState<Agent | null>(null)
  const [query, setQuery] = useState('')
  const [statusFilter, setStatusFilter] = useState<FilterValue>('ALL')

  const filtered = agents.filter(a => {
    const matchesSearch = !query ||
      a.agentName.toLowerCase().includes(query.toLowerCase()) ||
      a.ownerName.toLowerCase().includes(query.toLowerCase()) ||
      a.dnaId.toLowerCase().includes(query.toLowerCase())
    const matchesStatus = statusFilter === 'ALL' || a.status === statusFilter
    return matchesSearch && matchesStatus
  })

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
      {/* Search + filter bar */}
      <div className="mb-3 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
        <div className="relative max-w-xs flex-1">
          <Search size={13} className="absolute left-3.5 top-1/2 -translate-y-1/2 text-soft" />
          <input
            value={query}
            onChange={e => setQuery(e.target.value)}
            placeholder="Search by name, owner or ID…"
            className="w-full rounded-2xl border border-border bg-panel py-2.5 pl-9 pr-9 text-sm text-ink placeholder:text-soft focus:border-accent focus:outline-none"
          />
          {query && (
            <button
              onClick={() => setQuery('')}
              className="absolute right-3 top-1/2 -translate-y-1/2 text-soft hover:text-ink"
            >
              <X size={13} />
            </button>
          )}
        </div>

        <div className="flex flex-wrap items-center gap-1.5">
          {STATUS_FILTERS.map(s => (
            <button
              key={s}
              onClick={() => setStatusFilter(s)}
              className={`rounded-xl border px-3 py-1.5 text-xs font-semibold uppercase tracking-[0.1em] transition-colors ${
                statusFilter === s
                  ? 'border-accent bg-accent text-white'
                  : 'border-border bg-panel text-soft hover:text-ink'
              }`}
            >
              {s === 'ALL' ? 'All' : s.charAt(0) + s.slice(1).toLowerCase()}
            </button>
          ))}
        </div>
      </div>

      {/* Table */}
      <div className="overflow-x-auto rounded-[24px] border border-border bg-panel">
        {filtered.length === 0 ? (
          <div className="px-8 py-14 text-center">
            <p className="text-sm font-semibold text-ink">No results found.</p>
            <p className="mt-1 text-sm text-soft">Try a different search term or filter.</p>
          </div>
        ) : (
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
              {filtered.map(agent => {
                const worker  = isWorker(agent)
                const manager = isManager(agent, agents)
                const expired = isExpired(agent)
                const shown   = agent.capabilities.slice(0, 2)
                const extra   = agent.capabilities.length - shown.length
                const isSelected = selected?.dnaId === agent.dnaId

                return (
                  <tr
                    key={agent.dnaId}
                    onClick={() => setSelected(agent)}
                    className={`cursor-pointer transition-colors ${
                      isSelected
                        ? 'bg-accentSoft'
                        : 'hover:bg-accentSoft/50'
                    }`}
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
                      <StatusBadge status={agent.status as AgentStatus} />
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
        )}
      </div>

      {selected && (
        <AgentDrawer
          agent={selected}
          allAgents={agents}
          onClose={() => setSelected(null)}
          onRefresh={async () => { await onRefresh(); setSelected(null) }}
          onVerify={onVerify}
        />
      )}
    </>
  )
}
