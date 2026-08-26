# Epic 5: S3 and Azure Storage Adapters

**Status:** In Progress
**Progress:** 2/5
**Sources:** [ADR-004](../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md), [PLAN-005](../../implementation-plans/PLAN-005-s3-azure-storage-adapters.md)

## Goal

Add S3 and Azure Blob Storage adapters without leaking provider SDKs into core or starter code.

## Tasks

- [x] 5.1 [Add S3 storage module](tasks/task-001-add-s3-storage-module.md)
- [x] 5.2 [Implement S3 BlobStorage adapter](tasks/task-002-implement-s3-blob-storage-adapter.md)
- [ ] 5.3 [Add Azure storage module](tasks/task-003-add-azure-storage-module.md)
- [ ] 5.4 [Implement Azure BlobStorage adapter](tasks/task-004-implement-azure-blob-storage-adapter.md)
- [ ] 5.5 [Add provider SDK boundary and contract tests](tasks/task-005-add-provider-sdk-boundary-and-contract-tests.md)

## Done When

S3 and Azure implement the same `BlobStorage` behavior and provider SDK dependencies remain isolated.
