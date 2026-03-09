import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    './app/**/*.{ts,tsx}',
    './components/**/*.{ts,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        surface:  '#0f0f0f',
        panel:    '#161616',
        border:   '#222222',
        muted:    '#3a3a3a',
        accent:   '#6366f1',        // indigo — trustworthy, tech, security
        positive: '#22c55e',        // green  — ACTIVE / ALLOWED
        caution:  '#f59e0b',        // amber  — SUSPENDED
        danger:   '#ef4444',        // red    — REVOKED / DENIED
        neutral:  '#6b7280',        // gray   — PENDING
      },
    },
  },
  plugins: [],
}
export default config
