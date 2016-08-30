package com.prodyna.pac.auth;

import java.io.IOException;
import java.util.Enumeration;

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

/**
 * Custom filter to handle JWT token provided by custom header field.
 * <p>
 * Get further information <a href="https://en.wikipedia.org/wiki/Cross-origin_resource_sharing">here</a>.
 *
 * @see GenericFilterBean
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;

		if (log.isDebugEnabled()) {
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = headerNames.nextElement();
				String value = request.getHeader(key);
				log.debug("\t" + key + " - " + value);
			}
		}

		Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) req);

		if (log.isDebugEnabled()) {
			if (null != authentication) {
				log.debug("Authentication created: " + authentication.toString());
				log.debug("Authentication isAuthenticated: " + authentication.isAuthenticated());
				log.debug("Authentication details: " + authentication.getDetails().toString());
				log.debug("Authentication getAuthorities: " + authentication.getAuthorities().toString());
			} else {
				log.debug("Authentication is null");
			}
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.debug("user added to SecurityContextHolder");

		chain.doFilter(req, response); // always continue
	}
}