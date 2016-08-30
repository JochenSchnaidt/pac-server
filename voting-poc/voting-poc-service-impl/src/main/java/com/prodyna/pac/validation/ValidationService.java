package com.prodyna.pac.validation;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.dto.VotingDTO;

/**
 * Central validation service to check if required data is present before processing.
 */
public interface ValidationService {

	/**
	 * Checks if the provided identifier is valid<br>
	 * Raises a {@code ValidationException} in case the validation failed
	 * 
	 * @param id
	 *            the identifier
	 */
	public void validateEntityId(String id);

	/**
	 * Checks if the provided email is valid<br>
	 * Raises a {@code ValidationException} in case the validation failed
	 * 
	 * @param email
	 *            the email address
	 */
	public void validateEmail(String email);

	/**
	 * Checks if the provided password is valid<br>
	 * Raises a {@code ValidationException} in case the validation failed
	 * 
	 * @param password
	 *            the password to check
	 */
	public void validatePassword(String password);

	/**
	 * Checks if at least one authority is granted<br>
	 * Raises a {@code ValidationException} in case the validation failed
	 * 
	 * @param authorities
	 *            the collection of authorities to check
	 */
	public void validateAuthorities(Collection<GrantedAuthority> authorities);

	/**
	 * Checks if a user dataset provides the following items:
	 * <ul>
	 * <li>email</li>
	 * <li>password</li>
	 * <li>first name</li>
	 * <li>last name</li>
	 * </ul>
	 * <br>
	 * Raises a {@code ValidationException} in case the validation failed
	 * 
	 * @param data
	 *            the user dataset to check
	 */
	public void validateUserData(UserDTO data);

	/**
	 * Checks if a vote dataset provides at least minimal the following items:
	 * <ul>
	 * <li>id - in case of an update</li>
	 * <li>topic</li>
	 * </ul>
	 * <br>
	 * Raises a {@code ValidationException} in case the validation failed
	 * 
	 * @param data
	 *            the vote dataset to check
	 * @param isUpdate
	 *            flag in case of update
	 */
	public void validateVoteData(VoteDTO data, boolean isUpdate);

	/**
	 * * Checks if a voting dataset is complete. <br>
	 * Raises a {@code ValidationException} in case the validation failed
	 *
	 * @param data
	 *            the voting dataset to check
	 */
	public void validateVotingData(VotingDTO data);
}
