package com.edem.blobhelper.storage.local;

import com.edem.blobhelper.core.exception.BlobValidationException;
import com.edem.blobhelper.core.exception.ContentNotFoundException;
import com.edem.blobhelper.core.storage.BlobResource;
import com.edem.blobhelper.core.storage.PutBlobRequest;
import com.edem.blobhelper.core.storage.StoredBlob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalBlobStorageIntegrationTest {

    @TempDir
    Path tempRoot;

    private final LocalBlobStorageProperties properties = new LocalBlobStorageProperties();

    @Test
    void putGetDeleteRoundTrip() throws IOException {
        properties.setRootDirectory(tempRoot);
        LocalBlobStorage storage = new LocalBlobStorage(properties);
        byte[] payload = "round-trip-bytes".getBytes(StandardCharsets.UTF_8);

        StoredBlob stored = storage.put(new PutBlobRequest(
                "sha256/ab/abcdef",
                new ByteArrayInputStream(payload),
                payload.length,
                "text/plain",
                "notes.txt",
                Map.of("origin", "test")
        ));

        assertEquals("sha256/ab/abcdef", stored.objectKey());
        assertEquals(LocalBlobStorage.PROVIDER, stored.provider());
        assertEquals(tempRoot.toString(), stored.bucketOrContainer());
        assertEquals(payload.length, stored.sizeBytes());
        assertEquals("text/plain", stored.contentType());
        assertNotNull(stored.createdAt());
        assertTrue(Files.exists(tempRoot.resolve("sha256/ab/abcdef")));

        assertTrue(storage.exists("sha256/ab/abcdef"));
        try (BlobResource resource = storage.get("sha256/ab/abcdef")) {
            assertEquals("sha256/ab/abcdef", resource.objectKey());
            assertEquals(payload.length, resource.sizeBytes());
            assertArrayEquals(payload, readAll(resource.content()));
        }

        storage.delete("sha256/ab/abcdef");

        assertFalse(storage.exists("sha256/ab/abcdef"));
        assertFalse(Files.exists(tempRoot.resolve("sha256/ab/abcdef")));
        assertThrows(ContentNotFoundException.class, () -> storage.get("sha256/ab/abcdef"));
    }

    @Test
    void missingObjectIsAlreadyDeletedByDefault() {
        properties.setRootDirectory(tempRoot);
        LocalBlobStorage storage = new LocalBlobStorage(properties);

        assertDoesNotThrow(() -> storage.delete("missing/object/key"));        assertFalse(storage.exists("missing/object/key"));
    }

    @Test
    void existsIsFalseBeforeAnyWrite() {
        properties.setRootDirectory(tempRoot);
        LocalBlobStorage storage = new LocalBlobStorage(properties);

        assertFalse(storage.exists("never/written"));
    }

    @Test
    void putOverwritesExistingObject() throws IOException {
        properties.setRootDirectory(tempRoot);
        LocalBlobStorage storage = new LocalBlobStorage(properties);

        storage.put(request("overwrite/key", "first".getBytes(StandardCharsets.UTF_8)));
        storage.put(request("overwrite/key", "second".getBytes(StandardCharsets.UTF_8)));

        try (BlobResource resource = storage.get("overwrite/key")) {
            assertArrayEquals("second".getBytes(StandardCharsets.UTF_8), readAll(resource.content()));
        }
    }

    @Test
    void rejectsBlankObjectKeys() {
        properties.setRootDirectory(tempRoot);
        LocalBlobStorage storage = new LocalBlobStorage(properties);

        assertThrows(BlobValidationException.class, () -> storage.get(" "));
        assertThrows(BlobValidationException.class, () -> storage.delete(null));
        assertThrows(BlobValidationException.class, () -> storage.exists(" "));
    }

    private PutBlobRequest request(String objectKey, byte[] payload) {
        return new PutBlobRequest(
                objectKey,
                new ByteArrayInputStream(payload),
                payload.length,
                "application/octet-stream",
                null,
                null
        );
    }

    private static byte[] readAll(InputStream stream) {
        try (stream) {
            return stream.readAllBytes();
        } catch (IOException failure) {
            throw new IllegalStateException(failure);
        }
    }
}
