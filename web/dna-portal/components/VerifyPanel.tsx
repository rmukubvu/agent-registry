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
    <div className="bg-panel border border-border rounded-xl p-5 space-y-4">
      <h3 className="text-sm font-semibold text-white">Live Verification</h3>

      <div className="flex gap-2">
        <input
          value={dnaId} onChange={e => setDnaId(e.target.value)}
          placeholder="DNA ID (UUID)" onKeyDown={e => e.key === 'Enter' && run()}
          className="flex-1 bg-surface border border-border rounded-lg px-3 py-2 text-sm text-white placeholder-gray-500 focus:outline-none focus:border-accent font-mono"
        />
        <button onClick={run} disabled={busy || !dnaId.trim()}
          className="px-3 py-2 bg-accent hover:bg-accent/80 rounded-lg text-white transition-colors disabled:opacity-40">
          <Search size={16} />
        </button>
      </div>

      <div className="flex gap-2 items-center">
        <span className="text-xs text-gray-400">Tool:</span>
        <input value={tool} onChange={e => setTool(e.target.value)}
          className="flex-1 bg-surface border border-border rounded-lg px-3 py-1.5 text-xs text-white placeholder-gray-500 focus:outline-none focus:border-accent font-mono"
        />
      </div>

      {error && <p className="text-danger text-xs">{error}</p>}

      {verify && (
        <div className="space-y-3 pt-1">
          {/* Registry result */}
          <div className="rounded-lg bg-surface border border-border p-3 space-y-2">
            <div className="flex items-center justify-between">
              <span className="text-xs text-gray-400">Registry status</span>
              <StatusBadge status={verify.status} />
            </div>
            <div className="text-sm font-medium text-white">{verify.agentName}</div>
            <div className="flex flex-wrap gap-1">
              {verify.capabilities.map(c => (
                <span key={c} className="text-xs bg-accent/10 border border-accent/20 text-accent px-2 py-0.5 rounded-full">
                  {c}
                </span>
              ))}
            </div>
          </div>

          {/* Enforcer result */}
          {enforce && (
            <div className={`rounded-lg border p-3 flex items-start gap-3 ${
              enforce.allowed
                ? 'bg-positive/5 border-positive/20'
                : 'bg-danger/5 border-danger/20'
            }`}>
              {enforce.allowed
                ? <ShieldCheck size={20} className="text-positive mt-0.5 shrink-0" />
                : <ShieldX     size={20} className="text-danger mt-0.5 shrink-0" />
              }
              <div>
                <div className={`text-sm font-semibold ${enforce.allowed ? 'text-positive' : 'text-danger'}`}>
                  {enforce.allowed ? 'TOOL CALL ALLOWED' : 'TOOL CALL BLOCKED'}
                </div>
                <div className="text-xs text-gray-400 mt-0.5">
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
