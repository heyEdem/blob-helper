package com.edem.blobhelper.core.model;

import com.edem.blobhelper.core.exception.BlobValidationException;
import com.edem.blobhelper.core.hash.ContentHash;

import java.util.UUID;

public record BlobReference(
        UUID assetContentId,
        ContentHash contentHash,
        String contentType,
        String storageProvider,
        String objectKey,
        boolean duplicate
) {

    public BlobReference {
        if (assetContentId == null) {
            throw new BlobValidationException("assetContentId must not be null");
        }
        if (contentHash == null) {
            throw new BlobValidationException("contentHash must not be null");
        }
        if (storageProvider == null || storageProvider.isBlank()) {
            throw new BlobValidationException("storageProvider must not be blank");
        }
        if (objectKey == null || objectKey.isBlank()) {
            throw new BlobValidationException("objectKey must not be blank");
        }
    }
}
