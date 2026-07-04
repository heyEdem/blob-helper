# Task 2.3: Add Reference Retain and Release Service

**Status:** Pending  
**Source:** [PLAN-002](../../../implementation-plans/PLAN-002-jpa-metadata-and-reference-counting.md)  
**ADR:** [ADR-003](../../../adrs/ADR-003-release-delete-and-reconciliation.md)

## Goal

Implement safe `ref_count` increment and decrement behavior.

## Files

- Create: `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/ReferenceCountService.java`
- Create: `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/ReferenceCountServiceTest.java`

## Steps

- [ ] Add `retain(UUID assetContentId)`.
- [ ] Add `release(UUID assetContentId)` with underflow protection.
- [ ] Add tests for increment, decrement, and underflow failure.
- [ ] Run `./mvnw -pl blob-helper-jpa -Dtest=ReferenceCountServiceTest test`.

## Acceptance

- [ ] Retain increments exactly once.
- [ ] Release decrements exactly once.
- [ ] `ref_count` never goes below zero.
