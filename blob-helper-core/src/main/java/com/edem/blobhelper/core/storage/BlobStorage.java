package com.edem.blobhelper.core.storage;

public interface BlobStorage {

    StoredBlob put(PutBlobRequest request);

    BlobResource get(String objectKey);

    void delete(String objectKey);

    boolean exists(String objectKey);
}
