#!/usr/bin/env bash

set -euo pipefail

REGISTRY_URL="${REGISTRY_URL:-https://registry-production-0f52.up.railway.app}"
ENFORCER_URL="${ENFORCER_URL:-https://enforcer-production.up.railway.app}"
JQ_BIN="${JQ_BIN:-jq}"

ACTIVE_ID="${ACTIVE_ID:-b4894337-ab0f-4c31-90f9-ea103a981af1}"
PENDING_ID="${PENDING_ID:-3c2c9c49-bd3d-470f-a46d-4605062524a7}"
SUSPENDED_ID="${SUSPENDED_ID:-aab44d7c-9294-477c-90ad-dd25fefcf212}"
REVOKED_ID="${REVOKED_ID:-afb68e86-769f-41ac-85e5-7fd0dbd9c8f2}"
WORKER_ACTIVE_ID="${WORKER_ACTIVE_ID:-1b34a09d-1f4a-450b-b5c7-731e77de04c2}"
WORKER_CASCADE_ID="${WORKER_CASCADE_ID:-27d909b1-0a38-44d7-942b-defe8c648e4e}"

BOLD="\033[1m"
CYAN="\033[96m"
GREEN="\033[92m"
YELLOW="\033[93m"
RED="\033[91m"
RESET="\033[0m"
DIM="\033[2m"
HR="────────────────────────────────────────────────────────────────────────────"

print_header() {
  echo
  echo -e "${BOLD}${CYAN}${HR}${RESET}"
  echo -e "${BOLD}${CYAN}Agent DNA Live Demo${RESET}"
  echo -e "${DIM}registry=${REGISTRY_URL}${RESET}"
  echo -e "${DIM}enforcer=${ENFORCER_URL}${RESET}"
  echo -e "${BOLD}${CYAN}${HR}${RESET}"
  echo
}

section() {
  echo
  echo -e "${BOLD}${CYAN}── $1${RESET}"
}

show_verify() {
  local label="$1"
  local dna_id="$2"
  echo -e "${DIM}${label} verify${RESET}"
  curl -sS "${REGISTRY_URL}/v1/agents/${dna_id}/verify" | pretty_print
}

show_enforce() {
  local label="$1"
  local dna_id="$2"
  local tool_name="$3"
  echo -e "${DIM}${label} enforce (${tool_name})${RESET}"
  curl -sS -X POST "${ENFORCER_URL}/v1/enforce" \
    -H 'content-type: application/json' \
    -d "{\"dnaId\":\"${dna_id}\",\"toolName\":\"${tool_name}\",\"toolPayload\":{}}" | pretty_print
}

pretty_print() {
  if command -v "${JQ_BIN}" >/dev/null 2>&1; then
    "${JQ_BIN}"
  else
    cat
  fi
}

check_up() {
  curl -fsS "${REGISTRY_URL}/q/health/ready" >/dev/null
  curl -fsS "${ENFORCER_URL}/q/health/live" >/dev/null
}

main() {
  print_header
  check_up
  echo -e "${GREEN}✓ Services reachable${RESET}"

  section "1. Active agent is allowed"
  show_verify "Active agent" "${ACTIVE_ID}"
  show_enforce "Active agent" "${ACTIVE_ID}" "mcp:web-search"

  section "2. Pending agent is blocked"
  show_verify "Pending agent" "${PENDING_ID}"
  show_enforce "Pending agent" "${PENDING_ID}" "mcp:web-search"

  section "3. Suspended agent is blocked"
  show_verify "Suspended agent" "${SUSPENDED_ID}"
  show_enforce "Suspended agent" "${SUSPENDED_ID}" "mcp:filesystem:write"

  section "4. Revoked agent is blocked"
  show_verify "Revoked agent" "${REVOKED_ID}"
  show_enforce "Revoked agent" "${REVOKED_ID}" "mcp:web-search"

  section "5. Worker agent is allowed while parent is active"
  show_verify "Worker active" "${WORKER_ACTIVE_ID}"
  show_enforce "Worker active" "${WORKER_ACTIVE_ID}" "mcp:filesystem:read"

  section "6. Worker is blocked after parent revocation"
  show_verify "Worker cascade" "${WORKER_CASCADE_ID}"
  show_enforce "Worker cascade" "${WORKER_CASCADE_ID}" "mcp:filesystem:read"

  echo
  echo -e "${BOLD}${YELLOW}Key story:${RESET}"
  echo -e "${DIM}The registry is the source of truth for agent identity and lifecycle.${RESET}"
  echo -e "${DIM}The enforcer checks that truth at runtime before any MCP tool call is allowed.${RESET}"
  echo -e "${DIM}Delegated workers inherit trust from their parent and can be cascade-blocked.${RESET}"
  echo
}

main "$@"
