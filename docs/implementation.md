# Implementation

## Entry Points

- `pom.xml`: parent Maven reactor. Declares `blob-helper-core` and `blob-helper-jpa` as current modules.
- `blob-helper-core/pom.xml`: core module build file. Depends on JUnit Jupiter for tests.
- `blob-helper-jpa/pom.xml`: persistence module build file. Depends on `blob-helper-core`, exposes Jakarta Persistence, and uses Hibernate/H2 in test scope.
- `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/AssetContent.java`: JPA entity for unique physical content identity, object location, metadata, reference count, timestamps, and optimistic locking.
- `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/AssetContentRepository.java`: EntityManager-backed repository for identity lookup and pessimistic write-locked lookup by content id; transaction lifecycle remains with the caller.
- `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/ReferenceCountService.java`: lock-aware service that retains and releases one reference at a time, rejects missing content, prevents reference-count underflow, and delegates physical deletion to `BlobStorage` only when release reaches zero while leaving transaction ownership with the caller.
- `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/AssetContentMappingTest.java`: boots Hibernate against H2 and verifies persistence state, validation, table naming, identity uniqueness, and indexes.
- `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/AssetContentRepositoryTest.java`: verifies complete identity lookups, pessimistic write lock acquisition, missing-row behavior, constructor validation, and duplicate identity rejection.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/package-info.java`: package marker for provider-neutral core APIs.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/hash/ContentHasher.java`: stream-based content hashing contract.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/hash/ContentHash.java`: content identity value carrying algorithm, hash, and byte size.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/hash/Sha256ContentHasher.java`: streaming SHA-256 implementation that returns lowercase hex.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/key/ObjectKeyStrategy.java`: contract for generating storage object keys from content identity.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/key/HashObjectKeyStrategy.java`: deterministic key strategy using `{prefix}/{algorithm}/{first_two_hash_chars}/{content_hash}`.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/storage/BlobStorage.java`: provider-neutral storage SPI for put, get, idempotent delete, and existence checks.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/storage/PutBlobRequest.java`: validated streaming upload request with immutable metadata.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/storage/StoredBlob.java`: provider-neutral persisted object metadata, including provider and bucket/container location.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/storage/BlobResource.java`: validated read resource that owns and closes its content stream.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/model/StoreBlobCommand.java`: application-facing streaming store command without a caller-controlled object key.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/model/BlobReference.java`: stored-content reference carrying the asset-content ID, content identity, provider, object key, and duplicate decision.
- `blob-helper-core/src/main/java/com/edem/blobhelper/core/exception`: unchecked, provider-neutral exception hierarchy for validation, hashing, storage, missing content, and reference-count underflow.
- `blob-helper-core/src/test/java/com/edem/blobhelper/core/CoreModuleBoundaryTest.java`: scans the effective test classpath and fails when Spring, JPA, AWS SDK, or Azure SDK classes enter core.
- `blob-helper-core/src/test/java/com/edem/blobhelper/core/CoreModuleSmokeTest.java`: verifies the core test package is wired.
- `.github/workflows/ci.yml`: GitHub Actions workflow that runs Maven verify on pushes, pull requests, and manual dispatch.
- `src/main/java/com/edem/blobhelper/BlobHelperApplication.java`: original Spring Boot application class. Current root packaging means this is not part of a normal Spring Boot app module.

## Per-Module Breakdown

### Root Reactor

- **Entry point:** `pom.xml`
- **Key configuration:** Java 21, JUnit 5.13.4 BOM, Maven Compiler Plugin 3.14.1, Maven Surefire Plugin 3.5.4.
- **Initialization:** Maven builds modules listed under `<modules>`.
- **Non-obvious logic:** Root no longer inherits `spring-boot-starter-parent`; it is a plain Maven parent POM.

### blob-helper-core

- **Entry point:** `blob-helper-core/pom.xml`
- **Key classes/functions:** `Sha256ContentHasher.hash(InputStream)` computes lowercase SHA-256 while reading a stream; `HashObjectKeyStrategy.generateKey(ContentHash)` generates deterministic hash-derived relative keys; `BlobStorage` defines provider-neutral storage operations; `PutBlobRequest`, `StoredBlob`, `BlobResource`, `StoreBlobCommand`, and `BlobReference` define the immutable streaming API boundary; `CoreModuleBoundaryTest` inspects classpath directories and JARs for forbidden package roots.
- **Initialization:** Built as Maven child of root `blob-helper`.
- **Non-obvious logic:** Object keys are derived from content identity, not user filenames. Empty key prefixes omit the leading prefix segment and still produce relative keys. Core request/result records reject invalid required fields and defensively copy metadata. `BlobResource` implements `AutoCloseable` and delegates closure to its stream. Storage adapters translate provider failures into unchecked `BlobHelperException` subtypes. Maven Enforcer also rejects forbidden direct and transitive dependency coordinates before tests run.

### blob-helper-jpa

- **Entry point:** `blob-helper-jpa/pom.xml`
- **Key classes/functions:** `AssetContent` maps `blob_asset_content`; its public constructor validates required physical metadata, initializes new content with `refCount = 1`, and JPA lifecycle callbacks maintain creation/update timestamps. `AssetContentRepository.findByIdentity` queries the complete identity tuple, while `findByIdForUpdate` uses `LockModeType.PESSIMISTIC_WRITE`; `ReferenceCountService.retain` and `release` mutate the locked managed entity exactly once, reject missing or underflowed rows, and invoke the injected idempotent `BlobStorage.delete` collaborator only for the final reference. All operations use the caller-owned `EntityManager` and transaction.
- **Initialization:** Built as a Maven child of root `blob-helper`; consuming persistence environments discover the annotated entity, while tests bootstrap the `blob-helper-jpa-test` persistence unit directly.
- **Non-obvious logic:** Content identity is enforced by the database tuple `hash_algorithm + content_hash + size_bytes`. UUID generation and optimistic locking use standard Jakarta Persistence annotations. Repository lookups return `Optional` for missing rows, and locked lookups require the caller's active transaction to retain the database row lock. Reference-count mutation is package-private on the entity and exposed through the service, which throws core `ContentNotFoundException` and `ReferenceCountUnderflowException` before invalid state is persisted. Production code has no Hibernate or Spring imports; Hibernate and H2 are test-only dependencies.

### Documentation and Planning

- **Entry point:** `docs/taskindex.md`
- **Key files:** `docs/SPECIFICATION.md`, `docs/adrs/*.md`, `docs/implementation-plans/*.md`, `docs/epics/**/tasks/*.md`.
- **Initialization:** Manual planning docs drive future implementation tasks.
- **Non-obvious logic:** `docs/taskindex.md` is the status board. Epic 1 is complete and Tasks 2.1 and 2.2 are the completed Epic 2 tasks.

### CI

- **Entry point:** `.github/workflows/ci.yml`
- **Key behavior:** Checks out code, sets up Java 21 Temurin, enables Maven dependency caching, runs `./mvnw --batch-mode --no-transfer-progress verify`, and uploads Surefire reports.
- **Initialization:** Triggered on pushes and pull requests targeting `main`, `staging`, or `dev`, and by manual `workflow_dispatch`.
- **Non-obvious logic:** The workflow runs `chmod +x ./mvnw` so CI is not blocked if executable bits are lost.

## Configuration

| Variable / Property | Default | Purpose |
|---|---|---|
| `spring.application.name` | `blob-helper` | Present in root `src/main/resources/application.yaml`. |
| `java.version` | `21` | Maven compiler release target. |
| `junit.version` | `5.13.4` | JUnit BOM version. |

Planned future configuration from the specification:

| Property | Purpose |
|---|---|
| `blob-helper.storage.provider` | Selects `s3`, `azure`, `local`, or custom provider. |
| `blob-helper.storage.key-prefix` | Prefix for generated object keys. |
| `blob-helper.deduplication.hash-algorithm` | Initial target is `SHA-256`. |
| `blob-helper.cleanup.reconciliation-enabled` | Disabled by default; controls reconciliation scheduling. |
