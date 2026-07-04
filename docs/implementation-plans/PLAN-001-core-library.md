# PLAN-001: Core Library

**Date:** 2026-07-04  
**Status:** Todo  
**Implements:** ADR-001, ADR-004  
**Estimated complexity:** Medium

## Goal

Create the provider-neutral core module that defines content hashing, object key generation, storage contracts, and command/result models.

## Background

The repo currently has a Spring Boot shell and specification. The core module is the first implementation boundary because all later modules depend on its contracts.

## What to Build

- Convert the project to a Maven multi-module structure with `blob-helper-core`.
- Add `ContentHasher`, `ContentHash`, and `Sha256ContentHasher`.
- Add `ObjectKeyStrategy` and `HashObjectKeyStrategy`.
- Add `BlobStorage`, `PutBlobRequest`, `StoredBlob`, and `BlobResource`.
- Add `StoreBlobCommand` and `BlobReference` as storage-neutral models.
- Add domain exceptions for validation, hashing, storage, content-not-found, and reference-count underflow.

## Where the Logic Lives (from Q3)

| Logic | Location |
|-------|----------|
| Hashing contracts and SHA-256 implementation | `blob-helper-core/src/main/java/.../core/hash` |
| Object key generation | `blob-helper-core/src/main/java/.../core/key` |
| Storage SPI and models | `blob-helper-core/src/main/java/.../core/storage` |
| Domain command/result models | `blob-helper-core/src/main/java/.../core/model` |

## Acceptance Criteria (from Q4)

- [ ] **Sha256ContentHasherTest.hashesExactBytes:** Given known bytes, when hashed through the streaming hasher, then the lowercase SHA-256 hex digest matches the known value.
- [ ] **HashObjectKeyStrategyTest.generatesDeterministicKey:** Given a prefix, algorithm, and hash, when generating a key, then the key is `{prefix}/{algorithm}/{first_two_hash_chars}/{content_hash}`.
- [ ] **CoreModuleBoundaryTest.coreHasNoSpringJpaOrProviderDependencies:** Given `blob-helper-core`, then dependency analysis finds no Spring, JPA, AWS, or Azure dependencies.

## Out of Scope (from Q5)

- `blob-helper-core` Spring/JPA/provider dependencies — must not be added.
- Application-owned logical asset tables — not part of this library.
- Public URL generation — not supported by default.

## Implementation Notes

- Use Java 21.
- Keep core APIs stream-based.
- Treat filenames as metadata only.

## Definition of Done

- [ ] All acceptance criteria tests pass
- [ ] No out-of-scope files were modified
- [ ] ADR invariants are enforced in code
- [ ] PR reviewed and merged
