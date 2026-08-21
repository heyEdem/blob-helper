# Blob Helper Task Index

**Last Updated:** 2026-08-21
**Completed:** 12/30 (40%)
**Status:** In Progress

Checkbox states: `[x]` done · `[~]` in progress · `[ ]` pending. Task IDs are `<epic>.<seq>`.

## Progress Summary

| Epic | Title | Progress | Status |
|---|---|---:|---|
| 1 | Core library | 5/5 | Complete |
| 2 | JPA metadata and reference counting | 6/6 | Complete |
| 3 | Spring Boot starter and service API | 1/5 | In Progress |
| 4 | Local storage and integration tests | 0/4 | Not Started |
| 5 | S3 and Azure storage adapters | 0/5 | Not Started |
| 6 | Reconciliation and observability | 0/5 | Not Started |
| **Total** | | **12/30** | **In Progress** |

## Source Map

| Epic | ADRs | Implementation Plan | Epic Folder |
|---|---|---|---|
| 1 | [ADR-001](adrs/ADR-001-content-identity-and-core-boundaries.md), [ADR-004](adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md) | [PLAN-001](implementation-plans/PLAN-001-core-library.md) | [epic-001-core-library](epics/epic-001-core-library/README.md) |
| 2 | [ADR-001](adrs/ADR-001-content-identity-and-core-boundaries.md), [ADR-002](adrs/ADR-002-deduplicated-upload-reference-counting.md), [ADR-003](adrs/ADR-003-release-delete-and-reconciliation.md) | [PLAN-002](implementation-plans/PLAN-002-jpa-metadata-and-reference-counting.md) | [epic-002-jpa-metadata-reference-counting](epics/epic-002-jpa-metadata-reference-counting/README.md) |
| 3 | [ADR-002](adrs/ADR-002-deduplicated-upload-reference-counting.md), [ADR-004](adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md) | [PLAN-003](implementation-plans/PLAN-003-spring-boot-starter-service-api.md) | [epic-003-spring-boot-starter-service-api](epics/epic-003-spring-boot-starter-service-api/README.md) |
| 4 | [ADR-004](adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md) | [PLAN-004](implementation-plans/PLAN-004-local-storage-adapter-and-integration-tests.md) | [epic-004-local-storage-integration-tests](epics/epic-004-local-storage-integration-tests/README.md) |
| 5 | [ADR-004](adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md) | [PLAN-005](implementation-plans/PLAN-005-s3-azure-storage-adapters.md) | [epic-005-s3-azure-storage-adapters](epics/epic-005-s3-azure-storage-adapters/README.md) |
| 6 | [ADR-003](adrs/ADR-003-release-delete-and-reconciliation.md) | [PLAN-006](implementation-plans/PLAN-006-reconciliation-observability.md) | [epic-006-reconciliation-observability](epics/epic-006-reconciliation-observability/README.md) |

## Verification Commands

| Purpose | Command |
|---|---|
| Full test suite | `./mvnw test` |
| Core module tests | `./mvnw -pl blob-helper-core test` |
| JPA module tests | `./mvnw -pl blob-helper-jpa test` |
| Starter tests | `./mvnw -pl blob-helper-spring-boot-starter test` |
| Local storage tests | `./mvnw -pl blob-helper-storage-local test` |
| Dependency boundary checks | `./mvnw test -Dtest='*BoundaryTest'` |
| Inspect tracked planning docs | `git status --short docs` |

## Epic 1 — Core Library

- [x] 1.1 [Create Maven multi-module foundation](epics/epic-001-core-library/tasks/task-001-create-maven-multimodule-foundation.md)
- [x] 1.2 [Add streaming content hashing](epics/epic-001-core-library/tasks/task-002-add-streaming-content-hashing.md)
- [x] 1.3 [Add deterministic object key generation](epics/epic-001-core-library/tasks/task-003-add-deterministic-object-key-generation.md)
- [x] 1.4 [Add storage-neutral SPI and models](epics/epic-001-core-library/tasks/task-004-add-storage-neutral-spi-and-models.md)
- [x] 1.5 [Add core dependency boundary tests](epics/epic-001-core-library/tasks/task-005-add-core-dependency-boundary-tests.md)

## Epic 2 — JPA Metadata and Reference Counting

- [x] 2.1 [Add JPA module and AssetContent entity](epics/epic-002-jpa-metadata-reference-counting/tasks/task-001-add-jpa-module-and-asset-content-entity.md)
- [x] 2.2 [Add repository identity lookups and locks](epics/epic-002-jpa-metadata-reference-counting/tasks/task-002-add-repository-identity-lookups-and-locks.md)
- [x] 2.3 [Add reference retain and release service](epics/epic-002-jpa-metadata-reference-counting/tasks/task-003-add-reference-retain-and-release-service.md)
- [x] 2.4 [Add final-reference delete orchestration](epics/epic-002-jpa-metadata-reference-counting/tasks/task-004-add-final-reference-delete-orchestration.md)
- [x] 2.5 [Add duplicate-key retry behavior](epics/epic-002-jpa-metadata-reference-counting/tasks/task-005-add-duplicate-key-retry-behavior.md)
- [x] 2.6 [Add concurrent duplicate upload integration test](epics/epic-002-jpa-metadata-reference-counting/tasks/task-006-add-concurrent-duplicate-upload-integration-test.md)

## Epic 3 — Spring Boot Starter and Service API

- [x] 3.1 [Add starter module and properties](epics/epic-003-spring-boot-starter-service-api/tasks/task-001-add-starter-module-and-properties.md)
- [ ] 3.2 [Add BlobDeduplicationService contract](epics/epic-003-spring-boot-starter-service-api/tasks/task-002-add-blob-deduplication-service-contract.md)
- [ ] 3.3 [Implement new-content upload orchestration](epics/epic-003-spring-boot-starter-service-api/tasks/task-003-implement-new-content-upload-orchestration.md)
- [ ] 3.4 [Implement duplicate-content upload orchestration](epics/epic-003-spring-boot-starter-service-api/tasks/task-004-implement-duplicate-content-upload-orchestration.md)
- [ ] 3.5 [Add provider auto-configuration validation](epics/epic-003-spring-boot-starter-service-api/tasks/task-005-add-provider-auto-configuration-validation.md)

## Epic 4 — Local Storage and Integration Tests

- [ ] 4.1 [Add local storage module](epics/epic-004-local-storage-integration-tests/tasks/task-001-add-local-storage-module.md)
- [ ] 4.2 [Implement local put get delete exists](epics/epic-004-local-storage-integration-tests/tasks/task-002-implement-local-put-get-delete-exists.md)
- [ ] 4.3 [Add path traversal protection](epics/epic-004-local-storage-integration-tests/tasks/task-003-add-path-traversal-protection.md)
- [ ] 4.4 [Add local storage service integration tests](epics/epic-004-local-storage-integration-tests/tasks/task-004-add-local-storage-service-integration-tests.md)

## Epic 5 — S3 and Azure Storage Adapters

- [ ] 5.1 [Add S3 storage module](epics/epic-005-s3-azure-storage-adapters/tasks/task-001-add-s3-storage-module.md)
- [ ] 5.2 [Implement S3 BlobStorage adapter](epics/epic-005-s3-azure-storage-adapters/tasks/task-002-implement-s3-blob-storage-adapter.md)
- [ ] 5.3 [Add Azure storage module](epics/epic-005-s3-azure-storage-adapters/tasks/task-003-add-azure-storage-module.md)
- [ ] 5.4 [Implement Azure BlobStorage adapter](epics/epic-005-s3-azure-storage-adapters/tasks/task-004-implement-azure-blob-storage-adapter.md)
- [ ] 5.5 [Add provider SDK boundary and contract tests](epics/epic-005-s3-azure-storage-adapters/tasks/task-005-add-provider-sdk-boundary-and-contract-tests.md)

## Epic 6 — Reconciliation and Observability

- [ ] 6.1 [Add reconciliation contracts](epics/epic-006-reconciliation-observability/tasks/task-001-add-reconciliation-contracts.md)
- [ ] 6.2 [Implement mismatch reporting](epics/epic-006-reconciliation-observability/tasks/task-002-implement-mismatch-reporting.md)
- [ ] 6.3 [Implement opt-in repair](epics/epic-006-reconciliation-observability/tasks/task-003-implement-opt-in-repair.md)
- [ ] 6.4 [Add Micrometer metrics](epics/epic-006-reconciliation-observability/tasks/task-004-add-micrometer-metrics.md)
- [ ] 6.5 [Add structured operational logging](epics/epic-006-reconciliation-observability/tasks/task-005-add-structured-operational-logging.md)

## Notes

| Date | Note |
|---|---|
| 2026-08-21 | Completed task 3.1 with the Spring Boot starter module, `blob-helper.*` properties binding, upload-size parsing, and disabled-by-default reconciliation. |
| 2026-08-21 | Completed task 2.6 with a coordinated two-transaction duplicate upload integration test that verifies one identity row and one retained reference per worker. |
| 2026-08-20 | Completed task 2.5 with create-or-retain duplicate-key retry, locked winner reload, exact reference increment, and coordinated JPA race coverage. |
| 2026-08-20 | Completed task 2.4 with final-reference physical delete delegation, non-final release protection, and idempotent storage-delete coverage. |
| 2026-08-20 | Completed task 2.3 with lock-aware retain/release operations, core exception handling, and underflow tests. |
| 2026-08-20 | Completed task 2.2 with transaction-scoped identity lookup and pessimistic lock repository operations plus duplicate identity tests. |
| 2026-08-18 | Completed task 2.1 with the JPA module, `AssetContent` mapping, and Hibernate/H2 mapping tests. |
| 2026-07-16 | Completed task 1.5 with classpath scanning and Maven Enforcer dependency boundary checks; Epic 1 is complete. |
| 2026-07-15 | Completed task 1.4 with the provider-neutral storage SPI, models, and domain exception hierarchy. |
| 2026-07-04 | Task index generated from `docs/adrs/ADR-001..ADR-004.md` and `docs/implementation-plans/PLAN-001..PLAN-006.md`. |
