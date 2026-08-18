# Epic 2: JPA Metadata and Reference Counting

**Status:** In Progress
**Progress:** 1/6
**Sources:** [ADR-001](../../adrs/ADR-001-content-identity-and-core-boundaries.md), [ADR-002](../../adrs/ADR-002-deduplicated-upload-reference-counting.md), [ADR-003](../../adrs/ADR-003-release-delete-and-reconciliation.md), [PLAN-002](../../implementation-plans/PLAN-002-jpa-metadata-and-reference-counting.md)

## Goal

Persist physical content metadata and implement safe reference count mutation.

## Tasks

- [x] 2.1 [Add JPA module and AssetContent entity](tasks/task-001-add-jpa-module-and-asset-content-entity.md)
- [ ] 2.2 [Add repository identity lookups and locks](tasks/task-002-add-repository-identity-lookups-and-locks.md)
- [ ] 2.3 [Add reference retain and release service](tasks/task-003-add-reference-retain-and-release-service.md)
- [ ] 2.4 [Add final-reference delete orchestration](tasks/task-004-add-final-reference-delete-orchestration.md)
- [ ] 2.5 [Add duplicate-key retry behavior](tasks/task-005-add-duplicate-key-retry-behavior.md)
- [ ] 2.6 [Add concurrent duplicate upload integration test](tasks/task-006-add-concurrent-duplicate-upload-integration-test.md)

## Done When

One content row exists for each content identity, and `ref_count` changes safely under concurrent duplicate uploads and releases.
