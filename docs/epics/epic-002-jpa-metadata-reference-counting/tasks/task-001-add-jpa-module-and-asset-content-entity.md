# Task 2.1: Add JPA Module and AssetContent Entity

**Status:** Pending  
**Source:** [PLAN-002](../../../implementation-plans/PLAN-002-jpa-metadata-and-reference-counting.md)  
**ADRs:** [ADR-001](../../../adrs/ADR-001-content-identity-and-core-boundaries.md), [ADR-002](../../../adrs/ADR-002-deduplicated-upload-reference-counting.md)

## Goal

Create `blob-helper-jpa` and map physical blob metadata to `blob_asset_content`.

## Files

- Modify: `pom.xml`
- Create: `blob-helper-jpa/pom.xml`
- Create: `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/AssetContent.java`
- Create: `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/AssetContentMappingTest.java`

## Steps

- [ ] Add `blob-helper-jpa` to the Maven reactor.
- [ ] Add JPA dependencies and a test database dependency.
- [ ] Map `AssetContent` with UUID id, content identity, object location, content metadata, `refCount`, timestamps, and version.
- [ ] Run `./mvnw -pl blob-helper-jpa test`.

## Acceptance

- [ ] Table name is `blob_asset_content`.
- [ ] Unique constraint covers `hash_algorithm`, `content_hash`, and `size_bytes`.
- [ ] Indexes cover hash, object key, and reference count.
