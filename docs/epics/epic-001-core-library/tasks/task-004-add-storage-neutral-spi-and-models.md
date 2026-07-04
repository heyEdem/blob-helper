# Task 1.4: Add Storage-Neutral SPI and Models

**Status:** Pending  
**Source:** [PLAN-001](../../../implementation-plans/PLAN-001-core-library.md)  
**ADR:** [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Define provider-neutral storage and service models used by every adapter.

## Files

- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/storage/BlobStorage.java`
- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/storage/PutBlobRequest.java`
- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/storage/StoredBlob.java`
- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/storage/BlobResource.java`
- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/model/StoreBlobCommand.java`
- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/model/BlobReference.java`
- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/exception/*.java`

## Steps

- [ ] Define `BlobStorage.put`, `get`, `delete`, and `exists`.
- [ ] Add immutable request/response records.
- [ ] Add domain exceptions for validation, hashing, storage, content not found, and reference count underflow.
- [ ] Run `./mvnw -pl blob-helper-core test`.

## Acceptance

- [ ] Core API exposes no provider SDK type.
- [ ] Models include object key, provider, bucket/container, size, content type, and metadata.
- [ ] Exceptions are domain-level and provider-neutral.
