# Implementation

## Entry Points

- `pom.xml`: parent Maven reactor. Declares `blob-helper-core` as the only current module.
- `blob-helper-core/pom.xml`: core module build file. Depends on JUnit Jupiter for tests.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/package-info.java`: package marker for provider-neutral core APIs.
- `blob-helper-core/src/test/java/com/edem/blobhelper/core/CoreModuleSmokeTest.java`: verifies the core test package is wired.
- `src/main/java/com/edem/blobhelper/BlobHelperApplication.java`: original Spring Boot application class. Current root packaging means this is not part of a normal Spring Boot app module.

## Per-Module Breakdown

### Root Reactor

- **Entry point:** `pom.xml`
- **Key configuration:** Java 21, JUnit 5.13.4 BOM, Maven Compiler Plugin 3.14.1, Maven Surefire Plugin 3.5.4.
- **Initialization:** Maven builds modules listed under `<modules>`.
- **Non-obvious logic:** Root no longer inherits `spring-boot-starter-parent`; it is a plain Maven parent POM.

### blob-helper-core

- **Entry point:** `blob-helper-core/pom.xml`
- **Key classes/functions:** `CoreModuleSmokeTest.coreModuleTestsRunInExpectedPackage()` confirms current package wiring.
- **Initialization:** Built as Maven child of root `blob-helper`.
- **Non-obvious logic:** Planned core APIs are not implemented yet. ADR-001 and PLAN-001 define the target package areas: `core/hash`, `core/key`, `core/storage`, `core/model`, and `core/exception`.

### Documentation and Planning

- **Entry point:** `docs/taskindex.md`
- **Key files:** `docs/SPECIFICATION.md`, `docs/adrs/*.md`, `docs/implementation-plans/*.md`, `docs/epics/**/tasks/*.md`.
- **Initialization:** Manual planning docs drive future implementation tasks.
- **Non-obvious logic:** `docs/taskindex.md` is the status board. It currently marks Epic 1 Task 1.1 complete and remaining tasks pending.

## Configuration

| Variable / Property | Default | Purpose |
|---|---|---|
| `spring.application.name` | `blob-helper` | Present in root `src/main/resources/application.yaml`. |
| `java.version` | `21` | Maven compiler release target. |
| `junit.version` | `5.13.4` | JUnit BOM version. |

Planned future configuration from the specification:

| Property | Purpose |
|---|---|
| `blob-helper.storage.provider` | Selects `s3`, `azure`, `local`, or custom provider. |
| `blob-helper.storage.key-prefix` | Prefix for generated object keys. |
| `blob-helper.deduplication.hash-algorithm` | Initial target is `SHA-256`. |
| `blob-helper.cleanup.reconciliation-enabled` | Disabled by default; controls reconciliation scheduling. |
