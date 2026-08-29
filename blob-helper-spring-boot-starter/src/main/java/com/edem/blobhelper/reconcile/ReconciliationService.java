package com.edem.blobhelper.reconcile;

import com.edem.blobhelper.core.exception.BlobValidationException;
import com.edem.blobhelper.jpa.AssetContent;
import com.edem.blobhelper.jpa.AssetContentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Reports drift between application-owned logical references and stored
 * physical-content reference counts.
 *
 * <p>This service is read-only. Repair behavior belongs to a separate,
 * explicitly enabled operation.</p>
 */
public final class ReconciliationService {

    private final AssetContentRepository repository;

    public ReconciliationService(AssetContentRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
    }

    public ReconciliationReport reconcile(LogicalReferenceCountSource source) {
        Objects.requireNonNull(source, "source must not be null");
        Map<UUID, Long> expectedCounts = Objects.requireNonNull(
                source.countLogicalReferences(),
                "logical reference counts must not be null"
        );
        List<ReconciliationMismatch> mismatches = new ArrayList<>();
        List<AssetContent> contents = repository.findAll();

        for (AssetContent content : contents) {
            long expected = expectedCount(expectedCounts, content.getId());
            long actual = content.getRefCount();
            if (expected != actual) {
                mismatches.add(new ReconciliationMismatch(content.getId(), expected, actual));
            }
        }

        return new ReconciliationReport(contents.size(), mismatches);
    }

    private static long expectedCount(Map<UUID, Long> expectedCounts, UUID assetContentId) {
        Long expected = expectedCounts.getOrDefault(assetContentId, 0L);
        if (expected == null || expected < 0L) {
            throw new BlobValidationException(
                    "Logical reference count must not be null or negative for asset content " + assetContentId
            );
        }
        return expected;
    }
}
