# Epic 4: Local Storage and Integration Tests

**Status:** In Progress  
**Progress:** 2/4  
**Sources:** [ADR-004](../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md), [PLAN-004](../../implementation-plans/PLAN-004-local-storage-adapter-and-integration-tests.md)

## Goal

Provide deterministic filesystem storage for development and tests.

## Tasks

- [x] 4.1 [Add local storage module](tasks/task-001-add-local-storage-module.md)
- [x] 4.2 [Implement local put get delete exists](tasks/task-002-implement-local-put-get-delete-exists.md)
- [ ] 4.3 [Add path traversal protection](tasks/task-003-add-path-traversal-protection.md)
- [ ] 4.4 [Add local storage service integration tests](tasks/task-004-add-local-storage-service-integration-tests.md)

## Done When

The full deduplicated upload path can run locally without cloud credentials.
