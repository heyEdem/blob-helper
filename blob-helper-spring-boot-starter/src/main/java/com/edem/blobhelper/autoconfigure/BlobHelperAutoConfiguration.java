package com.edem.blobhelper.autoconfigure;

import com.edem.blobhelper.core.storage.BlobStorage;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

/**
 * Auto-configuration that validates the Blob Helper storage wiring.
 *
 * <p>The starter never contains provider SDK code. Provider modules
 * contribute their own {@link BlobStorage} beans; this configuration only
 * guarantees that exactly one provider is selected at startup.</p>
 */
@AutoConfiguration
@EnableConfigurationProperties(BlobHelperProperties.class)
public class BlobHelperAutoConfiguration {

    @Bean
    public static BlobStorageProviderValidator blobStorageProviderValidator(
            ConfigurableListableBeanFactory beanFactory,
            BlobHelperProperties properties
    ) {
        return new BlobStorageProviderValidator(beanFactory, properties);
    }

    static final class BlobStorageProviderValidator implements SmartInitializingSingleton {

        static final Set<String> SUPPORTED_PROVIDERS = Set.of("local", "s3", "azure");

        private final ConfigurableListableBeanFactory beanFactory;
        private final String provider;

        BlobStorageProviderValidator(
                ConfigurableListableBeanFactory beanFactory,
                BlobHelperProperties properties
        ) {
            this.beanFactory = beanFactory;
            this.provider = normalize(properties.getStorage().getProvider());
        }

        @Override
        public void afterSingletonsInstantiated() {
            if (provider != null && !SUPPORTED_PROVIDERS.contains(provider)) {
                throw new IllegalStateException(
                        "Unsupported blob-helper storage provider '" + provider
                                + "'. Supported providers: " + String.join(", ", SUPPORTED_PROVIDERS) + "."
                );
            }

            String[] candidateNames = beanFactory.getBeanNamesForType(BlobStorage.class);
            if (candidateNames.length == 0) {
                throw new IllegalStateException(
                        "No BlobStorage provider is configured. Add a provider module such as "
                                + "blob-helper-storage-local to the classpath and set "
                                + "'blob-helper.storage.provider'."
                );
            }
            if (provider == null) {
                if (candidateNames.length > 1) {
                    throw ambiguous(candidateNames);
                }
                return;
            }

            String[] matching = Arrays.stream(candidateNames)
                    .filter(name -> name.toLowerCase(Locale.ROOT).contains(provider))
                    .toArray(String[]::new);
            if (matching.length == 0) {
                throw new IllegalStateException(
                        "No BlobStorage bean matching provider '" + provider
                                + "' was found. Expected a bean named like '"
                                + provider + "BlobStorage'."
                );
            }
            if (matching.length > 1) {
                throw ambiguous(matching);
            }
        }

        private static IllegalStateException ambiguous(String[] names) {
            return new IllegalStateException(
                    "Ambiguous BlobStorage configuration: multiple providers found ("
                            + String.join(", ", names)
                            + "). Set 'blob-helper.storage.provider' to select exactly one."
            );
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim().toLowerCase(Locale.ROOT);
        }
    }
}
