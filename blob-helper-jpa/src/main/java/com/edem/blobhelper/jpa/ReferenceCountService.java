package com.edem.blobhelper.jpa;

import com.edem.blobhelper.core.exception.ContentNotFoundException;
import com.edem.blobhelper.core.exception.ReferenceCountUnderflowException;

import java.util.Objects;
import java.util.UUID;

/**
 * Mutates content reference counts while holding a pessimistic row lock.
 *
 * <p>The caller owns the transaction. The transaction must remain active until
 * the caller commits or rolls back the operation.</p>
 */
public final class ReferenceCountService {

    private final AssetContentRepository repository;

    public ReferenceCountService(AssetContentRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
    }

    public void retain(UUID assetContentId) {
        AssetContent content = findForUpdate(assetContentId);
        content.incrementRefCount();
    }

    public void release(UUID assetContentId) {
        AssetContent content = findForUpdate(assetContentId);
        if (content.getRefCount() <= 0L) {
            throw new ReferenceCountUnderflowException(
                    "Reference count is already zero for asset content " + assetContentId
            );
        }
        content.decrementRefCount();
    }

    private AssetContent findForUpdate(UUID assetContentId) {
        Objects.requireNonNull(assetContentId, "assetContentId must not be null");
        return repository.findByIdForUpdate(assetContentId)
                .orElseThrow(() -> new ContentNotFoundException(
                        "Asset content not found: " + assetContentId
                ));
    }
}
