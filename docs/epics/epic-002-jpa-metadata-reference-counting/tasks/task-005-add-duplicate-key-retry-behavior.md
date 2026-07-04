# Task 2.5: Add Duplicate-Key Retry Behavior

**Status:** Pending  
**Source:** [PLAN-002](../../../implementation-plans/PLAN-002-jpa-metadata-and-reference-counting.md)  
**ADR:** [ADR-002](../../../adrs/ADR-002-deduplicated-upload-reference-counting.md)

## Goal

Handle concurrent new-content races by reloading the existing row and incrementing it.

## Files

- Create: `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/AssetContentMutationService.java`
- Create: `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/AssetContentMutationServiceTest.java`

## Steps

- [ ] Add create-or-retain method keyed by content identity.
- [ ] Catch duplicate-key failures from concurrent inserts.
- [ ] Reload the existing content row and increment `ref_count`.
- [ ] Run `./mvnw -pl blob-helper-jpa -Dtest=AssetContentMutationServiceTest test`.

## Acceptance

- [ ] Duplicate insert race returns the existing content row.
- [ ] Duplicate race increments `ref_count` exactly once.
- [ ] Storage adapters are not involved in database mutation.
