#!/usr/bin/env bash
# ─────────────────────────────────────────────────────────────────────────────
# Agent DNA — Full Demo
#
# Prerequisites (run in separate terminals before this script):
#   1.  Docker Desktop running
#   2.  dna-registry:   cd /Users/robson/code/agent-dna
#                       JAVA_HOME=.../openjdk-23.0.1/Contents/Home \
#                       ./gradlew :services:dna-registry:quarkusDev
#   3.  dna-enforcer:   JAVA_HOME=... ./gradlew :services:dna-enforcer:quarkusDev
#   4.  dna-portal:     cd web/dna-portal && npm run dev
#
# Then run:  bash demo/run_demo.sh
# ─────────────────────────────────────────────────────────────────────────────

set -euo pipefail

BOLD="\033[1m"
CYAN="\033[96m"
GREEN="\033[92m"
RED="\033[91m"
RESET="\033[0m"
HR="────────────────────────────────────────────────────────────────────────────"

echo -e "\n${BOLD}${CYAN}${HR}"
echo -e "  Agent DNA — POC Demo"
echo -e "  Global AI Agent Identity & Revocation Registry"
echo -e "${HR}${RESET}\n"

# Verify services are up
echo -e "Checking services…"
check() {
  local name=$1 url=$2
  if curl -sf "$url" > /dev/null 2>&1; then
    echo -e "  ${GREEN}✓ $name${RESET}"
  else
    echo -e "  ${RED}✗ $name not responding at $url${RESET}"
    echo -e "    Please start it before running the demo."
    exit 1
  fi
}
check "dna-registry  (port 8081)" "http://localhost:8081/v1/agents"
check "dna-enforcer  (port 8082)" "http://localhost:8082/q/health/live" || true  # may not have health
echo ""

# ── Demo A: Registered Agent ──────────────────────────────────────────────────
echo -e "${BOLD}${CYAN}╔════════════════════════════════════════╗"
echo -e "║  DEMO 1 — Legitimate Registered Agent  ║"
echo -e "╚════════════════════════════════════════╝${RESET}"
python3 "$(dirname "$0")/registered_agent.py"

# ── Demo B: Rogue Agents ──────────────────────────────────────────────────────
echo -e "${BOLD}${CYAN}╔══════════════════════════════════╗"
echo -e "║  DEMO 2 — Rogue Agent Scenarios  ║"
echo -e "╚══════════════════════════════════╝${RESET}"
python3 "$(dirname "$0")/rogue_agent.py"

# ── Demo C: Hierarchical DNA ──────────────────────────────────────────────────
echo -e "${BOLD}${CYAN}╔══════════════════════════════════════════════════════╗"
echo -e "║  DEMO 3 — Hierarchical DNA (Cascade + Ephemeral TTL) ║"
echo -e "╚══════════════════════════════════════════════════════╝${RESET}"
python3 "$(dirname "$0")/hierarchical_demo.py"

echo -e "${BOLD}${GREEN}"
echo -e "  ${HR}"
echo -e "  Demo complete."
echo -e "  Portal: http://localhost:3002"
echo -e "  ${HR}${RESET}\n"
