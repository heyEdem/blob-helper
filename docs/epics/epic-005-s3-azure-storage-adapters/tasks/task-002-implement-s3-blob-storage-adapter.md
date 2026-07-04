# Task 5.2: Implement S3 BlobStorage Adapter

**Status:** Pending  
**Source:** [PLAN-005](../../../implementation-plans/PLAN-005-s3-azure-storage-adapters.md)  
**ADR:** [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Implement `BlobStorage` with AWS S3 or S3-compatible object storage.

## Files

- Create: `blob-helper-storage-s3/src/main/java/com/edem/blobhelper/storage/s3/S3BlobStorage.java`
- Create: `blob-helper-storage-s3/src/test/java/com/edem/blobhelper/storage/s3/S3BlobStorageContractTest.java`

## Steps

- [ ] Implement `put`.
- [ ] Implement `get`.
- [ ] Implement idempotent `delete`.
- [ ] Implement `exists`.
- [ ] Run provider tests with the configured S3-compatible test target.

## Acceptance

- [ ] S3 round trip satisfies the `BlobStorage` contract.
- [ ] Provider exceptions are mapped to Blob Helper domain exceptions.
- [ ] Normal unit tests do not require real AWS credentials.
