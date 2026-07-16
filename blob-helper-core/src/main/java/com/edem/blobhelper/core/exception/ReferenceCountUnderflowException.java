package com.edem.blobhelper.core.exception;

public final class ReferenceCountUnderflowException extends BlobHelperException {

    public ReferenceCountUnderflowException(String message) {
        super(message);
    }

    public ReferenceCountUnderflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
