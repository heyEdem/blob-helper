# Task 3.1: Add Starter Module and Properties

**Status:** Pending  
**Source:** [PLAN-003](../../../implementation-plans/PLAN-003-spring-boot-starter-service-api.md)  
**ADR:** [ADR-004](../../../adrs/ADR-004-pluggable-storage-and-spring-boot-starter.md)

## Goal

Create the Spring Boot starter module and bind `blob-helper.*` configuration.

## Files

- Modify: `pom.xml`
- Create: `blob-helper-spring-boot-starter/pom.xml`
- Create: `blob-helper-spring-boot-starter/src/main/java/com/edem/blobhelper/autoconfigure/BlobHelperProperties.java`
- Create: `blob-helper-spring-boot-starter/src/test/java/com/edem/blobhelper/autoconfigure/BlobHelperPropertiesTest.java`

## Steps

- [ ] Add starter module to the reactor.
- [ ] Add properties for `storage`, `deduplication`, and `cleanup`.
- [ ] Bind provider, key prefix, max upload size, strict content type validation, delete-on-zero-references, and reconciliation settings.
- [ ] Run `./mvnw -pl blob-helper-spring-boot-starter test`.

## Acceptance

- [ ] Properties bind from `blob-helper.*`.
- [ ] Reconciliation is disabled by default.
- [ ] No REST controllers are added.
