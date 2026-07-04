# Task 5.5: Add Provider SDK Boundary and Contract Tests

**Status:** Pending  
**Source:** [PLAN-005](../../../implementation-plans/PLAN-005-s3-azure-storage-adapters.md)  
**ADR:** [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Prove provider SDKs stay out of core and starter modules.

## Files

- Create: `src/test/java/com/edem/blobhelper/ProviderDependencyBoundaryTest.java`
- Create: `docs/provider-testing.md`

## Steps

- [ ] Add dependency boundary checks for AWS and Azure SDK artifacts.
- [ ] Verify AWS appears only in `blob-helper-storage-s3`.
- [ ] Verify Azure appears only in `blob-helper-storage-azure`.
- [ ] Document how to run external provider contract tests.
- [ ] Run `./mvnw test -Dtest=ProviderDependencyBoundaryTest`.

## Acceptance

- [ ] Core and starter stay provider-neutral.
- [ ] Provider tests are separated from the default unit-test path.
