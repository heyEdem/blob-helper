# Task 3.5: Add Provider Auto-Configuration Validation

**Status:** Pending  
**Source:** [PLAN-003](../../../implementation-plans/PLAN-003-spring-boot-starter-service-api.md)  
**ADR:** [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Wire exactly one configured `BlobStorage` provider and fail clearly otherwise.

## Files

- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/autoconfigure/BlobHelperAutoConfiguration.java`
- Create: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/autoconfigure/BlobHelperAutoConfigurationTest.java`

## Steps

- [ ] Add conditional auto-configuration for a single `BlobStorage`.
- [ ] Fail startup for unsupported provider names.
- [ ] Fail startup for missing or ambiguous providers.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=BlobHelperAutoConfigurationTest test`.

## Acceptance

- [ ] `provider=local` selects local storage when present.
- [ ] Unsupported provider fails with a clear message.
- [ ] Starter contains no AWS or Azure SDK implementation.
