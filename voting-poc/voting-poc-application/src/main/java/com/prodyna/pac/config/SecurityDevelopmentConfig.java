package com.prodyna.pac.config;

import static com.prodyna.pac.Constants.STAGE_DEVELOPMENT_NO_SECURITY;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile({ STAGE_DEVELOPMENT_NO_SECURITY })
@Configuration
@EnableWebSecurity
public class SecurityDevelopmentConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().disable();
    }
}