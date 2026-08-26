# Implementation

## Entry Points

- `pom.xml`: parent Maven reactor. Declares `blob-helper-core`, `blob-helper-jpa`, `blob-helper-spring-boot-starter`, and `blob-helper-storage-local` as current modules and manages JUnit/Spring Boot dependency BOMs.
- `blob-helper-core/pom.xml`: core module build file. Depends on JUnit Jupiter for tests.
- `blob-helper-jpa/pom.xml`: persistence module build file. Depends on `blob-helper-core`, exposes Jakarta Persistence, and uses Hibernate/H2 in test scope.
- `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/AssetContent.java`: JPA entity for unique physical content identity, object location, metadata, reference count, timestamps, and optimistic locking.
- `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/AssetContentRepository.java`: EntityManager-backed repository for identity lookup and pessimistic write-locked lookup by content id; transaction lifecycle remains with the caller.
- `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/AssetContentMutationService.java`: create-or-retain service that flushes new inserts, catches SQL duplicate-key races, restarts the failed resource-local transaction, and reloads/increments the winning row without storage collaboration.
- `blob-helper-jpa/src/main/java/com/edem/blobhelper/jpa/ReferenceCountService.java`: lock-aware service that retains and releases one reference at a time, rejects missing content, prevents reference-count underflow, and delegates physical deletion to `BlobStorage` only when release reaches zero while leaving transaction ownership with the caller.
- `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/AssetContentMappingTest.java`: boots Hibernate against H2 and verifies persistence state, validation, table naming, identity uniqueness, and indexes.
- `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/AssetContentRepositoryTest.java`: verifies complete identity lookups, pessimistic write lock acquisition, missing-row behavior, constructor validation, and duplicate identity rejection.
- `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/AssetContentMutationServiceTest.java`: verifies new-row creation, ordinary duplicate retention, and a coordinated concurrent insert race that reloads the winner and increments its count once.
- `blob-helper-jpa/src/test/java/com/edem/blobhelper/jpa/ConcurrentUploadIntegrationTest.java`: verifies two parallel create-or-retain workers converge on one identity row and the final reference count equals the worker count.
- `blob-helper-spring-boot-starter/pom.xml`: starter module build file with Spring Boot auto-configuration and configuration-processor dependencies, plus a test-scoped dependency on `blob-helper-storage-local` for end-to-end provider coverage.
- `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/autoconfigure/BlobHelperProperties.java`: binds storage, deduplication, and cleanup settings under the `blob-helper` prefix, including upload-size parsing and reconciliation defaults.
- `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/autoconfigure/BlobHelperAutoConfiguration.java`: auto-configuration (registered in `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`) that enables `BlobHelperProperties` and installs a startup validator enforcing exactly one configured `BlobStorage` provider.
- `blob-helper-spring-boot-starter/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`: Spring Boot 3 auto-configuration registration for `BlobHelperAutoConfiguration`.
- `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/service/BlobDeduplicationService.java`: provider-neutral application-facing contract for store, retain, release, and get operations.
- `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/service/DefaultBlobDeduplicationService.java`: default facade that delegates reference mutation and content retrieval to JPA/storage collaborators and orchestrates uploads by buffering bytes, hashing with `ContentHasher`, looking up complete content identity, retaining duplicates without physical writes, generating deterministic keys for new content, writing through `BlobStorage`, and creating metadata through `AssetContentMutationService`.
- `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/autoconfigure/BlobHelperPropertiesTest.java`: verifies relaxed Spring Boot binding for configured values and the disabled-by-default reconciliation setting.
- `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/autoconfigure/BlobHelperAutoConfigurationTest.java`: uses the Spring `ApplicationContextRunner` to verify provider selection with `provider=local`, acceptance of a single unselected provider, and clear startup failures for unsupported, missing, selected-with-no-matching-bean, and ambiguous provider wiring.
- `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/service/BlobDeduplicationServiceContractTest.java`: verifies the service method signatures, provider-neutral return types, and missing-content exception behavior.
- `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/service/BlobDeduplicationServiceTest.java`: verifies new and duplicate upload orchestration against Hibernate/H2 with a recording storage fake: one physical write, one metadata row, reference retention, and duplicate decision reporting.
- `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/service/LocalStorageDeduplicationIntegrationTest.java`: verifies the complete service path with a real temporary-directory local provider: readback, one physical file for duplicate uploads, and final-reference deletion.
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
- `blob-helper-storage-local/pom.xml`: local storage adapter module build file. Depends only on `blob-helper-core` and JUnit Jupiter; no cloud SDKs.
- `blob-helper-storage-local/src/main/java/com/edem/blobhelper/storage/local/LocalBlobStorageProperties.java`: local provider configuration with a configurable `rootDirectory` (defaults to `blob-helper-storage`) that rejects null assignment.
- `blob-helper-storage-local/src/test/java/com/edem/blobhelper/storage/local/LocalBlobStoragePropertiesTest.java`: verifies the default root directory, custom root binding, and null rejection.
- `blob-helper-storage-local/src/main/java/com/edem/blobhelper/storage/local/LocalBlobStorage.java`: filesystem `BlobStorage` adapter that resolves object keys under the configured absolute root, streams uploads with parent-directory creation and overwrite semantics, returns owner-managed `BlobResource` streams, throws core `ContentNotFoundException` for missing reads, deletes idempotently via `Files.deleteIfExists`, and reports existence from the filesystem. Key resolution normalizes the resolved path and rejects keys that escape or equal the root with core `BlobValidationException` before any file IO.
- `blob-helper-storage-local/src/test/java/com/edem/blobhelper/storage/local/LocalBlobStorageIntegrationTest.java`: JUnit temporary-directory coverage for the put/get/delete round trip with filesystem assertions, idempotent missing-object delete, pre-write existence checks, overwrite behavior, blank-key rejection, and traversal protection for `../`, absolute-path, and self-resolving keys plus valid nested-key access.
- `.github/workflows/ci.yml`: GitHub Actions workflow that runs Maven verify on pushes, pull requests, and manual dispatch.
- `src/main/java/com/edem/blobhelper/BlobHelperApplication.java`: original Spring Boot application class. Current root packaging means this is not part of a normal Spring Boot app module.

## Per-Module Breakdown

### Root Reactor

- **Entry point:** `pom.xml`
- **Key configuration:** Java 21, JUnit 5.13.4 BOM, Spring Boot 3.5.10 BOM, Maven Compiler Plugin 3.14.1, Maven Surefire Plugin 3.5.4.
- **Initialization:** Maven builds modules listed under `<modules>`.
- **Non-obvious logic:** Root no longer inherits `spring-boot-starter-parent`; it is a plain Maven parent POM.

### blob-helper-core

- **Entry point:** `blob-helper-core/pom.xml`
- **Key classes/functions:** `Sha256ContentHasher.hash(InputStream)` computes lowercase SHA-256 while reading a stream; `HashObjectKeyStrategy.generateKey(ContentHash)` generates deterministic hash-derived relative keys; `BlobStorage` defines provider-neutral storage operations; `PutBlobRequest`, `StoredBlob`, `BlobResource`, `StoreBlobCommand`, and `BlobReference` define the immutable streaming API boundary; `CoreModuleBoundaryTest` inspects classpath directories and JARs for forbidden package roots.
- **Initialization:** Built as Maven child of root `blob-helper`.
- **Non-obvious logic:** Object keys are derived from content identity, not user filenames. Empty key prefixes omit the leading prefix segment and still produce relative keys. Core request/result records reject invalid required fields and defensively copy metadata. `BlobResource` implements `AutoCloseable` and delegates closure to its stream. Storage adapters translate provider failures into unchecked `BlobHelperException` subtypes. Maven Enforcer also rejects forbidden direct and transitive dependency coordinates before tests run.

### blob-helper-jpa

- **Entry point:** `blob-helper-jpa/pom.xml`
- **Key classes/functions:** `AssetContent` maps `blob_asset_content`; its public constructor validates required physical metadata, initializes new content with `refCount = 1`, and JPA lifecycle callbacks maintain creation/update timestamps. `AssetContentRepository.findByIdentity` queries the complete identity tuple, while `findByIdForUpdate` uses `LockModeType.PESSIMISTIC_WRITE`; `AssetContentMutationService.createOrRetain` returns an existing locked row or flushes a new insert and retries SQL state `23505` failures by restarting the failed resource-local transaction, reloading the identity row, and incrementing once. `ReferenceCountService.retain` and `release` mutate the locked managed entity exactly once, reject missing or underflowed rows, and invoke the injected idempotent `BlobStorage.delete` collaborator only for the final reference. `ConcurrentUploadIntegrationTest` runs the create-or-retain path from separate transactions and verifies one row with one reference per worker. Production code has no Hibernate or Spring imports; Hibernate and H2 are test-only dependencies.
- **Initialization:** Built as a Maven child of root `blob-helper`; consuming persistence environments discover the annotated entity, while tests bootstrap the `blob-helper-jpa-test` persistence unit directly.
- **Non-obvious logic:** Content identity is enforced by the database tuple `hash_algorithm + content_hash + size_bytes`. UUID generation and optimistic locking use standard Jakarta Persistence annotations. Repository lookups return `Optional` for missing rows, and locked lookups require the caller's active transaction to retain the database row lock. Reference-count mutation is package-private on the entity and exposed through the service, which throws core `ContentNotFoundException` and `ReferenceCountUnderflowException` before invalid state is persisted. Production code has no Hibernate or Spring imports; Hibernate and H2 are test-only dependencies.

### blob-helper-spring-boot-starter

- **Entry point:** `blob-helper-spring-boot-starter/pom.xml`
- **Key classes/functions:** `BlobHelperAutoConfiguration` registers `BlobHelperProperties` and a `BlobStorageProviderValidator` (`SmartInitializingSingleton`) that fails startup for unsupported provider names, missing provider beans, selected providers without a matching bean (bean-name convention `<provider>BlobStorage`), and ambiguous multi-provider contexts. `BlobHelperProperties` binds `blob-helper.storage.provider`, `storage.key-prefix`, deduplication hash and upload validation settings, and cleanup deletion/reconciliation settings. `BlobDeduplicationService` exposes storage-neutral `store`, `retain`, `release`, and `get` operations using core models. `DefaultBlobDeduplicationService` buffers upload bytes, hashes them with `ContentHasher`, checks the complete identity tuple, retains existing rows through the lock-aware `ReferenceCountService`, and for new content generates a deterministic key through `ObjectKeyStrategy`, writes through `BlobStorage`, and persists `AssetContent` through `AssetContentMutationService`. `deduplication.max-upload-size` uses Spring Boot `DataSize` binding, so values such as `25MB` are accepted.
- **Initialization:** Built as a Maven child of root `blob-helper`; production code depends on the provider-neutral core and JPA metadata modules, while tests additionally depend on `blob-helper-storage-local`. The auto-configuration is registered through `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`; provider modules contribute their own conditional `BlobStorage` beans in later epics.
- **Non-obvious logic:** Reconciliation is disabled by default, physical deletion on zero references is enabled by default, service callers retain transaction ownership, and the starter has no REST controllers or provider SDK runtime dependencies. Provider validation runs after all singletons instantiate so failures name every conflicting or missing bean; supported provider names are `local`, `s3`, and `azure`, while a single unselected custom provider is accepted. Upload streams are buffered once so hashing and storage can consume equivalent byte sequences; duplicate matches reuse the persisted object key and content ID. `LocalStorageDeduplicationIntegrationTest` wires the real local adapter only in test scope and verifies those behaviors end to end.

### blob-helper-storage-local

- **Entry point:** `blob-helper-storage-local/pom.xml`
- **Key classes/functions:** `LocalBlobStorageProperties` carries the local provider root directory; it defaults to `blob-helper-storage`, accepts any custom `Path`, and rejects null assignment. `LocalBlobStorage` implements the core `BlobStorage` SPI against that root: `put` streams the request content to `{root}/{objectKey}` (creating parent directories, replacing existing files) and returns a `StoredBlob` with provider `local` and the root as bucket; `get` returns a closeable `BlobResource` or throws core `ContentNotFoundException` when absent; `delete` is idempotent through `Files.deleteIfExists`; `exists` delegates to filesystem checks.
- **Initialization:** Built as a Maven child of root `blob-helper`; it depends only on the provider-neutral core module with no Spring or cloud SDK dependencies.
- **Non-obvious logic:** Keys resolve against an absolute normalized root; resolution then normalizes again and rejects results outside or equal to the root using component-based `startsWith` comparison, which defeats `..`, absolute-path, and prefix-collision escapes before any file IO. Storage failures translate to unchecked core `BlobStorageException`; missing reads use `ContentNotFoundException`. Content type and metadata are not persisted by the local adapter.

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
| `spring-boot.version` | `3.5.10` | Spring Boot BOM version used by the starter module. |

Implemented starter properties:

| Property | Default | Purpose |
|---|---|---|
| `blob-helper.storage.provider` | None | Selects `s3`, `azure`, `local`, or custom provider. |
| `blob-helper.storage.key-prefix` | Empty | Prefix for generated object keys. |
| `blob-helper.deduplication.hash-algorithm` | `SHA-256` | Content identity hash algorithm. |
| `blob-helper.deduplication.max-upload-size` | `25MB` | Maximum upload size. |
| `blob-helper.deduplication.strict-content-type-validation` | `false` | Rejects unsupported content types when enabled. |
| `blob-helper.cleanup.delete-physical-on-zero-references` | `true` | Controls physical deletion after the final reference is released. |
| `blob-helper.cleanup.reconciliation-enabled` | `false` | Controls reconciliation scheduling; disabled by default. |
