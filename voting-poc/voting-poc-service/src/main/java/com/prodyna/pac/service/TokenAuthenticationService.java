package com.prodyna.pac.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import com.prodyna.pac.auth.UserAuthentication;

public interface TokenAuthenticationService {

	public Authentication getAuthentication(HttpServletRequest req);

	public void addAuthentication(HttpServletResponse response, UserAuthentication userAuthentication);

}
