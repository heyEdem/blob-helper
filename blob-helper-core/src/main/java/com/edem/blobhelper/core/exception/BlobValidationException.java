package com.edem.blobhelper.core.exception;

public final class BlobValidationException extends BlobHelperException {

    public BlobValidationException(String message) {
        super(message);
    }

    public BlobValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
