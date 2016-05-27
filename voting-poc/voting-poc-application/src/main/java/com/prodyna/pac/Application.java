package com.prodyna.pac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// @EnableMBeanExport
// @EnableAspectJAutoProxy

@SpringBootApplication
// @SpringBootApplication is same as adding all 3:
// @Configuration, @EnableAutoConfiguration, @ComponentScan
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Application.class);
        application.setApplicationContextClass(AnnotationConfigApplicationContext.class);
        SpringApplication.run(Application.class, args);
    }

}
