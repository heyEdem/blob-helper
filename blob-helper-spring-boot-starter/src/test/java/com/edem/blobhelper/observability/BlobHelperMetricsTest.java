package com.edem.blobhelper.observability;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BlobHelperMetricsTest {

    @Test
    void recordsUploadOutcomesAndByteSavings() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        BlobHelperMetrics metrics = new BlobHelperMetrics(registry);

        metrics.recordUpload(10L, false);
        metrics.recordUpload(10L, true);

        assertThat(registry.get("blob.helper.uploads").counter().count()).isEqualTo(2.0);
        assertThat(registry.get("blob.helper.duplicates").counter().count()).isEqualTo(1.0);
        assertThat(registry.get("blob.helper.skipped.physical.writes").counter().count())
                .isEqualTo(1.0);
        assertThat(registry.get("blob.helper.bytes.accepted").counter().count()).isEqualTo(20.0);
        assertThat(registry.get("blob.helper.bytes.avoided").counter().count()).isEqualTo(10.0);
    }

    @Test
    void recordsOperationTimersFailuresAndRepairs() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        BlobHelperMetrics metrics = new BlobHelperMetrics(registry);

        metrics.recordHashing(() -> "hash");
        metrics.recordStorageWrite(() -> "stored");
        metrics.recordDeleteFailure();
        metrics.recordRepair();

        assertThat(registry.get("blob.helper.hashing").timer().count()).isEqualTo(1L);
        assertThat(registry.get("blob.helper.storage.writes").timer().count()).isEqualTo(1L);
        assertThat(registry.get("blob.helper.storage.delete.failures").counter().count())
                .isEqualTo(1.0);
        assertThat(registry.get("blob.helper.repairs").counter().count()).isEqualTo(1.0);
    }

    @Test
    void remainsOptionalWithoutARegistry() {
        BlobHelperMetrics metrics = new BlobHelperMetrics(null);

        assertThat(metrics.recordHashing(() -> "hash")).isEqualTo("hash");
        assertThat(metrics.recordStorageWrite(() -> "stored")).isEqualTo("stored");
        metrics.recordUpload(10L, true);
        metrics.recordDeleteFailure();
        metrics.recordRepair();
    }
}
