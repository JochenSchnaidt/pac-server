package com.prodyna.pac.service.impl;

import static com.prodyna.pac.Constants.JWT_AUTHENTICATION_HEADER;
import static com.prodyna.pac.Constants.TEN_DAYS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.prodyna.pac.auth.UserAuthentication;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.service.TokenAuthenticationService;
import com.prodyna.pac.service.TokenService;

@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TokenService tokenService;

	@Override
	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final UserDTO user = authentication.getDetails();
		user.setExpires(System.currentTimeMillis() + TEN_DAYS);
		response.addHeader(JWT_AUTHENTICATION_HEADER, tokenService.generateToken(user));
	}

	@Override
	public Authentication getAuthentication(HttpServletRequest request) {

		final String token = request.getHeader(JWT_AUTHENTICATION_HEADER);

		if (token != null) {
			final UserDTO user = tokenService.parseUser(token);
			if (user != null) {
				log.info("return parsed user");
				return new UserAuthentication(user);
			}
			log.info("parsed user == null ");
		}

		log.info("token not valid");
		return null;
	}

}
