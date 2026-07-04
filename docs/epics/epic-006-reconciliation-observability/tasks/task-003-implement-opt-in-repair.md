# Task 6.3: Implement Opt-In Repair

**Status:** Pending  
**Source:** [PLAN-006](../../../implementation-plans/PLAN-006-reconciliation-observability.md)  
**ADR:** [ADR-003](../../../adrs/ADR-003-release-delete-and-reconciliation.md)

## Goal

Repair reference count drift only when explicitly enabled.

## Files

- Modify: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/reconcile/ReconciliationService.java`
- Modify: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/reconcile/ReconciliationServiceTest.java`

## Steps

- [ ] Add repair-enabled configuration.
- [ ] Write `repairsOnlyWhenEnabled`.
- [ ] Keep disabled mode read-only.
- [ ] Update `ref_count` only through the JPA reference-count boundary.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=ReconciliationServiceTest test`.

## Acceptance

- [ ] Repair disabled is the default.
- [ ] Disabled repair performs no database mutation.
- [ ] Enabled repair is explicit and auditable.
