package com.edem.blobhelper.service;

import com.edem.blobhelper.core.exception.ContentNotFoundException;
import com.edem.blobhelper.core.hash.ContentHash;
import com.edem.blobhelper.core.hash.ContentHasher;
import com.edem.blobhelper.core.key.ObjectKeyStrategy;
import com.edem.blobhelper.core.model.BlobReference;
import com.edem.blobhelper.core.model.StoreBlobCommand;
import com.edem.blobhelper.core.storage.BlobResource;
import com.edem.blobhelper.core.storage.BlobStorage;
import com.edem.blobhelper.core.storage.PutBlobRequest;
import com.edem.blobhelper.core.storage.StoredBlob;
import com.edem.blobhelper.jpa.AssetContent;
import com.edem.blobhelper.jpa.AssetContentMutationService;
import com.edem.blobhelper.jpa.AssetContentRepository;
import com.edem.blobhelper.jpa.ReferenceCountService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * Default service facade over metadata and provider-neutral storage
 * collaborators.
 *
 * <p>The caller owns the persistence transaction. Uploads are buffered once
 * so the same bytes can be hashed and replayed to provider-neutral storage.</p>
 */
public final class DefaultBlobDeduplicationService implements BlobDeduplicationService {

    private final AssetContentRepository repository;
    private final ReferenceCountService referenceCountService;
    private final AssetContentMutationService mutationService;
    private final BlobStorage storage;
    private final ContentHasher contentHasher;
    private final ObjectKeyStrategy objectKeyStrategy;

    public DefaultBlobDeduplicationService(
            AssetContentRepository repository,
            ReferenceCountService referenceCountService,
            AssetContentMutationService mutationService,
            BlobStorage storage,
            ContentHasher contentHasher,
            ObjectKeyStrategy objectKeyStrategy
    ) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.referenceCountService = Objects.requireNonNull(
                referenceCountService,
                "referenceCountService must not be null"
        );
        this.mutationService = Objects.requireNonNull(
                mutationService,
                "mutationService must not be null"
        );
        this.storage = Objects.requireNonNull(storage, "storage must not be null");
        this.contentHasher = Objects.requireNonNull(contentHasher, "contentHasher must not be null");
        this.objectKeyStrategy = Objects.requireNonNull(
                objectKeyStrategy,
                "objectKeyStrategy must not be null"
        );
    }

    @Override
    public BlobReference store(StoreBlobCommand command) {
        Objects.requireNonNull(command, "command must not be null");

        byte[] bytes = readAll(command.content());
        ContentHash contentHash = hash(bytes);
        return repository.findByIdentity(
                        contentHash.algorithm(),
                        contentHash.hash(),
                        contentHash.sizeBytes()
                )
                .map(this::retainDuplicate)
                .orElseGet(() -> storeNewContent(command, contentHash, bytes));
    }

    private BlobReference retainDuplicate(AssetContent existing) {
        referenceCountService.retain(existing.getId());
        return new BlobReference(
                existing.getId(),
                new ContentHash(
                        existing.getHashAlgorithm(),
                        existing.getContentHash(),
                        existing.getSizeBytes()
                ),
                existing.getContentType(),
                existing.getStorageProvider(),
                existing.getObjectKey(),
                true
        );
    }

    private BlobReference storeNewContent(
            StoreBlobCommand command,
            ContentHash contentHash,
            byte[] bytes
    ) {
        String objectKey = objectKeyStrategy.generateKey(contentHash);
        StoredBlob stored = storage.put(new PutBlobRequest(
                objectKey,
                new ByteArrayInputStream(bytes),
                contentHash.sizeBytes(),
                command.contentType(),
                command.filename(),
                command.metadata()
        ));

        AssetContent candidate = new AssetContent(
                contentHash.algorithm(),
                contentHash.hash(),
                contentHash.sizeBytes(),
                stored.objectKey(),
                stored.provider(),
                stored.bucketOrContainer(),
                command.contentType(),
                extractExtension(command.filename())
        );

        AssetContent persisted = mutationService.createOrRetain(candidate);
        return new BlobReference(
                persisted.getId(),
                contentHash,
                command.contentType(),
                persisted.getStorageProvider(),
                persisted.getObjectKey(),
                persisted != candidate
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

    private ContentHash hash(byte[] bytes) {
        try {
            return contentHasher.hash(new ByteArrayInputStream(bytes));
        } catch (IOException failure) {
            throw new IllegalStateException("Content hashing failed", failure);
        }
    }

    private static byte[] readAll(InputStream stream) {
        Objects.requireNonNull(stream, "content must not be null");
        try (stream) {
            return stream.readAllBytes();
        } catch (IOException failure) {
            throw new IllegalStateException("Reading upload content failed", failure);
        }
    }

    private static String extractExtension(String filename) {
        if (filename == null || filename.isBlank()) {
            return null;
        }
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == filename.length() - 1) {
            return null;
        }
        String extension = filename.substring(dotIndex + 1).toLowerCase(Locale.ROOT);
        return extension.length() > 32 ? null : extension;
    }
}
