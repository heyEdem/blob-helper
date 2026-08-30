package com.edem.blobhelper.observability;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Optional Micrometer instrumentation for Blob Helper operations.
 *
 * <p>A {@code null} registry disables instrumentation while preserving the
 * operation behavior. Applications using Spring Boot Actuator can provide
 * their normal {@link MeterRegistry} and receive the meters automatically.</p>
 */
public final class BlobHelperMetrics {

    private static final String UPLOADS = "blob.helper.uploads";
    private static final String DUPLICATES = "blob.helper.duplicates";
    private static final String SKIPPED_PHYSICAL_WRITES = "blob.helper.skipped.physical.writes";
    private static final String ACCEPTED_BYTES = "blob.helper.bytes.accepted";
    private static final String AVOIDED_BYTES = "blob.helper.bytes.avoided";
    private static final String HASHING = "blob.helper.hashing";
    private static final String STORAGE_WRITES = "blob.helper.storage.writes";
    private static final String DELETE_FAILURES = "blob.helper.storage.delete.failures";
    private static final String REPAIRS = "blob.helper.repairs";

    private final MeterRegistry registry;

    public BlobHelperMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    public void recordUpload(long bytes, boolean duplicate) {
        if (registry == null) {
            return;
        }
        registry.counter(UPLOADS).increment();
        registry.counter(ACCEPTED_BYTES).increment(bytes);
        if (duplicate) {
            registry.counter(DUPLICATES).increment();
            registry.counter(SKIPPED_PHYSICAL_WRITES).increment();
            registry.counter(AVOIDED_BYTES).increment(bytes);
        }
    }

    public <T> T recordHashing(Supplier<T> operation) {
        Objects.requireNonNull(operation, "operation must not be null");
        return record(HASHING, operation);
    }

    public <T> T recordStorageWrite(Supplier<T> operation) {
        Objects.requireNonNull(operation, "operation must not be null");
        return record(STORAGE_WRITES, operation);
    }

    public void recordDeleteFailure() {
        if (registry != null) {
            registry.counter(DELETE_FAILURES).increment();
        }
    }

    public void recordRepair() {
        if (registry != null) {
            registry.counter(REPAIRS).increment();
        }
    }

    private <T> T record(String timerName, Supplier<T> operation) {
        if (registry == null) {
            return operation.get();
        }
        return registry.timer(timerName).record(operation);
    }
}
