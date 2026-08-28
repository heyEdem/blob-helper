# Task 7.5: Add Multi-Instance End-to-End Verification

**Status:** Pending
**Source:** [PLAN-007](../../../implementation-plans/PLAN-007-local-dashboard-monitoring.md)
**ADR:** [ADR-005](../../../adrs/ADR-005-local-dashboard-pull-monitoring.md)

## Goal

Prove that multiple local instances can register, be polled, contribute useful
history, and expose bounded failure retention without cloud services.

## Files

- Create: `blob-helper-dashboard/src/test/java/com/edem/blobhelper/dashboard/MultiInstanceDashboardIntegrationTest.java`
- Create: `blob-helper-spring-boot-management/src/test/java/com/edem/blobhelper/management/ManagementDashboardContractTest.java`
- Modify: `docs/provider-testing.md`
- Modify: `docs/README.md`

## Acceptance

- [ ] Two independent local management endpoints can register with one
      dashboard and appear with separate status and metrics.
- [ ] Polling records traffic contribution and deduplication savings for each
      instance and for the combined overview.
- [ ] A failed poll marks only the affected instance stale/disconnected.
- [ ] Failure details remain visible within seven days and are removed after
      retention cleanup.
- [ ] Full Maven verification runs without cloud credentials.
