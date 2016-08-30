package com.prodyna.pac.security;

/**
 * Class defines the roles users can have in the application. Also a {@code hasRole} is implemented for convenience, when it comes to working with spring security.
 */
public class Roles {

	/**
	 * Identifier of role {@code User} which is standard in the application.
	 */
	public static final String ROLE_USER = "User";

	/**
	 * Identifier of role {@code Administrator}.
	 */
	public static final String ROLE_ADMINISTRATOR = "Administrator";

	/**
	 * Spring security has role expression for {@code PreAuthorize} of role {@code User}.
	 */
	public static final String HAS_ROLE_USER = "hasRole('" + ROLE_USER + "')";

	/**
	 * Spring security has role expression for {@code PreAuthorize} of role {@code Administrator}.
	 */
	public static final String HAS_ROLE_ADMINISTRATOR = "hasRole('" + ROLE_ADMINISTRATOR + "')";

}
