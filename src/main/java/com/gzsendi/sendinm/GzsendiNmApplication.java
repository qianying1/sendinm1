package com.gzsendi.sendinm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
@SpringBootConfiguration
//@EnableCaching
//@MapperScan("com.gzsendi.sendinm.mapper")
public class GzsendiNmApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("GzsendiNmApplication used!!!!!!");
        /*Properties properties = new Properties();
        InputStream in = GzsendiNmApplication.class.getClassLoader().getResourceAsStream("application1.properties");
        System.out.println("line: " + in.available());
        properties.load(in);
        SpringApplication app = new SpringApplication(GzsendiNmApplication.class);
        app.setDefaultProperties(properties);
        app.run(args);*/
        SpringApplication.run(GzsendiNmApplication.class, args);
    }

    /*private void test(){
    Properties properties = new Properties();
        InputStream in = GzsendiNmApplication.class.getClassLoader().getResourceAsStream("application1.properties");
        System.out.println("line: " + in.available());
        properties.load(in);
        SpringApplication app = new SpringApplication(GzsendiNmApplication.class);
        app.setDefaultProperties(properties);
        app.run(args);
    }*/
}
