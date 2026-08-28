# Task 7.2: Add YAML Self-Registration and Dashboard Shell

**Status:** Pending
**Source:** [PLAN-007](../../../implementation-plans/PLAN-007-local-dashboard-monitoring.md)
**ADR:** [ADR-005](../../../adrs/ADR-005-local-dashboard-pull-monitoring.md)

## Goal

Allow local Blob Helper applications to register themselves and provide a
standalone dashboard process bound to loopback.

## Files

- Modify: `pom.xml`
- Create: `blob-helper-dashboard/pom.xml`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/BlobHelperDashboardApplication.java`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/registration/InstanceRegistrationController.java`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/registration/InstanceRegistrationClient.java`
- Create: `blob-helper-dashboard/src/main/java/com/edem/blobhelper/dashboard/registration/InstanceRegistration.java`
- Create: `blob-helper-dashboard/src/main/resources/application.yaml`
- Create: `blob-helper-dashboard/src/test/java/com/edem/blobhelper/dashboard/registration/InstanceRegistrationTest.java`

## Acceptance

- [ ] Instances opt in through `application.yaml` with dashboard URL, instance
      name, and advertised management URL.
- [ ] Registration creates or updates an instance by stable generated ID.
- [ ] Duplicate startup registration is idempotent.
- [ ] Dashboard defaults to `server.address=127.0.0.1` and port `9090`.
- [ ] Dashboard registration accepts local requests without tokens in the MVP.
- [ ] An unreachable instance can remain registered and later recover through
      polling; no manual re-registration is required.
