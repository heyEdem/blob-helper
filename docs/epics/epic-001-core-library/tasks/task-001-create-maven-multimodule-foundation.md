# Task 1.1: Create Maven Multi-Module Foundation

**Status:** Done  
**Source:** [PLAN-001](../../../implementation-plans/PLAN-001-core-library.md)  
**ADRs:** [ADR-001](../../../adrs/ADR-001-content-identity-and-core-boundaries.md), [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Convert the Spring Boot shell into a Maven reactor with a first `blob-helper-core` module.

## Files

- Modify: `pom.xml`
- Create: `blob-helper-core/pom.xml`
- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/package-info.java`
- Create: `blob-helper-core/src/test/java/com/edem/blobhelper/core/CoreModuleSmokeTest.java`

## Steps

- [x] Add `<packaging>pom</packaging>` and `<modules>` to the root `pom.xml`.
- [x] Create `blob-helper-core` with Java 21 and JUnit test support.
- [x] Add a smoke test proving the module is visible in the reactor.
- [x] Run `./mvnw -pl blob-helper-core test`.

## Acceptance

- [x] Reactor builds the core module.
- [x] Core module has no Spring Boot application class.
- [x] Core module is ready for framework-neutral APIs.
