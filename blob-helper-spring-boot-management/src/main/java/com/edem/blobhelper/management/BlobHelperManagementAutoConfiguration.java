package com.edem.blobhelper.management;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import com.edem.blobhelper.autoconfigure.BlobHelperProperties;

@AutoConfiguration
@EnableConfigurationProperties({BlobHelperManagementProperties.class, BlobHelperProperties.class})
@ConditionalOnProperty(prefix = "blob-helper.management", name = "enabled", havingValue = "true")
public class BlobHelperManagementAutoConfiguration {

    @Bean
    BlobHelperManagementController blobHelperManagementController(
            BlobHelperManagementProperties managementProperties,
            BlobHelperProperties blobHelperProperties,
            org.springframework.beans.factory.ObjectProvider<io.micrometer.core.instrument.MeterRegistry> meterRegistry,
            org.springframework.beans.factory.ObjectProvider<com.edem.blobhelper.jpa.AssetContentRepository> contentRepository,
            org.springframework.beans.factory.ObjectProvider<BlobHelperManagementSnapshot.FailureSource> failureSource
    ) {
        return new BlobHelperManagementController(
                managementProperties, blobHelperProperties, meterRegistry, contentRepository, failureSource);
    }
}
