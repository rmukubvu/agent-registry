import type { AgentStatus } from '@/lib/api'

const styles: Record<AgentStatus, string> = {
  ACTIVE:    'bg-emerald-50 text-positive border border-emerald-200',
  PENDING:   'bg-slate-100 text-neutral border border-slate-200',
  SUSPENDED: 'bg-amber-50 text-caution border border-amber-200',
  REVOKED:   'bg-rose-50 text-danger border border-rose-200',
}

const dots: Record<AgentStatus, string> = {
  ACTIVE:    'bg-positive',
  PENDING:   'bg-neutral',
  SUSPENDED: 'bg-caution',
  REVOKED:   'bg-danger',
}

export default function StatusBadge({ status }: { status: AgentStatus }) {
  return (
    <span className={`inline-flex items-center gap-1.5 rounded-full px-2.5 py-1 text-[11px] font-medium tracking-[0.02em] ${styles[status]}`}>
      <span className={`w-1.5 h-1.5 rounded-full ${dots[status]}`} />
      {status}
    </span>
  )
}
