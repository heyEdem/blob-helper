package com.edem.blobhelper.core.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

class DomainExceptionTest {

    @Test
    void domainExceptionsShareBaseTypeAndRetainCause() {
        RuntimeException cause = new RuntimeException("provider failed");
        BlobHelperException exception = new BlobStorageException("put failed", cause);

        assertEquals("put failed", exception.getMessage());
        assertSame(cause, exception.getCause());
        assertInstanceOf(BlobHelperException.class, new BlobValidationException("invalid"));
        assertInstanceOf(BlobHelperException.class, new BlobHashingException("hash failed", cause));
        assertInstanceOf(BlobHelperException.class, new ContentNotFoundException("missing"));
        assertInstanceOf(BlobHelperException.class, new ReferenceCountUnderflowException("underflow"));
    }
}
