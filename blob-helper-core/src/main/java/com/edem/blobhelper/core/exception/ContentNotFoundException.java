package com.edem.blobhelper.core.exception;

public final class ContentNotFoundException extends BlobHelperException {

    public ContentNotFoundException(String message) {
        super(message);
    }

    public ContentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
