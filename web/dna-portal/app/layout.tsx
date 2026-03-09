import type { Metadata } from 'next'
import './globals.css'

export const metadata: Metadata = {
  title: 'Agent DNA',
  description: 'Identity, verification, and revocation for agentic systems.',
}

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body className="min-h-screen bg-surface text-ink antialiased">
        {children}
      </body>
    </html>
  )
}
