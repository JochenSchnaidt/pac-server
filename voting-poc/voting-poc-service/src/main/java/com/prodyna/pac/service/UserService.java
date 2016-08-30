package com.prodyna.pac.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.security.Roles;

/**
 * Service contains all methods which are needed to operate the user database.
 * <p>
 * All modifications of datasets are protected by Spring Security.
 * 
 * @see UserDetailsService
 */
public interface UserService extends UserDetailsService {

	/**
	 * Creates a new user from given {@code UserDTO}
	 * 
	 * @param data
	 *            the given user dataset
	 * @return the new user after persisting
	 */
	public UserDTO create(UserDTO data);

	/**
	 * Updates an existing user from given {@code UserDTO}
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @param data
	 *            the given user
	 * @return the updated user after persisting
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public UserDTO update(UserDTO data);

	/**
	 * Returns an existing user from given email address
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @param email
	 *            the given email address
	 * @return the according user
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public UserDTO get(String email);

	/**
	 * Returns a list of all users in the database
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @return a wrapped list of all users
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public ListWrapperDTO<UserDTO> getAll();

	/**
	 * Deletes an existing user from the database
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @param id
	 *            the given unique identifier
	 * @return the result of the operation
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public OperationResult delete(String id);
}
