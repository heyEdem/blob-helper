package com.edem.blobhelper.dashboard;

import com.edem.blobhelper.dashboard.api.EmbeddedDashboardController;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class EmbeddedDashboardIntegrationTest {
    @Test
    void redirectsTheNoSlashRootSoRelativeAssetsResolveUnderTheDashboardPath() {
        assertThat(EmbeddedDashboardController.rootViewName("/blob-helper/dashboard", false))
                .isEqualTo("redirect:/blob-helper/dashboard/");
        assertThat(EmbeddedDashboardController.rootViewName("/blob-helper/dashboard", true))
                .isEqualTo("forward:/blob-helper/dashboard/index.html");
    }

    @Test
    void packagesTheUiWithRelativeApiRequestsAndNoSqlitePersistence() throws Exception {
        var index = getClass().getResourceAsStream("/static/blob-helper/dashboard/index.html");
        var script = getClass().getResourceAsStream("/static/blob-helper/dashboard/js/dashboard.js");
        assertThat(index).isNotNull();
        assertThat(script).isNotNull();
        assertThat(new String(index.readAllBytes(), StandardCharsets.UTF_8)).contains("Make every byte count.");
        assertThat(new String(script.readAllBytes(), StandardCharsets.UTF_8)).contains("/api/v1/overview");
        assertThat(getClass().getResource("/blob-helper-dashboard.sqlite")).isNull();
    }
}
