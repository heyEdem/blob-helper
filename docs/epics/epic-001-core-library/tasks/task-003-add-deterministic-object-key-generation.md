# Task 1.3: Add Deterministic Object Key Generation

**Status:** Pending  
**Source:** [PLAN-001](../../../implementation-plans/PLAN-001-core-library.md)  
**ADR:** [ADR-001](../../../adrs/ADR-001-content-identity-and-core-boundaries.md)

## Goal

Generate stable storage keys from content identity instead of user filenames.

## Files

- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/key/ObjectKeyStrategy.java`
- Create: `blob-helper-core/src/main/java/com/edem/blobhelper/core/key/HashObjectKeyStrategy.java`
- Create: `blob-helper-core/src/test/java/com/edem/blobhelper/core/key/HashObjectKeyStrategyTest.java`

## Steps

- [ ] Write `HashObjectKeyStrategyTest.generatesDeterministicKey`.
- [ ] Implement key format `{prefix}/{algorithm}/{first_two_hash_chars}/{content_hash}`.
- [ ] Normalize algorithm names to lowercase path segments.
- [ ] Run `./mvnw -pl blob-helper-core -Dtest=HashObjectKeyStrategyTest test`.

## Acceptance

- [ ] The same content identity always generates the same key.
- [ ] User filenames are not part of the storage key.
- [ ] Empty prefix still generates a valid relative key.
