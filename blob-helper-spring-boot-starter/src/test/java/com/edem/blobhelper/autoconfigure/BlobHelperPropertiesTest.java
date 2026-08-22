package com.edem.blobhelper.autoconfigure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.util.unit.DataSize;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlobHelperPropertiesTest {

    @Test
    void bindsStorageDeduplicationAndCleanupProperties() {
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(Map.of(
                "blob-helper.storage.provider", "s3",
                "blob-helper.storage.key-prefix", "uploads",
                "blob-helper.deduplication.hash-algorithm", "SHA-256",
                "blob-helper.deduplication.max-upload-size", "64MB",
                "blob-helper.deduplication.strict-content-type-validation", "true",
                "blob-helper.cleanup.delete-physical-on-zero-references", "false",
                "blob-helper.cleanup.reconciliation-enabled", "true"
        ));

        BlobHelperProperties properties = new Binder(source)
                .bind("blob-helper", Bindable.of(BlobHelperProperties.class))
                .orElseThrow(AssertionError::new);

        assertEquals("s3", properties.getStorage().getProvider());
        assertEquals("uploads", properties.getStorage().getKeyPrefix());
        assertEquals("SHA-256", properties.getDeduplication().getHashAlgorithm());
        assertEquals(DataSize.ofMegabytes(64), properties.getDeduplication().getMaxUploadSize());
        assertTrue(properties.getDeduplication().isStrictContentTypeValidation());
        assertFalse(properties.getCleanup().isDeletePhysicalOnZeroReferences());
        assertTrue(properties.getCleanup().isReconciliationEnabled());
    }

    @Test
    void disablesReconciliationByDefault() {
        BlobHelperProperties properties = new BlobHelperProperties();

        assertFalse(properties.getCleanup().isReconciliationEnabled());
    }
}
