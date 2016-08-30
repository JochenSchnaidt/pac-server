package com.prodyna.pac.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.prodyna.pac.dto.UserDTO;

/**
 * Wrapper class for {@link UserDTO} to implement spring's {@link Authentication}.
 */
public class UserAuthentication implements Authentication {

	private static final long serialVersionUID = 1L;

	private final UserDTO user;
	private boolean authenticated = false;

	/**
	 * Instantiates a new UserAuthentication from given user
	 * 
	 * @param user
	 *            the given user to authenticate
	 */
	public UserAuthentication(UserDTO user) {
		this.user = user;
		this.authenticated = true;
	}

	@Override
	public String getName() {
		return user.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public UserDTO getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user.getUsername();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
}