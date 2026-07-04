# Task 4.4: Add Local Storage Service Integration Tests

**Status:** Pending  
**Source:** [PLAN-004](../../../implementation-plans/PLAN-004-local-storage-adapter-and-integration-tests.md)  
**ADRs:** [ADR-002](../../../adrs/ADR-002-deduplicated-upload-reference-counting.md), [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Test the complete service path with local storage.

## Files

- Create: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/service/LocalStorageDeduplicationIntegrationTest.java`

## Steps

- [ ] Configure local provider with a temporary directory.
- [ ] Store new content and verify file exists.
- [ ] Store duplicate content and verify no second file is created.
- [ ] Release both references and verify final delete.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=LocalStorageDeduplicationIntegrationTest test`.

## Acceptance

- [ ] End-to-end local flow passes without cloud credentials.
- [ ] Duplicate upload skips physical write.
