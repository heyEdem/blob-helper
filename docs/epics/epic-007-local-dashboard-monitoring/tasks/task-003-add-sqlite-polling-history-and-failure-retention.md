# Task 7.3: Add SQLite Polling History and Failure Retention

**Status:** Pending
**Source:** [PLAN-007](../../../implementation-plans/PLAN-007-local-dashboard-monitoring.md)
**ADR:** [ADR-005](../../../adrs/ADR-005-local-dashboard-pull-monitoring.md)

## Goal

Poll registered instances, persist useful history in SQLite, and keep detailed
failure records bounded to seven days.

## Files

- Modify: `blob-helper-dashboard/pom.xml`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/persistence/DashboardDatabase.java`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/persistence/InstanceRepository.java`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/persistence/MetricSnapshotRepository.java`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/persistence/FailureEventRepository.java`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/polling/InstancePollingService.java`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/polling/MetricDeltaCalculator.java`
- Create: `blob-helper-dashboard/src/test/java/com/edem/blobhelper/dashboard/persistence/FailureEventRepositoryTest.java`
- Create: `blob-helper-dashboard/src/test/java/com/edem/blobhelper/dashboard/polling/MetricDeltaCalculatorTest.java`

## Acceptance

- [ ] SQLite is the default dashboard database and requires no external server.
- [ ] Registrations, metric snapshots, instance status, and failure details are
      persisted in separate tables.
- [ ] Polling supports multiple instances independently and records last-seen
      and failure status per instance.
- [ ] Cumulative instance counters are converted to interval deltas, including
      reset handling after an instance restart.
- [ ] Logical bytes, physical bytes, avoided bytes, duplicate rate, and
      operation counts are available for trend charts.
- [ ] Failure details older than seven days are deleted automatically; aggregate
      snapshots are not deleted by this cleanup.
