import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    './app/**/*.{ts,tsx}',
    './components/**/*.{ts,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        surface:    '#f3f6f8',
        panel:      '#ffffff',
        border:     '#d6e0e7',
        muted:      '#edf2f6',
        ink:        '#15232d',
        soft:       '#5f6d79',
        accent:     '#1668e3',
        accentSoft: '#ebf3ff',
        positive:   '#15803d',
        caution:    '#b45309',
        danger:     '#b42318',
        neutral:    '#475467',
      },
    },
  },
  plugins: [],
}
export default config
