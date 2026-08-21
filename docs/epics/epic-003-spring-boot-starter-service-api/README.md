# Epic 3: Spring Boot Starter and Service API

**Status:** In Progress
**Progress:** 1/5
**Sources:** [ADR-002](../../adrs/ADR-002-deduplicated-upload-reference-counting.md), [ADR-004](../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md), [PLAN-003](../../implementation-plans/PLAN-003-spring-boot-starter-service-api.md)

## Goal

Expose a storage-neutral Spring Boot service API with auto-configuration and upload orchestration.

## Tasks

- [x] 3.1 [Add starter module and properties](tasks/task-001-add-starter-module-and-properties.md)
- [ ] 3.2 [Add BlobDeduplicationService contract](tasks/task-002-add-blob-deduplication-service-contract.md)
- [ ] 3.3 [Implement new-content upload orchestration](tasks/task-003-implement-new-content-upload-orchestration.md)
- [ ] 3.4 [Implement duplicate-content upload orchestration](tasks/task-004-implement-duplicate-content-upload-orchestration.md)
- [ ] 3.5 [Add provider auto-configuration validation](tasks/task-005-add-provider-auto-configuration-validation.md)

## Done When

Consuming apps can call `BlobDeduplicationService` without receiving controllers or provider-specific APIs.
