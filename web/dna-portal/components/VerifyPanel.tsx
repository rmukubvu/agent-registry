'use client'
import { useState } from 'react'
import { verifyAgent, enforceToolCall, type VerifyResult, type EnforceResult } from '@/lib/api'
import StatusBadge from './StatusBadge'
import { Search, ShieldCheck, ShieldX } from 'lucide-react'

export default function VerifyPanel() {
  const [dnaId, setDnaId] = useState('')
  const [tool, setTool] = useState('mcp:filesystem:read')
  const [verify, setVerify] = useState<VerifyResult | null>(null)
  const [enforce, setEnforce] = useState<EnforceResult | null>(null)
  const [error, setError] = useState('')
  const [busy, setBusy] = useState(false)

  async function run() {
    if (!dnaId.trim()) return
    setBusy(true); setError(''); setVerify(null); setEnforce(null)
    try {
      const [v, e] = await Promise.all([
        verifyAgent(dnaId.trim()),
        enforceToolCall(dnaId.trim(), tool),
      ])
      setVerify(v); setEnforce(e)
    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : 'Verification failed')
    } finally { setBusy(false) }
  }

  return (
    <div className="space-y-5 rounded-[20px] border border-border bg-panel p-5">
      <div className="space-y-2">
        <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Live verification</p>
        <h3 className="text-[1.65rem] font-semibold leading-[1.15] text-ink">Run a live registry and policy check</h3>
        <p className="text-sm leading-7 text-soft">
          Query a DNA record, inspect its declared scope, and send the same identifier through the enforcer.
        </p>
      </div>

      <div className="flex gap-2">
        <input
          value={dnaId} onChange={e => setDnaId(e.target.value)}
          placeholder="DNA ID (UUID)" onKeyDown={e => e.key === 'Enter' && run()}
          className="flex-1 rounded-2xl border border-border bg-muted px-4 py-3 text-sm text-ink placeholder:text-soft focus:outline-none focus:border-accent focus:bg-panel font-mono"
        />
        <button onClick={run} disabled={busy || !dnaId.trim()}
          className="rounded-2xl border border-accent bg-accent px-4 py-3 text-white transition-colors hover:bg-[#1158c3] disabled:cursor-not-allowed disabled:opacity-40">
          <Search size={16} />
        </button>
      </div>

      <div className="flex gap-2 items-center">
        <span className="text-xs font-medium uppercase tracking-[0.16em] text-soft">Tool</span>
        <input value={tool} onChange={e => setTool(e.target.value)}
          className="flex-1 rounded-2xl border border-border bg-muted px-4 py-2.5 text-xs text-ink placeholder:text-soft focus:outline-none focus:border-accent focus:bg-panel font-mono"
        />
      </div>

      {error && <p className="rounded-2xl border border-rose-200 bg-rose-50 px-4 py-3 text-sm text-danger">{error}</p>}

      {verify && (
        <div className="space-y-4 pt-1">
          {/* Registry result */}
          <div className="space-y-3 rounded-[18px] border border-border bg-muted p-4">
            <div className="flex items-center justify-between">
              <span className="text-[11px] font-semibold uppercase tracking-[0.16em] text-soft">Registry status</span>
              <StatusBadge status={verify.status} />
            </div>
            <div className="text-base font-semibold text-ink">{verify.agentName}</div>
            <div className="flex flex-wrap gap-1">
              {verify.capabilities.map(c => (
                <span key={c} className="rounded-full border border-blue-200 bg-white px-2.5 py-1 text-xs font-medium text-accent">
                  {c}
                </span>
              ))}
            </div>
          </div>

          {/* Enforcer result */}
          {enforce && (
            <div className={`flex items-start gap-3 rounded-[20px] border p-3 ${
              enforce.allowed
                ? 'bg-emerald-50 border-emerald-200'
                : 'bg-rose-50 border-rose-200'
            }`}>
              {enforce.allowed
                ? <ShieldCheck size={20} className="text-positive mt-0.5 shrink-0" />
                : <ShieldX     size={20} className="text-danger mt-0.5 shrink-0" />
              }
              <div>
                <div className={`text-sm font-semibold ${enforce.allowed ? 'text-positive' : 'text-danger'}`}>
                  {enforce.allowed ? 'TOOL CALL ALLOWED' : 'TOOL CALL BLOCKED'}
                </div>
                <div className="mt-1 text-sm leading-6 text-soft">
                  {enforce.reason ?? `${enforce.toolName} — permitted`}
                </div>
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  )
}
