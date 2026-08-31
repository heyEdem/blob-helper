package com.edem.blobhelper.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlobHelperDashboardApplication {

    public static final String DEFAULT_ADDRESS = "127.0.0.1";
    public static final int DEFAULT_PORT = 9090;

    public static void main(String[] args) {
        SpringApplication.run(BlobHelperDashboardApplication.class, args);
    }
}
