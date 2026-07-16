package com.edem.blobhelper.core.key;

import com.edem.blobhelper.core.hash.ContentHash;

public interface ObjectKeyStrategy {

    String generateKey(ContentHash contentHash);
}
