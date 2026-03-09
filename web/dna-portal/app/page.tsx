'use client'
import { useState, useEffect, useCallback } from 'react'
import { listAgents, type Agent } from '@/lib/api'
import AgentTable from '@/components/AgentTable'
import StatsBar from '@/components/StatsBar'
import VerifyPanel from '@/components/VerifyPanel'
import RegisterModal from '@/components/RegisterModal'
import { RefreshCw, Plus, Dna } from 'lucide-react'

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

  return (
    <div className="min-h-screen bg-surface">
      {/* Header */}
      <header className="border-b border-border bg-panel/80 backdrop-blur sticky top-0 z-40">
        <div className="max-w-7xl mx-auto px-6 h-14 flex items-center justify-between">
          <div className="flex items-center gap-2.5">
            <div className="w-7 h-7 rounded-lg bg-accent flex items-center justify-center">
              <Dna size={16} className="text-white" />
            </div>
            <span className="font-semibold text-white text-sm">Agent DNA Portal</span>
            <span className="hidden sm:block text-xs text-gray-500 ml-1">
              Global AI Agent Identity &amp; Revocation Registry
            </span>
          </div>
          <div className="flex items-center gap-2">
            <button onClick={refresh}
              className="p-2 text-gray-400 hover:text-white hover:bg-muted/20 rounded-lg transition-colors">
              <RefreshCw size={15} />
            </button>
            <button onClick={() => setShowRegister(true)}
              className="flex items-center gap-1.5 px-3 py-1.5 bg-accent hover:bg-accent/80 text-white text-sm rounded-lg transition-colors">
              <Plus size={15} />
              <span>Register Agent</span>
            </button>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-6 py-8 space-y-6">
        {/* Stats */}
        <StatsBar agents={agents} />

        {/* Main grid */}
        <div className="grid grid-cols-1 lg:grid-cols-[1fr_280px] gap-6 items-start">
          {/* Agent table */}
          <div className="space-y-3">
            <div className="flex items-center justify-between">
              <h2 className="text-sm font-semibold text-white">Registered Agents</h2>
              <span className="text-xs text-gray-500">{agents.length} total</span>
            </div>
            {loading ? (
              <div className="text-center py-16 text-gray-500 text-sm animate-pulse">
                Loading agents…
              </div>
            ) : (
              <AgentTable agents={agents} onRefresh={refresh} />
            )}
          </div>

          {/* Verify panel */}
          <VerifyPanel />
        </div>

        {/* Footer note */}
        <p className="text-center text-xs text-gray-600 pt-4">
          dna-registry :8081 &nbsp;·&nbsp; dna-enforcer :8082 &nbsp;·&nbsp; portal :3002
        </p>
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
