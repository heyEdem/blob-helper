# Epic 6: Reconciliation and Observability

**Status:** In Progress
**Progress:** 4/5
**Sources:** [ADR-003](../../adrs/ADR-003-release-delete-and-reconciliation.md), [PLAN-006](../../implementation-plans/PLAN-006-reconciliation-observability.md)

## Goal

Add drift reporting, opt-in repair, metrics, and structured operational logs.

## Tasks

- [ ] 6.1 [Add reconciliation contracts](tasks/task-001-add-reconciliation-contracts.md)
- [ ] 6.2 [Implement mismatch reporting](tasks/task-002-implement-mismatch-reporting.md)
- [ ] 6.3 [Implement opt-in repair](tasks/task-003-implement-opt-in-repair.md)
- [x] 6.4 [Add Micrometer metrics](tasks/task-004-add-micrometer-metrics.md)
- [ ] 6.5 [Add structured operational logging](tasks/task-005-add-structured-operational-logging.md)

## Done When

Operators can detect reference-count drift and measure deduplication impact without enabling automatic repair by default.
