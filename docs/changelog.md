# Changelog

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
