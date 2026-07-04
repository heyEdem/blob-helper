# Task 2.6: Add Concurrent Duplicate Upload Integration Test

**Status:** Pending  
**Source:** [PLAN-002](../../../implementation-plans/PLAN-002-jpa-metadata-and-reference-counting.md)  
**ADR:** [ADR-002](../../../adrs/ADR-002-deduplicated-upload-reference-counting.md)

## Goal

Prove parallel duplicate uploads converge on one content row.

## Files

- Create: `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/ConcurrentUploadIntegrationTest.java`

## Steps

- [ ] Start parallel workers using identical content identity.
- [ ] Execute create-or-retain concurrently.
- [ ] Assert one `AssetContent` row exists.
- [ ] Assert `ref_count` equals the worker count.
- [ ] Run `./mvnw -pl blob-helper-jpa -Dtest=ConcurrentUploadIntegrationTest test`.

## Acceptance

- [ ] Concurrent duplicate uploads create one row.
- [ ] Final reference count is correct.
- [ ] The test fails if the unique constraint or retry path is removed.
