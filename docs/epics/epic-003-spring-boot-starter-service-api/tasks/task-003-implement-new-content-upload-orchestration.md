# Task 3.3: Implement New-Content Upload Orchestration

**Status:** Pending  
**Source:** [PLAN-003](../../../implementation-plans/PLAN-003-spring-boot-starter-service-api.md)  
**ADR:** [ADR-002](../../../adrs/ADR-002-deduplicated-upload-reference-counting.md)

## Goal

Store unseen bytes by hashing, uploading once, and creating metadata with `ref_count = 1`.

## Files

- Modify: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/service/DefaultBlobDeduplicationService.java`
- Create: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/service/BlobDeduplicationServiceTest.java`

## Steps

- [ ] Write `storesNewContent`.
- [ ] Hash the upload stream.
- [ ] Look up content identity.
- [ ] Generate object key and call `BlobStorage.put`.
- [ ] Persist `AssetContent` with `ref_count = 1`.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter -Dtest=BlobDeduplicationServiceTest test`.

## Acceptance

- [ ] New content writes storage once.
- [ ] New content creates one metadata row.
- [ ] Returned `BlobReference` has `duplicate = false`.
