package com.prodyna.pac.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.prodyna.pac.service.TokenAuthenticationService;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) req);

		if (null != authentication) {
			log.info("Authentication created: " + authentication.toString());
			log.info("Authentication isAuthenticated: " + authentication.isAuthenticated());
			log.info("Authentication details: " + authentication.getDetails().toString());
			log.info("Authentication getAuthorities: " + authentication.getAuthorities().toString());
		} else {
			log.info("Authentication is null");
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.debug("user added to SecurityContextHolder");

		chain.doFilter(req, res); // always continue
	}
}