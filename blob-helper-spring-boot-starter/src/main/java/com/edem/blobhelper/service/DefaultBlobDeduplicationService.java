package com.edem.blobhelper.service;

import com.edem.blobhelper.core.exception.ContentNotFoundException;
import com.edem.blobhelper.core.model.BlobReference;
import com.edem.blobhelper.core.model.StoreBlobCommand;
import com.edem.blobhelper.core.storage.BlobResource;
import com.edem.blobhelper.core.storage.BlobStorage;
import com.edem.blobhelper.jpa.AssetContentRepository;
import com.edem.blobhelper.jpa.ReferenceCountService;

import java.util.Objects;
import java.util.UUID;

/**
 * Default service facade over metadata and provider-neutral storage
 * collaborators.
 *
 * <p>The caller owns the persistence transaction. Upload orchestration is
 * added in the subsequent service tasks; this contract task wires the
 * reference and retrieval operations that are already supported by the JPA
 * module.</p>
 */
public final class DefaultBlobDeduplicationService implements BlobDeduplicationService {

    private final AssetContentRepository repository;
    private final ReferenceCountService referenceCountService;
    private final BlobStorage storage;

    public DefaultBlobDeduplicationService(
            AssetContentRepository repository,
            ReferenceCountService referenceCountService,
            BlobStorage storage
    ) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.referenceCountService = Objects.requireNonNull(
                referenceCountService,
                "referenceCountService must not be null"
        );
        this.storage = Objects.requireNonNull(storage, "storage must not be null");
    }

    @Override
    public BlobReference store(StoreBlobCommand command) {
        throw new UnsupportedOperationException(
                "store orchestration is not available until the upload service is configured"
        );
    }

    @Override
    public void retain(UUID assetContentId) {
        referenceCountService.retain(assetContentId);
    }

    @Override
    public void release(UUID assetContentId) {
        referenceCountService.release(assetContentId);
    }

    @Override
    public BlobResource get(UUID assetContentId) {
        Objects.requireNonNull(assetContentId, "assetContentId must not be null");
        return repository.findByIdForUpdate(assetContentId)
                .map(content -> storage.get(content.getObjectKey()))
                .orElseThrow(() -> new ContentNotFoundException(
                        "Asset content not found: " + assetContentId
                ));
    }
}
