package com.prodyna.pac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.prodyna.pac.monitoring.BusinessServicesAspect;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.prodyna.pac.monitoring")
public class AspectConfig {

    @Bean
    public BusinessServicesAspect businessServicesAspect() {
        return new BusinessServicesAspect();

    }
}