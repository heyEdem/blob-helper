package com.edem.blobhelper.reconcile;

import com.edem.blobhelper.jpa.AssetContent;
import com.edem.blobhelper.jpa.AssetContentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ReconciliationServiceTest {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private AssetContentRepository repository;

    @BeforeAll
    static void createEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("blob-helper-starter-test");
    }

    @AfterAll
    static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    @BeforeEach
    void createEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
        repository = new AssetContentRepository(entityManager);
    }

    @AfterEach
    void closeEntityManager() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @Test
    void reportsReferenceCountMismatchWithoutMutatingStoredCounts() {
        AssetContent expectedThree = persistContent("a".repeat(64));
        AssetContent expectedZero = persistContent("b".repeat(64));
        entityManager.clear();

        ReconciliationReport report = new ReconciliationService(repository).reconcile(
                () -> Map.of(expectedThree.getId(), 3L)
        );

        assertThat(report.checkedContentCount()).isEqualTo(2);
        assertThat(report.mismatches())
                .extracting(ReconciliationMismatch::assetContentId,
                        ReconciliationMismatch::expectedReferenceCount,
                        ReconciliationMismatch::actualReferenceCount)
                .containsExactlyInAnyOrder(
                        org.assertj.core.groups.Tuple.tuple(expectedThree.getId(), 3L, 1L),
                        org.assertj.core.groups.Tuple.tuple(expectedZero.getId(), 0L, 1L)
                );
        assertThat(entityManager.find(AssetContent.class, expectedThree.getId()).getRefCount()).isEqualTo(1L);
        assertThat(entityManager.find(AssetContent.class, expectedZero.getId()).getRefCount()).isEqualTo(1L);
    }

    private AssetContent persistContent(String hash) {
        AssetContent content = new AssetContent(
                "SHA-256", hash, 42L, "uploads/" + hash, "local", "test-bucket", null, "bin"
        );
        entityManager.getTransaction().begin();
        entityManager.persist(content);
        entityManager.getTransaction().commit();
        return content;
    }
}
