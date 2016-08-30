package com.prodyna.pac.persistence;

import java.util.List;

import com.prodyna.pac.dto.VoteDTO;

/**
 * Service contains all methods which are needed to operate the vote database.
 */
public interface VotePersistenceService {

	/**
	 * Creates a new vote from given {@code VoteDTO}
	 *
	 * @param data
	 *            the given user dataset
	 * @return the vote user after persisting
	 */
	public VoteDTO create(VoteDTO data);

	/**
	 * Updates an existing vote from given {@code VoteDTO}
	 *
	 * @param data
	 *            the given vote
	 * @return the updated vote after persisting
	 */
	public VoteDTO update(VoteDTO data);

	/**
	 * Returns an existing vote from given identifier
	 *
	 * @param id
	 *            the given unique identifier
	 * @return the according user
	 */
	public VoteDTO get(String id);

	/**
	 * Returns a list of all votes of a specific user in the database
	 *
	 * @param userId
	 *            the given unique user identifier
	 * @return a wrapped list of all votes
	 */
	public List<VoteDTO> getAllByUser(String userId);

	/**
	 * Deletes an existing vote from the database
	 *
	 * @param id
	 *            the given unique identifier
	 * @return the result of the operation
	 */
	public void delete(String id);

	/**
	 * Returns a list of all votes in the database
	 * <p>
	 * <b>Attention:</b><br>
	 * This method can return big amounts of data. Consider implementing another method with parameters to restrict the result list.
	 *
	 * @return a list of all votes
	 */
	public List<VoteDTO> getAll();

}