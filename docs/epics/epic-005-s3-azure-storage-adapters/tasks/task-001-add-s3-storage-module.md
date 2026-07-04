# Task 5.1: Add S3 Storage Module

**Status:** Pending  
**Source:** [PLAN-005](../../../implementation-plans/PLAN-005-s3-azure-storage-adapters.md)  
**ADR:** [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Create the S3 provider module and configuration.

## Files

- Modify: `pom.xml`
- Create: `blob-helper-storage-s3/pom.xml`
- Create: `blob-helper-storage-s3/src/main/java/com/edem/blobhelper/storage/s3/S3BlobStorageProperties.java`

## Steps

- [ ] Add module to the reactor.
- [ ] Add AWS SDK dependency only in this module.
- [ ] Add properties for bucket, region, endpoint override, and path-style access.
- [ ] Run `./mvnw -pl blob-helper-storage-s3 test`.

## Acceptance

- [ ] AWS SDK dependency is isolated to `blob-helper-storage-s3`.
- [ ] S3-compatible storage can be configured.
