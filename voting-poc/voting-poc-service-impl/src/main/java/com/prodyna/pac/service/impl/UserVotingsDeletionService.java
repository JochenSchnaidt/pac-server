package com.prodyna.pac.service.impl;

import com.prodyna.pac.dto.OperationResult;

/**
 * Internal service to delete votings of a specific user. This method is for business layer internal usage only.
 */
interface UserVotingsDeletionService {

	/**
	 * Deletes the votings of a specific user from the database. This method should only be called, when a user is deleted.
	 * 
	 * @param userId
	 *            the unique identifier of the user
	 * @return the result of the operation
	 */
	public OperationResult deleteVotings(String userId);

}
