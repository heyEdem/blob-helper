# Task 6.5: Add Structured Operational Logging

**Status:** Pending  
**Source:** [PLAN-006](../../../implementation-plans/PLAN-006-reconciliation-observability.md)  
**ADR:** [ADR-003](../../../adrs/ADR-003-release-delete-and-reconciliation.md)

## Goal

Log operational decisions without exposing full content hashes by default.

## Files

- Modify: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/service/DefaultBlobDeduplicationService.java`
- Create: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/observability/BlobHelperLoggingTest.java`

## Steps

- [ ] Log content id, provider, object key, duplicate/new decision, and hash prefix.
- [ ] Do not log full hash by default.
- [ ] Log failed physical deletes for later reconciliation.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=BlobHelperLoggingTest test`.

## Acceptance

- [ ] Logs include hash prefix only by default.
- [ ] Logs include enough context for failed delete reconciliation.
