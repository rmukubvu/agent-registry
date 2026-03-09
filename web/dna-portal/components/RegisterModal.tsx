'use client'
import { useState } from 'react'
import { registerAgent, activateAgent } from '@/lib/api'
import { X, GitBranch, Clock } from 'lucide-react'

interface Props { onClose: () => void; onSuccess: () => void }

export default function RegisterModal({ onClose, onSuccess }: Props) {
  const [form, setForm] = useState({
    agentName: '', ownerName: '', ownerId: crypto.randomUUID(),
    jurisdiction: 'US', capabilities: 'mcp:filesystem', publicKeyHex: '',
    parentDnaId: '',   // optional — leave blank for manager agents
    expiresAt: '',     // optional — datetime-local string; blank = persistent
  })
  const [autoActivate, setAutoActivate] = useState(true)
  const [busy, setBusy] = useState(false)
  const [error, setError] = useState('')

  const set = (k: keyof typeof form) => (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) =>
    setForm(f => ({ ...f, [k]: e.target.value }))

  const isWorker = form.parentDnaId.trim().length > 0

  async function submit(e: React.FormEvent) {
    e.preventDefault()
    if (!form.agentName.trim()) { setError('Agent name is required'); return }
    if (isWorker && !/^[0-9a-f-]{36}$/i.test(form.parentDnaId.trim())) {
      setError('Parent DNA ID must be a valid UUID'); return
    }
    setBusy(true); setError('')
    try {
      const caps = form.capabilities.split(',').map(c => c.trim()).filter(Boolean)
      const hex  = form.publicKeyHex.trim() || Array.from(crypto.getRandomValues(new Uint8Array(32)))
        .map(b => b.toString(16).padStart(2, '0')).join('')

      // Convert datetime-local to ISO 8601 (with Z suffix) if set
      const expiresAt = form.expiresAt ? new Date(form.expiresAt).toISOString() : null

      const result = await registerAgent({
        agentName: form.agentName.trim(), publicKeyHex: hex,
        ownerId: form.ownerId, ownerName: form.ownerName.trim() || 'Unknown',
        jurisdiction: form.jurisdiction, capabilities: caps,
        parentDnaId: isWorker ? form.parentDnaId.trim() : null,
        expiresAt,
        idemKey: `register-${Date.now()}`,
      })
      if (autoActivate) await activateAgent(result.dnaId)
      onSuccess()
    } catch (err: unknown) {
      setError(err instanceof Error ? err.message : 'Registration failed')
    } finally { setBusy(false) }
  }

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-[rgba(21,35,45,0.24)] p-4 backdrop-blur-sm">
      <div className="w-full max-w-xl rounded-[24px] border border-border bg-panel shadow-[0_22px_60px_rgba(21,35,45,0.16)]">
        <div className="flex items-center justify-between border-b border-border px-6 py-4">
          <div>
            <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">New record</p>
            <h2 className="mt-1 text-xl font-semibold text-ink">Register agent identity</h2>
            <p className="mt-2 max-w-lg text-sm leading-6 text-soft">
              Create a persistent manager identity or a delegated worker with an optional expiry.
            </p>
          </div>
          <button onClick={onClose} className="text-soft transition-colors hover:text-ink">
            <X size={20} />
          </button>
        </div>

        <form onSubmit={submit} className="p-6 space-y-4">
          <Field label="Agent Name *">
            <input value={form.agentName} onChange={set('agentName')}
              placeholder="ClaudeIntakeAgent" className={input} />
          </Field>
          <Field label="Owner Name">
            <input value={form.ownerName} onChange={set('ownerName')}
              placeholder="Acme Corp" className={input} />
          </Field>
          <div className="grid grid-cols-1 gap-3 sm:grid-cols-2">
            <Field label="Jurisdiction">
              <select value={form.jurisdiction} onChange={set('jurisdiction')} className={input}>
                {['US','GB','ZA','EU','AU','CA','SG','IN'].map(j => (
                  <option key={j} value={j}>{j}</option>
                ))}
              </select>
            </Field>
            <Field label="Capabilities">
              <input value={form.capabilities} onChange={set('capabilities')}
                placeholder="mcp:filesystem,mcp:web" className={input} />
            </Field>
          </div>
          <Field label="Ed25519 Public Key (hex — leave blank to generate)">
            <input value={form.publicKeyHex} onChange={set('publicKeyHex')}
              placeholder="auto-generated" className={input} />
          </Field>

          {/* ── Hierarchical DNA ──────────────────────────────────────── */}
          <div className="space-y-3 border-t border-border pt-4">
            <p className="flex items-center gap-1.5 text-xs text-soft">
              <GitBranch size={12} className="text-indigo-600" />
              Hierarchical DNA — optional
            </p>
            <Field label="Parent DNA ID (UUID — leave blank for manager agent)">
              <input value={form.parentDnaId} onChange={set('parentDnaId')}
                placeholder="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
                className={`${input} ${isWorker ? 'border-indigo-300 focus:border-indigo-500' : ''}`} />
            </Field>
            {isWorker && (
              <p className="-mt-1 flex items-center gap-1 text-[11px] leading-5 text-indigo-700">
                <GitBranch size={10} />
                This agent will be registered as a Worker under the specified Manager.
                The parent must be ACTIVE.
              </p>
            )}
            <Field label={<span className="flex items-center gap-1"><Clock size={11} />Expires At (leave blank for persistent)</span>}>
              <input type="datetime-local" value={form.expiresAt} onChange={set('expiresAt')}
                className={input} />
            </Field>
          </div>

          <label className="flex cursor-pointer items-center gap-2 rounded-[18px] border border-border bg-muted px-4 py-3 text-sm text-soft">
            <input type="checkbox" checked={autoActivate}
              onChange={e => setAutoActivate(e.target.checked)}
              className="accent-accent" />
            Auto-activate after registration
          </label>

          {error && <p className="rounded-2xl border border-rose-200 bg-rose-50 px-4 py-3 text-sm text-danger">{error}</p>}

          <div className="flex justify-end gap-3 pt-2">
            <button type="button" onClick={onClose}
              className="px-4 py-2 text-sm text-soft transition-colors hover:text-ink">
              Cancel
            </button>
            <button type="submit" disabled={busy}
              className="rounded-2xl border border-accent bg-accent px-5 py-2.5 text-sm font-medium text-white transition-colors hover:bg-[#1158c3] disabled:opacity-50">
              {busy ? 'Registering…' : isWorker ? 'Register Worker' : 'Register Manager'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

function Field({ label, children }: { label: React.ReactNode; children: React.ReactNode }) {
  return (
    <div>
      <label className="mb-1 block text-xs font-semibold uppercase tracking-[0.14em] text-soft">{label}</label>
      {children}
    </div>
  )
}

const input = 'w-full rounded-2xl border border-border bg-muted px-4 py-3 text-sm text-ink placeholder:text-soft focus:outline-none focus:border-accent focus:bg-panel transition-colors'
