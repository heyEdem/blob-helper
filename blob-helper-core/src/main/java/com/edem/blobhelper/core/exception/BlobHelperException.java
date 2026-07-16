package com.edem.blobhelper.core.exception;

public class BlobHelperException extends RuntimeException {

    public BlobHelperException(String message) {
        super(message);
    }

    public BlobHelperException(String message, Throwable cause) {
        super(message, cause);
    }
}
