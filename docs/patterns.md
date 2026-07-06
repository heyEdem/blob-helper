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
- Validation: not present in current implementation.
- Testing: current tests are JUnit Jupiter tests with package-private test classes.

## Testing Conventions

- Test file location: `src/test/java` within each module.
- Test naming: descriptive lowerCamelCase methods, e.g. `coreModuleTestsRunInExpectedPackage`.
- Test helpers: none observed.
- Run all current tests with `./mvnw test`.
- Run core tests with `./mvnw -pl blob-helper-core test`.

## Anti-Patterns Observed

- Root `src/` still contains a Spring Boot shell class while the root project is `pom` packaging. This is likely transitional during conversion to a multi-module library.
