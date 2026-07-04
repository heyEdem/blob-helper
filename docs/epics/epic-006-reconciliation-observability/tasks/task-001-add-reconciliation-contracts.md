# Task 6.1: Add Reconciliation Contracts

**Status:** Pending  
**Source:** [PLAN-006](../../../implementation-plans/PLAN-006-reconciliation-observability.md)  
**ADR:** [ADR-003](../../../adrs/ADR-003-release-delete-and-reconciliation.md)

## Goal

Define how consuming applications report logical reference counts to Blob Helper.

## Files

- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/reconcile/LogicalReferenceCountSource.java`
- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/reconcile/ReconciliationReport.java`
- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/reconcile/ReconciliationMismatch.java`

## Steps

- [ ] Add callback/query adapter interface for app-owned logical assets.
- [ ] Add report and mismatch records.
- [ ] Keep repair commands separate from report generation.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter test`.

## Acceptance

- [ ] No application logical schema is assumed.
- [ ] Reconciliation can report expected and actual counts.
