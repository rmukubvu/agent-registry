'use client'
import { useState, useEffect, useCallback } from 'react'
import { listAgents, type Agent } from '@/lib/api'
import AgentTable from '@/components/AgentTable'
import StatsBar from '@/components/StatsBar'
import VerifyPanel from '@/components/VerifyPanel'
import RegisterModal from '@/components/RegisterModal'
import { RefreshCw, Plus, Dna, ArrowRight, ShieldCheck, Workflow, Fingerprint, BookOpen, PanelRight } from 'lucide-react'

export default function DashboardPage() {
  const [agents, setAgents]     = useState<Agent[]>([])
  const [loading, setLoading]   = useState(true)
  const [showRegister, setShowRegister] = useState(false)

  const refresh = useCallback(async () => {
    try {
      const data = await listAgents()
      setAgents(data)
    } catch {
      // Registry not running — show empty state
      setAgents([])
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => { refresh() }, [refresh])

  const active = agents.filter(agent => agent.status === 'ACTIVE').length
  const workers = agents.filter(agent => agent.parentDnaId).length
  const pending = agents.filter(agent => agent.status === 'PENDING').length

  const principles = [
    {
      icon: <Fingerprint size={18} className="text-accent" />,
      title: 'Identity first',
      body: 'Every record is structured as a verifiable identity with owner, jurisdiction, scope, and lifecycle state.',
    },
    {
      icon: <Workflow size={18} className="text-accent" />,
      title: 'Delegated hierarchy',
      body: 'Manager and worker records are linked explicitly, allowing targeted suspension without flattening the whole system.',
    },
    {
      icon: <ShieldCheck size={18} className="text-accent" />,
      title: 'Operational control',
      body: 'Registry and enforcer checks can be demonstrated live from the same interface during review or due diligence.',
    },
  ]

  return (
    <div className="min-h-screen bg-surface text-ink">
      <header className="sticky top-0 z-40 border-b border-border bg-surface/90 backdrop-blur-xl">
        <div className="mx-auto flex h-14 max-w-[1180px] items-center justify-between px-6">
          <div className="flex items-center gap-3">
            <div className="flex h-9 w-9 items-center justify-center rounded-xl border border-blue-200 bg-accentSoft">
              <Dna size={16} className="text-accent" />
            </div>
            <div>
              <p className="text-sm font-semibold text-ink">Agent DNA</p>
              <p className="hidden text-xs text-soft sm:block">
                Identity, verification, and revocation for agentic systems
              </p>
            </div>
          </div>
          <div className="hidden items-center gap-6 lg:flex">
            <a href="#overview" className="text-sm text-soft transition-colors hover:text-ink">Overview</a>
            <a href="#operations" className="text-sm text-soft transition-colors hover:text-ink">Operations</a>
            <a href="#registry" className="text-sm text-soft transition-colors hover:text-ink">Registry</a>
            <a href="#verification" className="text-sm text-soft transition-colors hover:text-ink">Verification</a>
          </div>
          <div className="flex items-center gap-2">
            <button
              onClick={refresh}
              className="rounded-2xl border border-border bg-panel p-2.5 text-soft transition-colors hover:border-slate-300 hover:text-ink"
              aria-label="Refresh registry data"
            >
              <RefreshCw size={15} />
            </button>
            <button
              onClick={() => setShowRegister(true)}
              className="flex items-center gap-1.5 rounded-2xl border border-accent bg-accent px-4 py-2.5 text-sm font-medium text-white transition-colors hover:bg-[#1158c3]"
            >
              <Plus size={15} />
              <span>Register Agent</span>
            </button>
          </div>
        </div>
      </header>

      <main className="mx-auto max-w-[1180px] px-6 py-8">
        <div className="grid items-start gap-10 lg:grid-cols-[minmax(0,1fr)_320px]">
          <div className="space-y-8">
            <section id="overview" className="rounded-[20px] border border-border bg-panel px-7 py-8 sm:px-10">
              <div className="max-w-[760px] space-y-6">
                <div className="flex items-center gap-2 text-[11px] font-semibold uppercase tracking-[0.22em] text-soft">
                  <BookOpen size={14} />
                  Agent governance infrastructure
                </div>
                <div className="h-px w-24 bg-border" />
                <div className="space-y-4">
                  <h1
                    className="max-w-[720px] text-[2.45rem] leading-[1.02] text-ink sm:text-[3.2rem]"
                    style={{ fontFamily: 'var(--font-serif)' }}
                  >
                    Identity infrastructure for serious agent systems.
                  </h1>
                  <p className="max-w-[640px] text-base leading-8 text-soft sm:text-[1.02rem]">
                    Agent DNA gives teams a neutral control plane for agent identity. Register records, review delegated
                    worker relationships, and demonstrate policy enforcement from a single operational surface.
                  </p>
                </div>

                <div className="grid gap-4 sm:grid-cols-3">
                  <div className="rounded-[20px] border border-border bg-muted px-5 py-4">
                    <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Active now</p>
                    <p className="mt-3 text-3xl font-semibold text-ink">{active}</p>
                  </div>
                  <div className="rounded-[20px] border border-border bg-muted px-5 py-4">
                    <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Workers</p>
                    <p className="mt-3 text-3xl font-semibold text-ink">{workers}</p>
                  </div>
                  <div className="rounded-[20px] border border-border bg-muted px-5 py-4">
                    <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Awaiting review</p>
                    <p className="mt-3 text-3xl font-semibold text-ink">{pending}</p>
                  </div>
                </div>

                <div className="grid gap-3 lg:grid-cols-3">
                  {principles.map(principle => (
                    <div key={principle.title} className="rounded-[18px] border border-border bg-panel px-4 py-4">
                      <div className="mb-3 inline-flex rounded-2xl border border-blue-200 bg-accentSoft p-2.5">
                        {principle.icon}
                      </div>
                      <h2 className="text-base font-semibold text-ink">{principle.title}</h2>
                      <p className="mt-2 text-sm leading-7 text-soft">{principle.body}</p>
                    </div>
                  ))}
                </div>

                <div className="rounded-[18px] border border-blue-200 bg-accentSoft px-5 py-4 text-sm leading-7 text-[#24507b]">
                  The portal is now positioned more like documentation than a security dashboard, so the product reads as a standards-ready platform rather than an internal admin tool.
                </div>
              </div>
            </section>

            <section id="operations" className="space-y-4">
              <div className="flex flex-col gap-3 sm:flex-row sm:items-end sm:justify-between">
                <div>
                  <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Operational state</p>
                  <h2 className="mt-2 text-2xl font-semibold text-ink">Registry snapshot</h2>
                  <p className="mt-2 max-w-2xl text-sm leading-7 text-soft">
                    Current counts and lifecycle distribution across active, suspended, and revoked identities.
                  </p>
                </div>
              </div>
              <StatsBar agents={agents} />
            </section>

            <section id="registry" className="space-y-4">
              <div className="flex flex-col gap-3 sm:flex-row sm:items-end sm:justify-between">
                <div>
                  <p className="text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">Registry records</p>
                  <h2 className="mt-2 text-2xl font-semibold text-ink">Registered agent identities</h2>
                  <p className="mt-2 max-w-2xl text-sm leading-7 text-soft">
                    Review owner metadata, jurisdiction, delegated worker relationships, capability declarations, and status transitions.
                  </p>
                </div>
                <span className="inline-flex items-center gap-2 text-sm text-soft">
                  {agents.length} total records
                  <ArrowRight size={15} />
                </span>
              </div>
              {loading ? (
                <div className="rounded-[28px] border border-border bg-panel px-8 py-20 text-center text-soft shadow-[0_18px_40px_rgba(21,35,45,0.05)]">
                  Loading registry records…
                </div>
              ) : (
                <AgentTable agents={agents} onRefresh={refresh} />
              )}
            </section>
          </div>

          <div className="space-y-5 lg:sticky lg:top-20">
            <section className="rounded-[20px] border border-border bg-panel p-5">
              <div className="flex items-center gap-2 text-[11px] font-semibold uppercase tracking-[0.18em] text-soft">
                <PanelRight size={14} />
                On this page
              </div>
              <nav className="mt-4 space-y-2 text-sm">
                <a href="#overview" className="block rounded-xl px-3 py-2 text-soft transition-colors hover:bg-muted hover:text-ink">Overview</a>
                <a href="#operations" className="block rounded-xl px-3 py-2 text-soft transition-colors hover:bg-muted hover:text-ink">Operational state</a>
                <a href="#registry" className="block rounded-xl px-3 py-2 text-soft transition-colors hover:bg-muted hover:text-ink">Registered identities</a>
                <a href="#verification" className="block rounded-xl px-3 py-2 text-soft transition-colors hover:bg-muted hover:text-ink">Verification</a>
              </nav>
              <div className="mt-5 rounded-[18px] border border-border bg-muted px-4 py-4">
                <p className="text-sm font-semibold text-ink">Presentation note</p>
                <p className="mt-2 text-sm leading-7 text-soft">
                  This interface is intentionally restrained so the governance model reads clearly in technical and executive settings.
                </p>
              </div>
            </section>

            <section id="verification">
              <VerifyPanel />
            </section>
          </div>
        </div>

        <footer className="mt-10 border-t border-border pt-6">
          <p className="text-sm leading-7 text-soft">
            Agent DNA is presented here as a product-grade control layer: structured identity, delegated trust, and immediate policy feedback.
          </p>
        </footer>
      </main>

      {showRegister && (
        <RegisterModal
          onClose={() => setShowRegister(false)}
          onSuccess={() => { setShowRegister(false); refresh() }}
        />
      )}
    </div>
  )
}
