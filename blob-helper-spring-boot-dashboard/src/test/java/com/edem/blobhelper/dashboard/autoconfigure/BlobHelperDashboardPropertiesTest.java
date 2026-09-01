package com.edem.blobhelper.dashboard.autoconfigure;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class BlobHelperDashboardPropertiesTest {
    @Test
    void hasSafeDefaults() {
        var properties = new BlobHelperDashboardProperties();
        assertThat(properties.isEnabled()).isTrue();
        assertThat(properties.getBasePath()).isEqualTo("/blob-helper/dashboard");
        assertThat(properties.getFailureLookback()).isEqualTo(Duration.ofDays(7));
    }

    @Test
    void normalizesBasePath() {
        var properties = new BlobHelperDashboardProperties();
        properties.setBasePath("dashboard/");
        assertThat(properties.getBasePath()).isEqualTo("/dashboard");
    }
}
