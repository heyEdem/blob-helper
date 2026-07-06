package com.edem.blobhelper.core.hash;

/**
 * Represents a content hash.
 * @param algorithm
 * @param hash
 * @param sizeBytes
 */
public record ContentHash(String algorithm, String hash, long sizeBytes) {
}
