package com.guo.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.guo",exclude = DataSourceAutoConfiguration.class)
public class StorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

}
