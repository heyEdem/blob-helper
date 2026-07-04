# Task 3.4: Implement Duplicate-Content Upload Orchestration

**Status:** Pending  
**Source:** [PLAN-003](../../../implementation-plans/PLAN-003-spring-boot-starter-service-api.md)  
**ADR:** [ADR-002](../../../adrs/ADR-002-deduplicated-upload-reference-counting.md)

## Goal

Reuse existing physical content for byte-identical uploads.

## Files

- Modify: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/service/DefaultBlobDeduplicationService.java`
- Modify: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/service/BlobDeduplicationServiceTest.java`

## Steps

- [ ] Write `reusesDuplicateContent`.
- [ ] Detect existing content by hash algorithm, hash, and size.
- [ ] Increment `ref_count`.
- [ ] Skip `BlobStorage.put`.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=BlobDeduplicationServiceTest test`.

## Acceptance

- [ ] Duplicate content does not write storage.
- [ ] Duplicate content increments `ref_count` once.
- [ ] Returned `BlobReference` has `duplicate = true`.
