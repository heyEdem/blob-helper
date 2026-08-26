package com.edem.blobhelper.autoconfigure;

import com.edem.blobhelper.core.storage.BlobResource;
import com.edem.blobhelper.core.storage.BlobStorage;
import com.edem.blobhelper.core.storage.PutBlobRequest;
import com.edem.blobhelper.core.storage.StoredBlob;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlobHelperAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(BlobHelperAutoConfiguration.class));

    @Test
    void wiresConfiguredProvider() {
        runner.withUserConfiguration(LocalStorageConfig.class)
                .withPropertyValues("blob-helper.storage.provider=local")
                .run(context -> {
                    assertTrue(context.isActive());
                    assertNotNull(context.getBean("localBlobStorage", BlobStorage.class));
                });
    }

    @Test
    void acceptsSingleUnselectedProvider() {
        runner.withUserConfiguration(LocalStorageConfig.class)
                .run(context -> {
                    assertTrue(context.isActive());
                    assertNotNull(context.getBean("localBlobStorage", BlobStorage.class));
                });
    }

    @Test
    void failsForUnsupportedProvider() {
        runner.withUserConfiguration(LocalStorageConfig.class)
                .withPropertyValues("blob-helper.storage.provider=grid")
                .run(context -> assertFailureContains(
                        context.getStartupFailure(),
                        "Unsupported blob-helper storage provider 'grid'"
                ));
    }

    @Test
    void failsWhenNoProviderBeansExist() {
        runner.withPropertyValues("blob-helper.storage.provider=local")
                .run(context -> assertFailureContains(
                        context.getStartupFailure(),
                        "No BlobStorage provider is configured"
                ));
    }

    @Test
    void failsWhenSelectedProviderHasNoMatchingBean() {
        runner.withUserConfiguration(LocalStorageConfig.class)
                .withPropertyValues("blob-helper.storage.provider=s3")
                .run(context -> assertFailureContains(
                        context.getStartupFailure(),
                        "No BlobStorage bean matching provider 's3'"
                ));
    }

    @Test
    void failsForAmbiguousUnselectedProviders() {
        runner.withUserConfiguration(LocalStorageConfig.class, SecondStorageConfig.class)
                .run(context -> assertFailureContains(
                        context.getStartupFailure(),
                        "Ambiguous BlobStorage configuration"
                ));
    }

    @Test
    void failsForAmbiguousSelectedProviderBeans() {
        runner.withUserConfiguration(LocalStorageConfig.class, DuplicateLocalConfig.class)
                .withPropertyValues("blob-helper.storage.provider=local")
                .run(context -> assertFailureContains(
                        context.getStartupFailure(),
                        "Ambiguous BlobStorage configuration"
                ));
    }

    private static void assertFailureContains(Throwable failure, String expected) {
        Throwable current = failure;
        while (current != null) {
            if (current.getMessage() != null && current.getMessage().contains(expected)) {
                return;
            }
            current = current.getCause();
        }
        throw new AssertionError(
                "Expected startup failure to contain '" + expected + "' but was: " + failure,
                failure
        );
    }

    @Configuration(proxyBeanMethods = false)
    static class LocalStorageConfig {

        @Bean
        BlobStorage localBlobStorage() {
            return new NoopBlobStorage();
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class SecondStorageConfig {

        @Bean
        BlobStorage s3BlobStorage() {
            return new NoopBlobStorage();
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class DuplicateLocalConfig {

        @Bean
        BlobStorage localBlobStorageMirror() {
            return new NoopBlobStorage();
        }
    }

    static class NoopBlobStorage implements BlobStorage {

        @Override
        public StoredBlob put(PutBlobRequest request) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BlobResource get(String objectKey) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void delete(String objectKey) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean exists(String objectKey) {
            return false;
        }
    }
}
