# Task 7.1: Add Management Module and Local Read-Only API

**Status:** Complete
**Source:** [PLAN-007](../../../implementation-plans/PLAN-007-local-dashboard-monitoring.md)
**ADR:** [ADR-005](../../../adrs/ADR-005-local-dashboard-pull-monitoring.md)

## Goal

Expose provider-neutral operational information from a Blob Helper instance
without adding business controllers to the starter or exposing provider
credentials.

## Files

- Modify: `pom.xml`
- Create: `blob-helper-spring-boot-management/pom.xml`
- Create: `blob-helper-spring-boot-management/src/main/java/com/edem/blobhelper/management/BlobHelperManagementProperties.java`
- Create: `blob-helper-spring-boot-management/src/main/java/com/edem/blobhelper/management/BlobHelperManagementSnapshot.java`
- Create: `blob-helper-spring-boot-management/src/main/java/com/edem/blobhelper/management/BlobHelperManagementController.java`
- Create: `blob-helper-spring-boot-management/src/main/java/com/edem/blobhelper/management/BlobHelperManagementAutoConfiguration.java`
- Create: `blob-helper-spring-boot-management/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
- Create: `blob-helper-spring-boot-management/src/test/java/com/edem/blobhelper/management/BlobHelperManagementControllerTest.java`

## Acceptance

- [x] Management is disabled unless explicitly enabled.
- [x] `GET /blob-helper/management/v1/info`, `/health`, `/metrics`, and
      `/failures` are read-only and return provider-neutral JSON.
- [x] Responses include instance ID/name, provider, cumulative operation and
      byte counters, current content totals, and recent failure details.
- [x] No endpoint accepts blob deletion, reference repair, or provider
      credentials.
- [x] The module depends on the starter/core contracts and introduces no cloud
      SDK dependency.
