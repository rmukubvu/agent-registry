import type { Agent } from '@/lib/api'
import { Activity, ShieldAlert, ShieldCheck, ShieldX } from 'lucide-react'

function Stat({
  label,
  detail,
  value,
  icon,
  tone,
}: {
  label: string
  detail: string
  value: number
  icon: React.ReactNode
  tone: string
}) {
  return (
    <div className="rounded-[20px] border border-border bg-panel p-5">
      <div className="flex items-start justify-between gap-4">
        <div>
          <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">{label}</p>
          <p className="mt-3 text-3xl font-semibold text-ink">{value}</p>
          <p className="mt-2 text-sm leading-6 text-soft">{detail}</p>
        </div>
        <div className={`rounded-[18px] border px-3 py-3 ${tone}`}>
          {icon}
        </div>
      </div>
    </div>
  )
}

export default function StatsBar({ agents }: { agents: Agent[] }) {
  const count = (s: string) => agents.filter(a => a.status === s).length
  return (
    <div className="grid grid-cols-2 sm:grid-cols-4 gap-3">
      <Stat
        label="Total Agents"
        value={agents.length}
        detail="All identities registered in the current registry view."
        icon={<Activity size={18} className="text-accent" />}
        tone="border-blue-200 bg-blue-50"
      />
      <Stat
        label="Active"
        value={count('ACTIVE')}
        detail="Agents presently authorized for verification and tool access."
        icon={<ShieldCheck size={18} className="text-positive" />}
        tone="border-emerald-200 bg-emerald-50"
      />
      <Stat
        label="Suspended"
        value={count('SUSPENDED')}
        detail="Records paused for review, intervention, or cooling-off."
        icon={<ShieldAlert size={18} className="text-caution" />}
        tone="border-amber-200 bg-amber-50"
      />
      <Stat
        label="Revoked"
        value={count('REVOKED')}
        detail="Permanently blocked identities removed from active use."
        icon={<ShieldX size={18} className="text-danger" />}
        tone="border-rose-200 bg-rose-50"
      />
    </div>
  )
}
