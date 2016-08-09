package com.prodyna.pac.config;

import static com.prodyna.pac.Constants.STAGE_DEVELOPMENT;
import static com.prodyna.pac.Constants.STAGE_PRODUCTION;
import static com.prodyna.pac.Constants.STAGE_QUALITY_ASSURANCE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prodyna.pac.auth.JwtAuthenticationEntryPoint;
import com.prodyna.pac.auth.JwtAuthenticationTokenFilter;

@Profile(value = { STAGE_DEVELOPMENT, STAGE_QUALITY_ASSURANCE, STAGE_PRODUCTION })
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityProductionConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        //@formatter:off
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler) // Notice the entry point

                .and().authorizeRequests()

                // allow anonymous resource requests
                .antMatchers(HttpMethod.GET, "/system/**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()

                .antMatchers(HttpMethod.GET, "/vote/**").authenticated()
                .antMatchers(HttpMethod.POST, "/vote/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/vote/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/vote/**").authenticated()

                .antMatchers(HttpMethod.GET, "/user/**").authenticated()
                .antMatchers(HttpMethod.POST, "/user/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/user/**").authenticated()

                .antMatchers(HttpMethod.POST, "/voting/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/voting/**").authenticated();
        //@formatter:on

        // Custom JWT based security filter
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity.headers().cacheControl();
    }
}