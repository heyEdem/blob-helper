# Architecture

## Project Type

Maven multi-module Java 21 library project for a Spring Boot-compatible blob deduplication helper.

Current implementation state: root Maven reactor with `blob-helper-core`, `blob-helper-jpa`, `blob-helper-spring-boot-starter`, and `blob-helper-storage-local` modules. The original Spring Boot shell class still exists under root `src/`, but the root project is now `pom` packaging and the shell source is not part of a reactor child module.

## Directory Map

```text
.
├── blob-helper-core/
│   ├── pom.xml
│   └── src/
├── blob-helper-jpa/
│   ├── pom.xml
│   └── src/
├── blob-helper-spring-boot-starter/
│   ├── pom.xml
│   └── src/
├── blob-helper-storage-local/
│   ├── pom.xml
│   └── src/
├── blob-helper-storage-s3/
│   ├── pom.xml
│   └── src/
├── docs/
│   ├── adrs/
│   ├── epics/
│   ├── implementation-plans/
│   ├── SPECIFICATION.md
│   └── taskindex.md
├── .github/
│   └── workflows/
├── src/
│   ├── main/
│   └── test/
├── pom.xml
└── README.md
```

## Module Overview

| Module/Package | Purpose |
|---|---|
| `blob-helper-core` | Provider-neutral core module. Owns streaming content hashing, deterministic hash-derived object key generation, the storage SPI, command/result models, domain exceptions, and dependency-boundary enforcement. |
| `blob-helper-jpa` | Framework-independent relational metadata module. Owns the `AssetContent` JPA mapping, content-identity uniqueness, physical object metadata, timestamps, optimistic-lock state, transaction-scoped repository lookups/locks, create-or-retain duplicate-key retry, lock-aware reference mutation, and final-reference delete delegation; uses provider-neutral contracts and exceptions from `blob-helper-core`. |
| `blob-helper-spring-boot-starter` | Spring Boot integration module. Owns `blob-helper.*` configuration binding, the auto-configuration with provider validation that enforces exactly one configured `BlobStorage`, and the provider-neutral `BlobDeduplicationService` facade; it contains no REST controllers or provider SDK implementations. Its test suite uses the local adapter for end-to-end service coverage. |
| `blob-helper-storage-local` | Local filesystem storage adapter module. Owns local provider configuration (`LocalBlobStorageProperties` with configurable root directory) and the `LocalBlobStorage` adapter implementing put, get, idempotent delete, and exists with normalized key resolution that rejects path traversal outside the root; depends only on `blob-helper-core` with no cloud SDKs. |
| `blob-helper-storage-s3` | AWS S3 provider module. Owns the module-local AWS SDK v2 dependency management and S3 connection properties; S3 `BlobStorage` behavior is implemented in the next Epic 5 task. |
| root `pom.xml` | Maven reactor parent with Java 21, JUnit and Spring Boot BOMs, compiler plugin, and Surefire plugin management. |
| root `src/main/java/com/edem/blobhelper` | Legacy Spring Boot shell application class from project creation. Not currently part of a reactor child module. |
| `.github/workflows/ci.yml` | GitHub Actions CI workflow for Java 21 Maven verification. |
| `docs/adrs` | Architecture decisions for content identity, upload/ref-counting, release/reconciliation, and pluggable storage. |
| `docs/implementation-plans` | Phase-level implementation plans for core, JPA, starter, local storage, S3/Azure, and operations. |
| `docs/epics` | Task breakdown by implementation epic. |

## Data Flow

Planned deduplicated upload flow:

```text
Application upload
  -> BlobDeduplicationService
  -> streaming SHA-256 hash
  -> AssetContent lookup by hash_algorithm + content_hash + size_bytes
  -> duplicate: increment ref_count, skip physical upload
  -> new: generate hash-derived object key, write through BlobStorage, create AssetContent
  -> application creates its own logical asset pointing to AssetContent
```

Planned delete flow:

```text
Application deletes logical asset
  -> Blob Helper release(assetContentId)
  -> lock AssetContent
  -> decrement ref_count
  -> if final reference: delete physical object through BlobStorage
```

## External Dependencies

| Name | Purpose |
|---|---|
| Java 21 | Project language/runtime target. |
| Maven | Build and module orchestration. |
| `blob-helper-core` | Reactor dependency that supplies provider-neutral content-not-found and reference-count-underflow exceptions to `blob-helper-jpa`. |
| `blob-helper-jpa` | Reactor dependency used by the starter service facade for metadata lookups and reference-count mutation. |
| JUnit Jupiter | Unit testing. |
| Maven Enforcer Plugin | Rejects Spring, JPA, AWS SDK, and Azure SDK dependencies from `blob-helper-core`, including transitive dependencies. |
| Jakarta Persistence 3.2 | Portable entity mapping API used by `blob-helper-jpa`. |
| Hibernate ORM 7.4 | Test-scope JPA provider used to verify entity mappings. |
| H2 2.4 | Test-scope in-memory database for JPA mapping tests. |
| Spring Boot 3.5.10 | `blob-helper-spring-boot-starter` auto-configuration, properties binding, and configuration metadata generation. |
| spring-boot-test / AssertJ | Test-scope only in the starter module: `ApplicationContextRunner` context tests and fluent failure assertions. |
| `blob-helper-storage-local` | Test-scope starter dependency used by the end-to-end local storage service integration test; it is not a starter runtime dependency. |
| AWS SDK for Java 2.x 2.54.4 | Isolated to `blob-helper-storage-s3` through its module-local BOM and `software.amazon.awssdk:s3` dependency; not present in core or starter. |
| Azure Blob SDK | Planned only for `blob-helper-storage-azure`; not currently present. |
