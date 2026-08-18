# Task 1.5: Add Core Dependency Boundary Tests

**Status:** Complete  
**Source:** [PLAN-001](../../../implementation-plans/PLAN-001-core-library.md)  
**ADRs:** [ADR-001](../../../adrs/ADR-001-content-identity-and-core-boundaries.md), [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Protect `blob-helper-core` from Spring, JPA, AWS, and Azure dependencies.

## Files

- Modify: `blob-helper-core/pom.xml`
- Create: `blob-helper-core/src/test/java/com/edem/blobhelper/core/CoreModuleBoundaryTest.java`

## Steps

- [x] Add a dependency-boundary test using Maven dependency output or classpath inspection.
- [x] Assert no `org.springframework`, `jakarta.persistence`, `software.amazon.awssdk`, or `com.azure` artifacts are present.
- [x] Run `./mvnw -pl blob-helper-core test`.

## Acceptance

- [x] Boundary test fails if forbidden dependencies enter core.
- [x] Core remains framework-neutral.
