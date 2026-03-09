import type { AgentStatus } from '@/lib/api'

const styles: Record<AgentStatus, string> = {
  ACTIVE:    'bg-positive/10 text-positive  border border-positive/20',
  PENDING:   'bg-neutral/10  text-neutral   border border-neutral/20',
  SUSPENDED: 'bg-caution/10  text-caution   border border-caution/20',
  REVOKED:   'bg-danger/10   text-danger    border border-danger/20',
}

const dots: Record<AgentStatus, string> = {
  ACTIVE:    'bg-positive',
  PENDING:   'bg-neutral',
  SUSPENDED: 'bg-caution',
  REVOKED:   'bg-danger',
}

export default function StatusBadge({ status }: { status: AgentStatus }) {
  return (
    <span className={`inline-flex items-center gap-1.5 px-2.5 py-0.5 rounded-full text-xs font-medium ${styles[status]}`}>
      <span className={`w-1.5 h-1.5 rounded-full ${dots[status]}`} />
      {status}
    </span>
  )
}
