package com.prodyna.pac.security;

public class Roles {

	/**
	 * Identifier of role 'Trainer' from ebiz.
	 */
	public static final String ROLE_USER = "User";

	/**
	 * Identifier of role 'Administrator' from ebiz.
	 */
	public static final String ROLE_ADMINISTRATOR = "Administrator";

	/**
	 * Spring security has role expression for {@code PreAuthorize} of role
	 * 'Trainer' from ebiz.
	 */
	public static final String HAS_ROLE_USER = "hasRole('" + ROLE_USER + "')";

	/**
	 * Spring security has role expression for {@code PreAuthorize} of role
	 * 'Administrator' from ebiz.
	 */
	public static final String HAS_ROLE_ADMINISTRATOR = "hasRole('" + ROLE_ADMINISTRATOR + "')";

}
