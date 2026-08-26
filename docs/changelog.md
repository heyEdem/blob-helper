# Changelog

## 2026-08-26 — Implement local put/get/delete/exists

- Added `LocalBlobStorage`, a filesystem `BlobStorage` adapter that streams uploads under the configured root directory, creates parent directories on demand, and overwrites existing objects.
- `get` returns an owner-managed `BlobResource` stream and throws core `ContentNotFoundException` for missing objects; `delete` is idempotent through `Files.deleteIfExists`; `exists` reflects filesystem state.
- Added JUnit temporary-directory integration coverage for the round trip, idempotent missing-object delete, overwrite behavior, existence checks, and blank-key rejection.
- Modules affected: `blob-helper-storage-local` and Epic 4 planning/status documentation.

## 2026-08-26 — Add local storage module

- Added the `blob-helper-storage-local` Maven module to the reactor with a dependency on `blob-helper-core` and no cloud SDKs.
- Added `LocalBlobStorageProperties` with a configurable root directory (`blob-helper-storage` default) and null-rejection on assignment.
- Added property coverage for defaults, custom roots, and null rejection; full `./mvnw verify` passes.
- Modules affected: root reactor, `blob-helper-storage-local`, and Epic 4 planning/status documentation.

## 2026-08-26 — Add provider auto-configuration validation

- Added `BlobHelperAutoConfiguration` registered through Spring Boot `AutoConfiguration.imports`, enabling `BlobHelperProperties` and a startup provider validator.
- Startup now fails clearly for unsupported provider names, missing provider beans, selected providers without a matching bean (`<provider>BlobStorage` naming convention), and ambiguous multi-provider contexts.
- Added `ApplicationContextRunner` coverage for provider selection and every failure mode; added test-scope `spring-boot-test` and AssertJ to the starter module.
- Epic 3 is complete (5/5). Modules affected: `blob-helper-spring-boot-starter` and planning/status documentation.

## 2026-08-25 — Add duplicate-content upload orchestration

- Added pre-write content identity lookup and lock-aware reference retention for duplicate uploads.
- Duplicate uploads now skip `BlobStorage.put`, reuse the existing physical object, and return `BlobReference.duplicate = true`.
- Added service coverage for one physical write, one metadata row, and `ref_count` increasing from one to two.
- Modules affected: `blob-helper-spring-boot-starter` and Epic 3 planning/status documentation.

## 2026-08-24 — Add new-content upload orchestration

- Added buffered upload hashing, deterministic object-key generation, provider-neutral storage writes, and JPA metadata creation through `DefaultBlobDeduplicationService`.
- Added Hibernate/H2 service coverage for one physical write, one metadata row with `ref_count = 1`, and a non-duplicate `BlobReference`.
- Modules affected: `blob-helper-spring-boot-starter` and Epic 3 planning/status documentation.

## 2026-08-22 — Add BlobDeduplicationService contract

- Added the provider-neutral `BlobDeduplicationService` API and default retain/release/get facade.
- Added missing-content contract coverage and starter dependencies on the core and JPA modules.
- Modules affected: `blob-helper-spring-boot-starter` and Epic 3 planning/status documentation.

## 2026-08-21 — Add Spring Boot starter properties

- Added the `blob-helper-spring-boot-starter` Maven module and `BlobHelperProperties` binding for storage, deduplication, and cleanup configuration.
- Added Spring Boot binding coverage for provider selection, upload-size parsing, content-type validation, physical deletion, and disabled-by-default reconciliation.
- Modules affected: root reactor, `blob-helper-spring-boot-starter`, and Epic 3 planning/status documentation.

## 2026-08-21 — Add concurrent duplicate upload integration test

- Added `ConcurrentUploadIntegrationTest` with separate JPA transactions coordinated to race on the same content identity.
- Verified concurrent duplicate uploads converge on one `AssetContent` row with one retained reference per worker.
- Modules affected: `blob-helper-jpa` tests and Epic 2 planning/status documentation.

## 2026-08-20 — Add duplicate-key retry behavior

- Added `AssetContentMutationService.createOrRetain` with explicit insert flushing, duplicate-key transaction retry, locked winner reload, and exactly-once reference incrementing.
- Added coordinated Hibernate/H2 coverage for new content, existing duplicates, and concurrent insert races without storage collaborators.
- Modules affected: `blob-helper-jpa`, Epic 2 planning/status documentation, and the living implementation indexes.

## 2026-08-20 — Add final-reference delete orchestration

- Added `BlobStorage` collaboration to `ReferenceCountService` and delete delegation only when a release reaches zero references.
- Added JPA integration coverage for non-final releases, final physical deletion, and exactly-once delete behavior.
- Modules affected: `blob-helper-jpa` and Epic 2 planning/status documentation.

## 2026-08-20 — Add reference retain and release service

- Added lock-aware `ReferenceCountService` retain and release operations with missing-content and underflow protection.
- Added JPA/Hibernate tests for exactly-once increment/decrement behavior and zero-count release rejection.
- Modules affected: `blob-helper-jpa`, `blob-helper-core` dependency boundary, and Epic 2 planning/status documentation.

## 2026-08-20 — Add AssetContent repository lookups and locks

- Added an EntityManager-backed repository for complete content identity lookup and pessimistic write-locked id lookup.
- Added Hibernate/H2 tests for lookup behavior, missing rows, lock mode, constructor validation, and duplicate identity rejection.
- Modules affected: `blob-helper-jpa` and Epic 2 planning/status documentation.

## 2026-08-18 — Add global PR writer workflow

- Added the reusable `pr-writer` skill for Claude and Codex based on the KADO-65 pull request style.
- Required both agents to use the skill for pull request titles and bodies and recorded baseline and forward validation evidence.
- Areas affected: repository agent instructions, workflow documentation, and global agent skill installations.

## 2026-08-18 — Add JPA metadata module and AssetContent entity

- Added the Jakarta Persistence module and mapped physical blob metadata with identity uniqueness, indexes, timestamps, and optimistic locking.
- Added Hibernate/H2 mapping tests and updated the Maven reactor, planning status, and living project index.
- Modules affected: root reactor, `blob-helper-jpa`, and `docs`.

## 2026-07-16 — Add core dependency boundary enforcement

- Added a classpath-scanning JUnit boundary test and Maven Enforcer rules for Spring, JPA, AWS SDK, and Azure SDK dependencies.
- Completed Epic 1 and updated `blob-helper-core` plus its planning/status documentation.

## 2026-07-15 — Add storage-neutral SPI and models

- Added provider-neutral storage contracts, immutable command/result models, resource stream lifecycle handling, and domain exceptions.
- Affected `blob-helper-core` and the Epic 1 planning/status documentation.

## 2026-07-06 — Add deterministic object key generation

- Added `ObjectKeyStrategy` and `HashObjectKeyStrategy` in `blob-helper-core`.
- Added tests for deterministic hash-derived key generation, lowercase algorithm segments, and empty-prefix relative keys.
- Updated Epic 1 task status and implementation docs for the new `core/key` package.

## 2026-07-04 — Add GitHub Actions CI

- Added `.github/workflows/ci.yml` for Java 21 Maven verification.
- Updated architecture and implementation indexes with the CI entry point.

## 2026-07-04 — Add PR message style guide

- Added `docs/pr-message-style.md` based on Edem's existing PR writeups.
- Updated `docs/README.md` to link the PR message style guide.

## 2026-07-04 — Initial index

- First codebase-indexer scan of Blob Helper.
- Generated `docs/architecture.md`, `docs/implementation.md`, `docs/patterns.md`, `docs/decisions.md`, and `docs/changelog.md`.
- Detected Java 21 Maven reactor with current `blob-helper-core` module and planning docs for JPA, Spring Boot starter, local storage, S3/Azure adapters, and reconciliation.
