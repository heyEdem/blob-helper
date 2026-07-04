# Task 5.3: Add Azure Storage Module

**Status:** Pending  
**Source:** [PLAN-005](../../../implementation-plans/PLAN-005-s3-azure-storage-adapters.md)  
**ADR:** [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Create the Azure Blob Storage provider module and configuration.

## Files

- Modify: `pom.xml`
- Create: `blob-helper-storage-azure/pom.xml`
- Create: `blob-helper-storage-azure/src/main/java/com/edem/blobhelper/storage/azure/AzureBlobStorageProperties.java`

## Steps

- [ ] Add module to the reactor.
- [ ] Add Azure Blob SDK dependency only in this module.
- [ ] Add properties for container, connection string, endpoint, and account name.
- [ ] Run `./mvnw -pl blob-helper-storage-azure test`.

## Acceptance

- [ ] Azure SDK dependency is isolated to `blob-helper-storage-azure`.
- [ ] Azure provider can be configured without changing core APIs.
