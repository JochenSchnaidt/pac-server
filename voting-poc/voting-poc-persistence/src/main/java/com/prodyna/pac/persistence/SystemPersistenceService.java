package com.prodyna.pac.persistence;

/**
 * Service checks availability of database.
 */
public interface SystemPersistenceService {

	/**
	 * Checks if the database is available for usage
	 *
	 * @return {@code true} if the database is accessible, {@code false} otherwise
	 */
	public boolean checkAvailability();

}
