package com.gzsendi.qhb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gzsendi.qhb.mapper")
public class GzsendiNmApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.gzsendi.qhb.GzsendiNmApplication.class, args);
    }
}
