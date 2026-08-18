# Architecture

## Project Type

Maven multi-module Java 21 library project for a Spring Boot-compatible blob deduplication helper.

Current implementation state: root Maven reactor with `blob-helper-core` and `blob-helper-jpa` modules. The original Spring Boot shell class still exists under root `src/`, but the root project is now `pom` packaging and the shell source is not part of a reactor child module.

## Directory Map

```text
.
├── blob-helper-core/
│   ├── pom.xml
│   └── src/
├── blob-helper-jpa/
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
| `blob-helper-jpa` | Framework-independent relational metadata module. Owns the `AssetContent` JPA mapping, content-identity uniqueness, physical object metadata, reference counts, timestamps, and optimistic-lock state. |
| root `pom.xml` | Maven reactor parent with Java 21, JUnit BOM, compiler plugin, and Surefire plugin management. |
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
| JUnit Jupiter | Unit testing. |
| Maven Enforcer Plugin | Rejects Spring, JPA, AWS SDK, and Azure SDK dependencies from `blob-helper-core`, including transitive dependencies. |
| Jakarta Persistence 3.2 | Portable entity mapping API used by `blob-helper-jpa`. |
| Hibernate ORM 7.4 | Test-scope JPA provider used to verify entity mappings. |
| H2 2.4 | Test-scope in-memory database for JPA mapping tests. |
| Spring Boot | Present in legacy root shell source; planned starter integration, but not currently configured as a reactor module dependency. |
| AWS SDK | Planned only for `blob-helper-storage-s3`; not currently present. |
| Azure Blob SDK | Planned only for `blob-helper-storage-azure`; not currently present. |
