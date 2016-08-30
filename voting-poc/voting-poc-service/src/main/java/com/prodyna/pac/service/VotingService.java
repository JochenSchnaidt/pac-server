package com.prodyna.pac.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.dto.VotingDTO;
import com.prodyna.pac.security.Roles;

/**
 * Service contains all methods which are needed to apply user voting on the vote database.
 * <p>
 * All modifications of datasets are protected by Spring Security.
 */
public interface VotingService {

	/**
	 * Creates a new voting of a user on a vote from given {@code VotingDTO}
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @param data
	 *            the given user selection dataset
	 * @return the vote user after persisting
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public VoteDTO create(VotingDTO data);

	/**
	 * Updates an existing voting of a user on a vote from given {@code VotingDTO}
	 * <p>
	 * Note that this method is protected by Spring Security
	 * 
	 * @param data
	 *            the given user selection dataset
	 * @return the updated vote after persisting
	 */
	@PreAuthorize(value = Roles.HAS_ROLE_USER)
	public VoteDTO update(VotingDTO data);

}
