package com.prodyna.pac.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.security.Roles;

/**
 * Service contains all methods which are needed to perform vote related operations.
 * <p>
 * All modifications of datasets are protected by Spring Security.
 */
public interface VoteService {

	/**
	 * Creates a new vote from given {@code VoteDTO}
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @param data
	 *            the given user dataset
	 * @return the vote user after persisting
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public VoteDTO create(VoteDTO data);

	/**
	 * Updates an existing vote from given {@code VoteDTO}
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @param data
	 *            the given vote
	 * @return the updated vote after persisting
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public VoteDTO update(VoteDTO data);

	/**
	 * Returns an existing vote from given identifier
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @param id
	 *            the given unique identifier
	 * @return the according user
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public VoteDTO get(String id);

	/**
	 * Returns a list of all votes in the database
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @return a wrapped list of all votes
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public ListWrapperDTO<VoteDTO> getAll();

	/**
	 * Returns a list of all votes of a specific user in the database
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @param userId
	 *            the given unique user identifier
	 * @return a wrapped list of all votes
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public ListWrapperDTO<VoteDTO> getAllByUser(String userId);

	/**
	 * Deletes an existing vote from the database
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
