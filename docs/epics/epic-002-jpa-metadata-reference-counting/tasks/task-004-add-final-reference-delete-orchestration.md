# Task 2.4: Add Final-Reference Delete Orchestration

**Status:** Pending  
**Source:** [PLAN-002](../../../implementation-plans/PLAN-002-jpa-metadata-and-reference-counting.md)  
**ADR:** [ADR-003](../../../adrs/ADR-003-release-delete-and-reconciliation.md)

## Goal

Delete physical storage only when the final logical reference is released.

## Files

- Modify: `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/ReferenceCountService.java`
- Modify: `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/ReferenceCountServiceTest.java`

## Steps

- [ ] Accept a storage-delete callback or service collaborator at the release boundary.
- [ ] Keep physical storage when `ref_count` remains above zero.
- [ ] Delete or tombstone content when `ref_count` reaches zero according to configuration.
- [ ] Run `./mvnw -pl blob-helper-jpa -Dtest=ReferenceCountServiceTest test`.

## Acceptance

- [ ] `ref_count = 2` releases to `1` without storage delete.
- [ ] `ref_count = 1` triggers one physical delete.
- [ ] Missing storage objects are treated as already deleted by default.
