'use client'
import { useState, useEffect } from 'react'
import type { Agent } from '@/lib/api'
import { activateAgent, suspendAgent, reinstateAgent, revokeAgent, isWorker, isManager, isExpired } from '@/lib/api'
import StatusBadge from './StatusBadge'
import {
  X, Copy, Check, GitBranch, Users, Clock,
  Zap, ShieldOff, RotateCcw, Shield, ScanSearch,
} from 'lucide-react'

interface Props {
  agent: Agent
  allAgents: Agent[]
  onClose: () => void
  onRefresh: () => Promise<void>
  onVerify: (id: string) => void
}

export default function AgentDrawer({ agent, allAgents, onClose, onRefresh, onVerify }: Props) {
  const [copied, setCopied] = useState(false)
  const [busy, setBusy] = useState(false)
  const [visible, setVisible] = useState(false)

  // Trigger slide-in on mount
  useEffect(() => {
    const id = requestAnimationFrame(() => setVisible(true))
    return () => cancelAnimationFrame(id)
  }, [])

  const worker = isWorker(agent)
  const manager = isManager(agent, allAgents)
  const expired = isExpired(agent)
  const workerCount = allAgents.filter(a => a.parentDnaId === agent.dnaId).length

  async function act(action: () => Promise<void>) {
    setBusy(true)
    try { await action(); await onRefresh(); onClose() }
    finally { setBusy(false) }
  }

  function copyId() {
    navigator.clipboard.writeText(agent.dnaId)
    setCopied(true)
    setTimeout(() => setCopied(false), 2000)
  }

  function handleVerify() {
    onClose()
    onVerify(agent.dnaId)
  }

  return (
    <>
      {/* Backdrop — fades in */}
      <div
        className={`fixed inset-0 z-40 bg-ink/20 backdrop-blur-sm transition-opacity duration-300 ${visible ? 'opacity-100' : 'opacity-0'}`}
        onClick={onClose}
      />

      {/* Drawer — slides in from right */}
      <div className={`fixed inset-y-0 right-0 z-50 flex w-full max-w-[480px] flex-col border-l border-border bg-panel shadow-2xl transition-transform duration-300 ease-out ${visible ? 'translate-x-0' : 'translate-x-full'}`}>

        {/* Header */}
        <div className="flex items-start justify-between border-b border-border px-6 py-5">
          <div className="space-y-2">
            <div className="flex flex-wrap items-center gap-2">
              {manager && (
                <span className="inline-flex items-center gap-1 rounded-full border border-blue-200 bg-blue-50 px-2.5 py-1 text-[10px] font-semibold uppercase tracking-[0.14em] text-blue-700">
                  <Users size={10} /> Manager
                </span>
              )}
              {worker && (
                <span className="inline-flex items-center gap-1 rounded-full border border-indigo-200 bg-indigo-50 px-2.5 py-1 text-[10px] font-semibold uppercase tracking-[0.14em] text-indigo-700">
                  <GitBranch size={10} /> Worker
                </span>
              )}
              {expired && (
                <span className="inline-flex items-center gap-1 rounded-full border border-amber-200 bg-amber-50 px-2.5 py-1 text-[10px] font-semibold uppercase tracking-[0.14em] text-amber-700">
                  <Clock size={10} /> Expired
                </span>
              )}
            </div>
            <h2 className="text-xl font-semibold text-ink">{agent.agentName}</h2>
          </div>
          <div className="ml-4 mt-0.5 flex shrink-0 items-center gap-2">
            {/* Verify shortcut */}
            <button
              onClick={handleVerify}
              title="Run live verification"
              className="inline-flex items-center gap-1.5 rounded-xl border border-accent bg-accentSoft px-3 py-1.5 text-xs font-medium text-accent transition-colors hover:bg-accent hover:text-white"
            >
              <ScanSearch size={13} />
              Verify
            </button>
            <button
              onClick={onClose}
              className="rounded-xl border border-border p-1.5 text-soft transition-colors hover:border-slate-300 hover:text-ink"
            >
              <X size={16} />
            </button>
          </div>
        </div>

        {/* Scrollable body */}
        <div className="flex-1 overflow-y-auto px-6 py-5 space-y-6">

          {/* DNA ID */}
          <div className="space-y-1.5">
            <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">DNA ID</p>
            <div className="flex items-center gap-2 rounded-[14px] border border-border bg-muted px-4 py-3">
              <p className="flex-1 truncate font-mono text-xs text-ink">{agent.dnaId}</p>
              <button
                onClick={copyId}
                className="shrink-0 rounded-lg p-1 text-soft transition-colors hover:text-ink"
                title="Copy DNA ID"
              >
                {copied ? <Check size={14} className="text-positive" /> : <Copy size={14} />}
              </button>
            </div>
          </div>

          {/* Status + Actions */}
          <div className="space-y-3">
            <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Status</p>
            <div className="flex items-center justify-between rounded-[14px] border border-border bg-muted px-4 py-3">
              <StatusBadge status={agent.status} />
              {busy ? (
                <span className="animate-pulse text-xs text-soft">Updating…</span>
              ) : (
                <div className="flex items-center gap-1.5">
                  {agent.status === 'PENDING' && (
                    <ActionBtn onClick={() => act(() => activateAgent(agent.dnaId))} title="Activate" color="text-positive hover:bg-positive/10">
                      <Zap size={13} /><span>Activate</span>
                    </ActionBtn>
                  )}
                  {agent.status === 'ACTIVE' && (<>
                    <ActionBtn onClick={() => {
                      const r = prompt('Suspend reason:')
                      if (r) act(() => suspendAgent(agent.dnaId, r))
                    }} title="Suspend" color="text-caution hover:bg-caution/10">
                      <ShieldOff size={13} /><span>Suspend</span>
                    </ActionBtn>
                    <ActionBtn onClick={() => {
                      const r = prompt('Revoke reason (required):')
                      if (r) act(() => revokeAgent(agent.dnaId, r))
                    }} title="Revoke" color="text-danger hover:bg-danger/10">
                      <Shield size={13} /><span>Revoke</span>
                    </ActionBtn>
                  </>)}
                  {agent.status === 'SUSPENDED' && (<>
                    <ActionBtn onClick={() => act(() => reinstateAgent(agent.dnaId))} title="Reinstate" color="text-positive hover:bg-positive/10">
                      <RotateCcw size={13} /><span>Reinstate</span>
                    </ActionBtn>
                    <ActionBtn onClick={() => {
                      const r = prompt('Revoke reason (required):')
                      if (r) act(() => revokeAgent(agent.dnaId, r))
                    }} title="Revoke" color="text-danger hover:bg-danger/10">
                      <Shield size={13} /><span>Revoke</span>
                    </ActionBtn>
                  </>)}
                </div>
              )}
            </div>
            {agent.revokedReason && (
              <div className="rounded-[14px] border border-rose-200 bg-rose-50 px-4 py-3 text-sm text-danger">
                <span className="font-medium">Revocation reason:</span> {agent.revokedReason}
              </div>
            )}
          </div>

          {/* Identity details */}
          <div className="space-y-1.5">
            <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Identity details</p>
            <div className="divide-y divide-border rounded-[14px] border border-border overflow-hidden">
              <Row label="Owner" value={agent.ownerName} />
              <Row label="Owner ID" value={agent.ownerId} mono />
              {agent.workloadIdentity && <Row label="Workload" value={agent.workloadIdentity} mono />}
              {agent.provenanceRef && <Row label="Provenance" value={agent.provenanceRef} />}
              <Row label="Jurisdiction">
                <span className="rounded-full border border-slate-200 bg-slate-50 px-2.5 py-0.5 text-xs font-semibold uppercase tracking-[0.12em] text-neutral">
                  {agent.jurisdiction}
                </span>
              </Row>
              <Row label="Created" value={new Date(agent.createdAt).toLocaleString()} />
              <Row label="Updated" value={new Date(agent.updatedAt).toLocaleString()} />
              {agent.approvedBy && (
                <Row label="Approved by" value={agent.approvedBy} />
              )}
              {agent.approvedAt && (
                <Row label="Approved at" value={new Date(agent.approvedAt).toLocaleString()} />
              )}
              {agent.approvalReason && (
                <Row label="Approval reason" value={agent.approvalReason} />
              )}
              {agent.revokedAt && (
                <Row label="Revoked at" value={new Date(agent.revokedAt).toLocaleString()} />
              )}
              {agent.expiresAt && (
                <Row label="Expires">
                  <span className={`text-sm ${expired ? 'font-semibold text-amber-700' : 'text-ink'}`}>
                    {new Date(agent.expiresAt).toLocaleString()}{expired ? ' — expired' : ''}
                  </span>
                </Row>
              )}
              <Row label="Version" value={`v${agent.version}`} />
            </div>
          </div>

          {/* Capabilities */}
          <div className="space-y-1.5">
            <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Declared capabilities</p>
            <div className="rounded-[14px] border border-border bg-muted px-4 py-3">
              {agent.capabilities.length > 0 ? (
                <div className="flex flex-wrap gap-1.5">
                  {agent.capabilities.map(c => (
                    <span key={c} className="rounded-full border border-blue-200 bg-white px-3 py-1 text-xs font-medium text-accent">
                      {c}
                    </span>
                  ))}
                </div>
              ) : (
                <p className="text-sm text-soft">No capabilities declared.</p>
              )}
            </div>
          </div>

          {/* Hierarchy */}
          {(worker || manager) && (
            <div className="space-y-1.5">
              <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Hierarchy</p>
              <div className="divide-y divide-border rounded-[14px] border border-border overflow-hidden">
                {worker && agent.parentDnaId && (
                  <div className="flex items-center justify-between bg-muted px-4 py-3">
                    <span className="text-sm text-soft">Parent manager</span>
                    <span className="font-mono text-xs text-indigo-700">{agent.parentDnaId.slice(0, 18)}…</span>
                  </div>
                )}
                {manager && (
                  <div className="flex items-center justify-between bg-muted px-4 py-3">
                    <span className="text-sm text-soft">Delegated workers</span>
                    <span className="text-sm font-semibold text-ink">{workerCount}</span>
                  </div>
                )}
              </div>
            </div>
          )}
        </div>
      </div>
    </>
  )
}

function Row({ label, value, mono, children }: {
  label: string
  value?: string
  mono?: boolean
  children?: React.ReactNode
}) {
  return (
    <div className="flex items-center justify-between gap-4 bg-muted px-4 py-3">
      <span className="shrink-0 text-sm text-soft">{label}</span>
      {children ?? (
        <span className={`text-right text-sm text-ink ${mono ? 'font-mono text-xs' : ''}`}>{value}</span>
      )}
    </div>
  )
}

function ActionBtn({ onClick, title, color, children }: {
  onClick: () => void
  title: string
  color: string
  children: React.ReactNode
}) {
  return (
    <button
      onClick={onClick}
      title={title}
      className={`inline-flex items-center gap-1.5 rounded-xl border border-border bg-white px-2.5 py-1.5 text-xs font-medium transition-colors ${color}`}
    >
      {children}
    </button>
  )
}
