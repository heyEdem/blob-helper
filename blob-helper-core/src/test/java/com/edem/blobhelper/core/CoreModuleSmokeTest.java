package com.edem.blobhelper.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoreModuleSmokeTest {

    @Test
    void corePackageIsAvailable() {
        assertEquals("com.edem.blobhelper.core", CoreModuleSmokeTest.class.getPackageName());
    }
}
