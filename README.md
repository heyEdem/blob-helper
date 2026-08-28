# Blob Helper

Blob Helper is a reusable Spring Boot library for deduplicated object uploads.
It stores identical file bytes once, while allowing each application to keep its
own logical asset records.

The first target use case is image upload deduplication like the design
described in the Medium article, but the project is storage-neutral. S3, Azure
Blob Storage, local filesystem storage, MinIO, Google Cloud Storage, or any
custom object store can be supported through the same storage adapter contract.

## Problem

Applications often upload the same file many times:

- users re-upload the same profile image
- multiple records attach the same document
- imports contain repeated images
- retries create duplicate object-store data

Without deduplication, each upload becomes a new object-store write and a new
stored object, even when the bytes are identical.

Blob Helper solves this by separating:

- logical assets owned by the consuming application
- physical blob content owned by Blob Helper

Many logical assets can point to one physical content record.

## Planned Modules

```text
blob-helper-core
blob-helper-jpa
blob-helper-spring-boot-starter
blob-helper-storage-s3
blob-helper-storage-azure
blob-helper-spring-boot-management  (planned)
blob-helper-dashboard                (planned)
blob-helper-storage-local
```

`blob-helper-core` contains hashing, deduplication contracts, and storage-neutral
interfaces. Storage providers live in separate adapter modules.

The planned optional management module exposes local read-only operational data
and self-registers instances with the planned standalone dashboard. The
dashboard polls multiple local instances and stores aggregate history in
SQLite; it does not manage blob bytes or provider credentials.

## High-Level Flow

```text
Upload file
  -> stream through SHA-256 hasher
  -> look up existing content by hash and size
  -> if found, increment reference count and skip storage upload
  -> if not found, upload through configured storage adapter
  -> return a normal logical asset response
```

Delete flow:

```text
Delete logical asset
  -> decrement content reference count
  -> delete the physical object only when no assets reference it
```

## Documentation

- [Project Specification](docs/SPECIFICATION.md)
- [Task Index](docs/taskindex.md)
- [Docs Index](docs/README.md)

## Status

Phases 1–5 are complete: the core library, JPA metadata and reference counting,
Spring Boot starter, local storage, S3, and Azure adapters are implemented and
verified. The local dashboard and multi-instance monitoring design is approved
for the next implementation epic; see [Epic 7](docs/epics/epic-007-local-dashboard-monitoring/README.md).
