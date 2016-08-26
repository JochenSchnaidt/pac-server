package com.prodyna.pac.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.service.TokenAuthenticationService;
import com.prodyna.pac.service.UserService;
import com.prodyna.pac.validation.UserValidationService;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private UserService service;
	private UserValidationService validationService;
	private TokenAuthenticationService tokenAuthenticationService;

	public StatelessLoginFilter(String urlMapping, AuthenticationManager authManager, UserService service, UserValidationService validationService,
	        TokenAuthenticationService tokenAuthenticationService) {
		super(new AntPathRequestMatcher(urlMapping));
		this.setAuthenticationManager(authManager);
		this.service = service;
		this.validationService = validationService;
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

		final UserDTO requestUser = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);

		log.debug("requestUser: " + requestUser.toString());

		validationService.validateEmail(requestUser.getEmail());
		validationService.validatePassword(requestUser.getPassword());

		final UsernamePasswordAuthenticationToken temp = new UsernamePasswordAuthenticationToken(requestUser.getEmail(), requestUser.getPassword());
		log.debug("UsernamePasswordAuthenticationToken created");

		Authentication authentication = getAuthenticationManager().authenticate(temp);
		log.info("Authentication created: " +authentication.toString());

		return authentication;

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		
		log.info("Authentication from parameter: " +authentication.toString());
		
		
		// Lookup the complete User object from the database and create an Authentication for it
		final UserDTO authenticatedUser = service.get(authentication.getName());

		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthentication(response, userAuthentication);

		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
		log.debug("user added to SecurityContextHolder");
	}
}