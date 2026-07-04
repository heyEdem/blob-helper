# Task 6.2: Implement Mismatch Reporting

**Status:** Pending  
**Source:** [PLAN-006](../../../implementation-plans/PLAN-006-reconciliation-observability.md)  
**ADR:** [ADR-003](../../../adrs/ADR-003-release-delete-and-reconciliation.md)

## Goal

Compare stored `ref_count` values with application-provided logical counts.

## Files

- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/reconcile/ReconciliationService.java`
- Create: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/reconcile/ReconciliationServiceTest.java`

## Steps

- [ ] Write `reportsReferenceCountMismatch`.
- [ ] Load physical content metadata.
- [ ] Ask the application count source for expected counts.
- [ ] Return mismatch reports without mutating the database.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=ReconciliationServiceTest test`.

## Acceptance

- [ ] Reporting works with repair disabled.
- [ ] Mismatches include asset content id, expected count, and actual count.
