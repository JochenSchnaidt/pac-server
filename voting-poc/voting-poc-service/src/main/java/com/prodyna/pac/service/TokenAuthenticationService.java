package com.prodyna.pac.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import com.prodyna.pac.auth.UserAuthentication;

/**
 * Service provides the authentication of a user in Spring Security.
 */
public interface TokenAuthenticationService {

	/**
	 * Extracts the authentication token from a request and returns as Spring {@code Authentication}
	 * 
	 * @param request
	 *            the incoming request from the web application
	 * @return the already granted authentication
	 */
	public Authentication getAuthentication(HttpServletRequest request);

	/**
	 * Adds the already granted authentication to the response, so that the following filter can access it.
	 * 
	 * @param response
	 *            the outgoing response for the web application
	 * @param userAuthentication
	 *            the already granted authentication
	 */
	public void addAuthentication(HttpServletResponse response, UserAuthentication userAuthentication);

}
