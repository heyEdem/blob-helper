# Task 6.4: Add Micrometer Metrics

**Status:** Complete
**Source:** [PLAN-006](../../../implementation-plans/PLAN-006-reconciliation-observability.md)  
**ADR:** [ADR-003](../../../adrs/ADR-003-release-delete-and-reconciliation.md)

## Goal

Expose metrics for upload volume, deduplication savings, storage latency, delete failures, and repairs.

## Files

- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/observability/BlobHelperMetrics.java`
- Create: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/observability/BlobHelperMetricsTest.java`

## Steps

- [x] Add counters for uploads, duplicates, skipped physical writes, accepted bytes, and avoided bytes.
- [x] Add timers for hashing and storage writes.
- [x] Add counters for storage delete failures and repairs.
- [x] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=BlobHelperMetricsTest test`.

## Acceptance

- [x] Duplicate upload increments duplicate and skipped-upload metrics.
- [x] Metrics are optional through normal Spring Boot/Micrometer behavior.
