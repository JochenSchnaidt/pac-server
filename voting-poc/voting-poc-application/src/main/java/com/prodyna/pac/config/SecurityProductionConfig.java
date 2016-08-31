package com.prodyna.pac.config;

import static com.prodyna.pac.Constants.STAGE_DEVELOPMENT;
import static com.prodyna.pac.Constants.STAGE_PRODUCTION;
import static com.prodyna.pac.Constants.STAGE_QUALITY_ASSURANCE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prodyna.pac.auth.SimpleCORSFilter;
import com.prodyna.pac.auth.StatelessAuthenticationFilter;
import com.prodyna.pac.auth.StatelessLoginFilter;
import com.prodyna.pac.service.TokenAuthenticationService;
import com.prodyna.pac.service.UserService;
import com.prodyna.pac.validation.ValidationService;

/**
 * Security Configuration of the application, handled by Spring Security.
 */
@Profile(value = { STAGE_DEVELOPMENT, STAGE_QUALITY_ASSURANCE, STAGE_PRODUCTION })
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityProductionConfig extends WebSecurityConfigurerAdapter {

	// public SecurityProductionConfig() {
	// super(true);
	// }

	@Autowired
	private UserService userService;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		http.exceptionHandling().and().anonymous().and().servletApi().and().headers().cacheControl().disable().and()

		        .authorizeRequests()

		        // allow anonymous resource requests
		        // .antMatchers("/").permitAll().antMatchers("/favicon.ico").permitAll().antMatchers("/resources/**").permitAll()

		        // allow anonymous POSTs to login
		        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		        .antMatchers(HttpMethod.POST, "/auth/").permitAll()

		        // allow anonymous GETs to API
		        // .antMatchers(HttpMethod.GET, "/api/**").permitAll()

		        // defined Admin only API area
		        // .antMatchers("/admin/**").hasRole("ADMIN")

		        // all other request need to be authenticated
		        // .anyRequest().hasRole("USER").and()
		        .anyRequest().authenticated()

		        // custom CORS filter to deal with all CORS purposes
		        .and().addFilterBefore(new SimpleCORSFilter(), ChannelProcessingFilter.class)

		        // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
		        .addFilterBefore(new StatelessLoginFilter("/auth/", authenticationManager(), userService, validationService, tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)

		        // custom Token based authentication based on the header previously given to the client
		        .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
		//@formatter:on
	}

}