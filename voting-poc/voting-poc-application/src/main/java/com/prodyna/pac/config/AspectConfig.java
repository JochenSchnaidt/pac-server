package com.prodyna.pac.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration to enable AspectJ on the project.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.prodyna.pac.monitoring")
public class AspectConfig {
}