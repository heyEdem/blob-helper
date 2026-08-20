package com.edem.blobhelper.jpa;

import com.edem.blobhelper.core.exception.ContentNotFoundException;
import com.edem.blobhelper.core.exception.ReferenceCountUnderflowException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReferenceCountServiceTest {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;
    private ReferenceCountService service;

    @BeforeAll
    static void createEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("blob-helper-jpa-test");
    }

    @AfterAll
    static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    @BeforeEach
    void createEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
        service = new ReferenceCountService(new AssetContentRepository(entityManager));
    }

    @AfterEach
    void closeEntityManager() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @Test
    void retainIncrementsReferenceCountOnce() {
        AssetContent content = persistContent();

        entityManager.clear();
        entityManager.getTransaction().begin();

        service.retain(content.getId());

        entityManager.getTransaction().commit();
        entityManager.clear();

        assertEquals(2L, entityManager.find(AssetContent.class, content.getId()).getRefCount());
    }

    @Test
    void releaseDecrementsReferenceCountOnce() {
        AssetContent content = persistContent();

        entityManager.clear();
        entityManager.getTransaction().begin();
        service.retain(content.getId());
        entityManager.getTransaction().commit();

        entityManager.clear();
        entityManager.getTransaction().begin();
        service.release(content.getId());
        entityManager.getTransaction().commit();
        entityManager.clear();

        assertEquals(1L, entityManager.find(AssetContent.class, content.getId()).getRefCount());
    }

    @Test
    void releaseRejectsReferenceCountUnderflow() {
        AssetContent content = persistContent();

        entityManager.clear();
        entityManager.getTransaction().begin();
        service.release(content.getId());

        assertThrows(
                ReferenceCountUnderflowException.class,
                () -> service.release(content.getId())
        );

        entityManager.getTransaction().commit();
        entityManager.clear();

        assertEquals(0L, entityManager.find(AssetContent.class, content.getId()).getRefCount());
    }

    @Test
    void retainFailsWhenContentDoesNotExist() {
        entityManager.getTransaction().begin();

        assertThrows(
                ContentNotFoundException.class,
                () -> service.retain(UUID.randomUUID())
        );

        entityManager.getTransaction().rollback();
    }

    private AssetContent persistContent() {
        String contentHash = UUID.randomUUID().toString().replace("-", "");
        AssetContent content = new AssetContent(
                "SHA-256",
                contentHash,
                42L,
                "uploads/" + contentHash,
                "local",
                "test-bucket",
                "application/octet-stream",
                "bin"
        );

        entityManager.getTransaction().begin();
        entityManager.persist(content);
        entityManager.getTransaction().commit();
        return content;
    }
}
