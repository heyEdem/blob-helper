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
blob-helper-storage-local
```

`blob-helper-core` contains hashing, deduplication contracts, and storage-neutral
interfaces. Storage providers live in separate adapter modules.

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

This repository currently contains the Spring Boot project shell and project
documentation. Implementation will follow the specification in `docs/`.
