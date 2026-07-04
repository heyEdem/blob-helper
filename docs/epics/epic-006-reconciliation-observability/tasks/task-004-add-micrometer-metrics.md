# Task 6.4: Add Micrometer Metrics

**Status:** Pending  
**Source:** [PLAN-006](../../../implementation-plans/PLAN-006-reconciliation-observability.md)  
**ADR:** [ADR-003](../../../adrs/ADR-003-release-delete-and-reconciliation.md)

## Goal

Expose metrics for upload volume, deduplication savings, storage latency, delete failures, and repairs.

## Files

- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/observability/BlobHelperMetrics.java`
- Create: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/observability/BlobHelperMetricsTest.java`

## Steps

- [ ] Add counters for uploads, duplicates, skipped physical writes, accepted bytes, and avoided bytes.
- [ ] Add timers for hashing and storage writes.
- [ ] Add counters for storage delete failures and repairs.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=BlobHelperMetricsTest test`.

## Acceptance

- [ ] Duplicate upload increments duplicate and skipped-upload metrics.
- [ ] Metrics are optional through normal Spring Boot/Micrometer behavior.
