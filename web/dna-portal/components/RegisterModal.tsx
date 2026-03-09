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
    <div className="fixed inset-0 bg-black/70 backdrop-blur-sm flex items-center justify-center z-50 p-4">
      <div className="bg-panel border border-border rounded-2xl w-full max-w-md shadow-2xl">
        <div className="flex items-center justify-between px-6 py-4 border-b border-border">
          <h2 className="text-lg font-semibold text-white">Register Agent</h2>
          <button onClick={onClose} className="text-gray-400 hover:text-white transition-colors">
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
          <div className="grid grid-cols-2 gap-3">
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
          <div className="border-t border-border pt-4 space-y-3">
            <p className="text-xs text-gray-500 flex items-center gap-1.5">
              <GitBranch size={12} className="text-purple-400" />
              Hierarchical DNA — optional
            </p>
            <Field label="Parent DNA ID (UUID — leave blank for manager agent)">
              <input value={form.parentDnaId} onChange={set('parentDnaId')}
                placeholder="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
                className={`${input} ${isWorker ? 'border-purple-500/50 focus:border-purple-500' : ''}`} />
            </Field>
            {isWorker && (
              <p className="text-[10px] text-purple-400 -mt-1 flex items-center gap-1">
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

          <label className="flex items-center gap-2 cursor-pointer text-sm text-gray-300">
            <input type="checkbox" checked={autoActivate}
              onChange={e => setAutoActivate(e.target.checked)}
              className="accent-accent" />
            Auto-activate after registration
          </label>

          {error && <p className="text-danger text-sm">{error}</p>}

          <div className="flex justify-end gap-3 pt-2">
            <button type="button" onClick={onClose}
              className="px-4 py-2 text-sm text-gray-400 hover:text-white transition-colors">
              Cancel
            </button>
            <button type="submit" disabled={busy}
              className="px-5 py-2 bg-accent hover:bg-accent/80 text-white text-sm rounded-lg transition-colors disabled:opacity-50">
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
      <label className="block text-xs text-gray-400 mb-1">{label}</label>
      {children}
    </div>
  )
}

const input = 'w-full bg-surface border border-border rounded-lg px-3 py-2 text-sm text-white placeholder-gray-500 focus:outline-none focus:border-accent transition-colors'
