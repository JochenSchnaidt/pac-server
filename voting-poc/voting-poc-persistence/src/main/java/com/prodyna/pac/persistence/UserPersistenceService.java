package com.prodyna.pac.persistence;

import java.util.List;

import com.prodyna.pac.dto.UserDTO;

/**
 * Service contains all methods which are needed to operate the user database.
 */
public interface UserPersistenceService {

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
	 *
	 * @param data
	 *            the given user
	 * @return the updated user after persisting
	 */
	public UserDTO update(UserDTO data);

	/**
	 * Returns an existing user from given email address
	 *
	 * @param email
	 *            the given email address
	 * @return the according user
	 */
	public UserDTO getByEmail(String email);

	/**
	 * Returns an existing user from given identifier
	 *
	 * @param id
	 *            the given unique identifier
	 * @return the according user
	 */
	public UserDTO getById(String id);

	/**
	 * Returns a list of all users in the database
	 * <p>
	 * <b>Attention:</b><br>
	 * This method can return big amounts of data. Consider implementing another method with parameters to restrict the result list.
	 *
	 * @return a list of all users
	 */
	public List<UserDTO> getAll();

	/**
	 * Deletes an existing user from the database
	 *
	 * @param id
	 *            the given unique identifier
	 * @return the result of the operation
	 */
	public void delete(String id);

	/**
	 * Checks if a user to a given identifier exists
	 *
	 * @param id
	 *            the given unique identifier
	 * @return {@code true} if the user exists in the database, {@code false} otherwise
	 */
	public boolean userExists(String id);

}