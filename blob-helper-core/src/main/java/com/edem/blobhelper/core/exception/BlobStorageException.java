package com.edem.blobhelper.core.exception;

public final class BlobStorageException extends BlobHelperException {

    public BlobStorageException(String message) {
        super(message);
    }

    public BlobStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
