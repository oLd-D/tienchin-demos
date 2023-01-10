package com.guo.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.guo")
public class StorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

}
