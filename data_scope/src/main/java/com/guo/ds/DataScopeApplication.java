package com.guo.ds;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.guo.ds.mapper")
public class DataScopeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataScopeApplication.class, args);
    }

}
