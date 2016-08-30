package com.prodyna.pac.auth;

/**
 * Request class to parse the authentication request from web application.
 */
public class AuthenticationRequest {

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthenticationRequest [email=" + email + ", password=***";
	}
}