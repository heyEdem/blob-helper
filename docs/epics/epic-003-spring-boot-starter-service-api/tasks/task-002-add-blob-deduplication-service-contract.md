# Task 3.2: Add BlobDeduplicationService Contract

**Status:** Pending  
**Source:** [PLAN-003](../../../implementation-plans/PLAN-003-spring-boot-starter-service-api.md)  
**ADRs:** [ADR-002](../../../adrs/ADR-002-deduplicated-upload-reference-counting.md), [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Define the app-facing service API for store, retain, release, and get.

## Files

- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/service/BlobDeduplicationService.java`
- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/service/DefaultBlobDeduplicationService.java`
- Create: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/service/BlobDeduplicationServiceContractTest.java`

## Steps

- [ ] Define `store(StoreBlobCommand)`, `retain(UUID)`, `release(UUID)`, and `get(UUID)`.
- [ ] Return provider-neutral `BlobReference` and `BlobResource`.
- [ ] Add a contract test for API shape and missing-content behavior.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=BlobDeduplicationServiceContractTest test`.

## Acceptance

- [ ] API exposes no AWS or Azure types.
- [ ] Service does not create logical application assets.
- [ ] Service can be used by application-owned controllers.
