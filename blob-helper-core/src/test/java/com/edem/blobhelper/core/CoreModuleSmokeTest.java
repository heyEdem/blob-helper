package com.edem.blobhelper.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CoreModuleSmokeTest {

    @Test
    void coreModuleTestsRunInExpectedPackage() {
        assertEquals("com.edem.blobhelper.core", CoreModuleSmokeTest.class.getPackageName());
    }
}
