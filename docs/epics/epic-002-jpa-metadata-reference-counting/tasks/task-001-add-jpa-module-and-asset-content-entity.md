# Task 2.1: Add JPA Module and AssetContent Entity

**Status:** Complete
**Source:** [PLAN-002](../../../implementation-plans/PLAN-002-jpa-metadata-and-reference-counting.md)  
**ADRs:** [ADR-001](../../../adrs/ADR-001-content-identity-and-core-boundaries.md), [ADR-002](../../../adrs/ADR-002-deduplicated-upload-reference-counting.md)

## Goal

Create `blob-helper-jpa` and map physical blob metadata to `blob_asset_content`.

## Files

- Modify: `pom.xml`
- Create: `blob-helper-jpa/pom.xml`
- Create: `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/AssetContent.java`
- Create: `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/AssetContentMappingTest.java`
- Create: `blob-helper-jpa/src/test/resources/META-INF/persistence.xml`

## Steps

- [x] Add `blob-helper-jpa` to the Maven reactor.
- [x] Add JPA dependencies and a test database dependency.
- [x] Map `AssetContent` with UUID id, content identity, object location, content metadata, `refCount`, timestamps, and version.
- [x] Run `./mvnw -pl blob-helper-jpa test`.

## Acceptance

- [x] Table name is `blob_asset_content`.
- [x] Unique constraint covers `hash_algorithm`, `content_hash`, and `size_bytes`.
- [x] Indexes cover hash, object key, and reference count.
