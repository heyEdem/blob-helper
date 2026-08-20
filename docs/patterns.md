# Patterns

## Naming Conventions

- Files: Java source files use PascalCase class names matching filenames.
- Classes/types: PascalCase, e.g. `BlobHelperApplication`, `CoreModuleSmokeTest`.
- Functions/methods: lowerCamelCase, e.g. `main`, `contextLoads`, `coreModuleTestsRunInExpectedPackage`.
- Variables: lowerCamelCase where present.
- Docs: ADR files use `ADR-###-kebab-case-title.md`; implementation plans use `PLAN-###-kebab-case-title.md`; epic tasks use `task-###-kebab-case-title.md`.

## Folder Conventions

- Maven modules live at repository root, e.g. `blob-helper-core`.
- Java production code follows `src/main/java`.
- Java tests follow `src/test/java`.
- Project planning docs live under `docs/`.
- ADRs live under `docs/adrs/`.
- Phase plans live under `docs/implementation-plans/`.
- Epic task files live under `docs/epics/<epic>/tasks/`.

## Recurring Code Patterns

- Error handling: not enough implemented code to determine project-specific error handling.
- Async: not present in current implementation.
- Dependency injection: Spring Boot annotation exists in legacy root shell, but no active service wiring exists yet.
- Validation: constructors reject null or blank required text and invalid negative sizes before state crosses a module boundary.
- Testing: current tests are JUnit Jupiter tests with package-private test classes.
- Dependency boundaries: reusable modules pair classpath-level package scanning with Maven Enforcer rules so both loaded classes and direct/transitive artifact coordinates are guarded.
- JPA entities: use field access, a protected no-argument constructor, explicit snake_case column names, constructor validation for required metadata, portable lifecycle callbacks for timestamps, and standard `@Version` optimistic locking.
- JPA repositories: wrap a caller-owned `EntityManager`, return `Optional` for lookup methods, and apply `LockModeType.PESSIMISTIC_WRITE` for mutation workflows that must hold a row lock through the caller's transaction.

## Testing Conventions

- Test file location: `src/test/java` within each module.
- Test naming: descriptive lowerCamelCase methods, e.g. `coreModuleTestsRunInExpectedPackage`.
- Test helpers: none observed.
- Run all current tests with `./mvnw test`.
- Run core tests with `./mvnw -pl blob-helper-core test`.
- Run JPA mapping tests with `./mvnw -pl blob-helper-jpa test`; they use a real Hibernate persistence unit backed by in-memory H2.
- Name dependency boundary tests `*BoundaryTest` so they can be run together with `./mvnw test -Dtest='*BoundaryTest'`.

## Pull Request Conventions

- Use the global `$pr-writer` skill for every pull request title and body, including reviews and updates to existing pull requests.
- Derive claims from the actual branch/base, commit range, diff, test results, and issue context.
- Use a conventional-commit title and the reviewer-oriented sections defined by the skill; verify the remote pull request after creation or editing.

## Anti-Patterns Observed

- Root `src/` still contains a Spring Boot shell class while the root project is `pom` packaging. This is likely transitional during conversion to a multi-module library.
