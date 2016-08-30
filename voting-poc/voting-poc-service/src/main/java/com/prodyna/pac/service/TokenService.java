package com.prodyna.pac.service;

import com.prodyna.pac.dto.UserDTO;

/**
 * Service provides the processing of JWT token.
 */
public interface TokenService {

	/**
	 * Creates a JWT token out of a given {@code UserDTO}
	 * 
	 * @param user
	 *            the given user
	 * @return the encrypted token
	 */
	public String generateToken(UserDTO user);

	/**
	 * Creates a {@code UserDTO} from a given token
	 * 
	 * @param token
	 *            the encrypted information
	 * @return the user parsed from the token
	 */
	public UserDTO parseUser(String token);

}
