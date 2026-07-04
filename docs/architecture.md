# Architecture

## Project Type

Maven multi-module Java 21 library project for a Spring Boot-compatible blob deduplication helper.

Current implementation state: root Maven reactor plus `blob-helper-core` module. The original Spring Boot shell class still exists under root `src/`, but the root project is now `pom` packaging and only `blob-helper-core` is listed as a module.

## Directory Map

```text
.
├── blob-helper-core/
│   ├── pom.xml
│   └── src/
├── docs/
│   ├── adrs/
│   ├── epics/
│   ├── implementation-plans/
│   ├── SPECIFICATION.md
│   └── taskindex.md
├── src/
│   ├── main/
│   └── test/
├── pom.xml
└── README.md
```

## Module Overview

| Module/Package | Purpose |
|---|---|
| `blob-helper-core` | Provider-neutral core module. Currently contains package marker and smoke test. Planned to own hashing, storage SPI, key generation, command/result models, and domain exceptions. |
| root `pom.xml` | Maven reactor parent with Java 21, JUnit BOM, compiler plugin, and Surefire plugin management. |
| root `src/main/java/com/edem/blobhelper` | Legacy Spring Boot shell application class from project creation. Not currently part of a reactor child module. |
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
| Spring Boot | Present in legacy root shell source; planned starter integration, but not currently configured as a reactor module dependency. |
| AWS SDK | Planned only for `blob-helper-storage-s3`; not currently present. |
| Azure Blob SDK | Planned only for `blob-helper-storage-azure`; not currently present. |
