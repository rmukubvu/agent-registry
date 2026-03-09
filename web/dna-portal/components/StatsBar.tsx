import type { Agent } from '@/lib/api'

function Stat({ label, value, color }: { label: string; value: number; color: string }) {
  return (
    <div className="bg-panel border border-border rounded-xl px-5 py-4 flex items-center gap-4">
      <div className={`text-3xl font-bold ${color}`}>{value}</div>
      <div className="text-sm text-gray-400">{label}</div>
    </div>
  )
}

export default function StatsBar({ agents }: { agents: Agent[] }) {
  const count = (s: string) => agents.filter(a => a.status === s).length
  return (
    <div className="grid grid-cols-2 sm:grid-cols-4 gap-3">
      <Stat label="Total Agents"     value={agents.length}         color="text-white" />
      <Stat label="Active"           value={count('ACTIVE')}       color="text-positive" />
      <Stat label="Suspended"        value={count('SUSPENDED')}    color="text-caution" />
      <Stat label="Revoked"          value={count('REVOKED')}      color="text-danger" />
    </div>
  )
}
