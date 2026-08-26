package com.edem.blobhelper.storage.local;

import com.edem.blobhelper.core.exception.BlobStorageException;
import com.edem.blobhelper.core.exception.BlobValidationException;
import com.edem.blobhelper.core.exception.ContentNotFoundException;
import com.edem.blobhelper.core.storage.BlobResource;
import com.edem.blobhelper.core.storage.BlobStorage;
import com.edem.blobhelper.core.storage.PutBlobRequest;
import com.edem.blobhelper.core.storage.StoredBlob;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

public class LocalBlobStorage implements BlobStorage {

    public static final String PROVIDER = "local";

    private final LocalBlobStorageProperties properties;

    public LocalBlobStorage(LocalBlobStorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public StoredBlob put(PutBlobRequest request) {
        Path target = resolve(request.objectKey());
        try {
            Files.createDirectories(target.getParent());
            try (InputStream content = request.content()) {
                Files.copy(content, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException failure) {
            throw new BlobStorageException("Failed to store object: " + request.objectKey(), failure);
        }
        return new StoredBlob(
                request.objectKey(),
                PROVIDER,
                root().toString(),
                request.sizeBytes(),
                request.contentType(),
                null,
                Instant.now()
        );
    }

    @Override
    public BlobResource get(String objectKey) {
        Path file = requireExisting(objectKey);
        try {
            return new BlobResource(
                    objectKey,
                    Files.newInputStream(file),
                    Files.size(file),
                    null,
                    null
            );
        } catch (IOException failure) {
            throw new BlobStorageException("Failed to read object: " + objectKey, failure);
        }
    }

    @Override
    public void delete(String objectKey) {
        Path file = resolve(objectKey);
        try {
            Files.deleteIfExists(file);
        } catch (IOException failure) {
            throw new BlobStorageException("Failed to delete object: " + objectKey, failure);
        }
    }

    @Override
    public boolean exists(String objectKey) {
        validateKey(objectKey);
        return Files.exists(resolve(objectKey));
    }

    private Path requireExisting(String objectKey) {
        Path file = resolve(objectKey);
        if (!Files.exists(file)) {
            throw new ContentNotFoundException("Local object not found: " + objectKey);
        }
        return file;
    }

    private Path resolve(String objectKey) {
        validateKey(objectKey);
        return root().resolve(objectKey);
    }

    private void validateKey(String objectKey) {
        if (objectKey == null || objectKey.isBlank()) {
            throw new BlobValidationException("objectKey must not be blank");
        }
    }

    private Path root() {
        return properties.getRootDirectory().toAbsolutePath().normalize();
    }
}
