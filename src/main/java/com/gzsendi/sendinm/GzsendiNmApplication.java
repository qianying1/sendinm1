package com.gzsendi.sendinm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gzsendi.sendinm.mapper")
public class GzsendiNmApplication {

    public static void main(String[] args) {
        System.out.println("GzsendiNmApplication used!!!!!!");
        SpringApplication.run(com.gzsendi.sendinm.GzsendiNmApplication.class, args);
    }
}
