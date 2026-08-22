package com.edem.blobhelper.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix = "blob-helper")
public class BlobHelperProperties {

    @NestedConfigurationProperty
    private final Storage storage = new Storage();

    @NestedConfigurationProperty
    private final Deduplication deduplication = new Deduplication();

    @NestedConfigurationProperty
    private final Cleanup cleanup = new Cleanup();

    public Storage getStorage() {
        return storage;
    }

    public Deduplication getDeduplication() {
        return deduplication;
    }

    public Cleanup getCleanup() {
        return cleanup;
    }

    public static class Storage {

        private String provider;
        private String keyPrefix = "";

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getKeyPrefix() {
            return keyPrefix;
        }

        public void setKeyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
        }
    }

    public static class Deduplication {

        private String hashAlgorithm = "SHA-256";
        private DataSize maxUploadSize = DataSize.ofMegabytes(25);
        private boolean strictContentTypeValidation;

        public String getHashAlgorithm() {
            return hashAlgorithm;
        }

        public void setHashAlgorithm(String hashAlgorithm) {
            this.hashAlgorithm = hashAlgorithm;
        }

        public DataSize getMaxUploadSize() {
            return maxUploadSize;
        }

        public void setMaxUploadSize(DataSize maxUploadSize) {
            this.maxUploadSize = maxUploadSize;
        }

        public boolean isStrictContentTypeValidation() {
            return strictContentTypeValidation;
        }

        public void setStrictContentTypeValidation(boolean strictContentTypeValidation) {
            this.strictContentTypeValidation = strictContentTypeValidation;
        }
    }

    public static class Cleanup {

        private boolean deletePhysicalOnZeroReferences = true;
        private boolean reconciliationEnabled;

        public boolean isDeletePhysicalOnZeroReferences() {
            return deletePhysicalOnZeroReferences;
        }

        public void setDeletePhysicalOnZeroReferences(boolean deletePhysicalOnZeroReferences) {
            this.deletePhysicalOnZeroReferences = deletePhysicalOnZeroReferences;
        }

        public boolean isReconciliationEnabled() {
            return reconciliationEnabled;
        }

        public void setReconciliationEnabled(boolean reconciliationEnabled) {
            this.reconciliationEnabled = reconciliationEnabled;
        }
    }
}
