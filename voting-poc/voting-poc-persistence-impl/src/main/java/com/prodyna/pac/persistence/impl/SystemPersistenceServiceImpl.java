package com.prodyna.pac.persistence.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.prodyna.pac.persistence.SystemPersistenceService;
import com.prodyna.pac.persistence.entities.User;
import com.prodyna.pac.persistence.entities.Vote;

/**
 * Implementation class of {@code SystemPersistenceService}.
 * <p>
 * This class explicitly works with MongoDB.
 *
 * @see SystemPersistenceService
 */
@Service
public class SystemPersistenceServiceImpl implements SystemPersistenceService {

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method explicit checks for the existence of the {@code User} and {@code Vote} collections in MongoDB
	 *
	 * @return {@code true} if the database is accessible, {@code false} otherwise
	 */
	@Override
	public boolean checkAvailability() {

		if (mongoTemplate.collectionExists(Vote.class) && mongoTemplate.collectionExists(User.class)) {
			return true;
		} else {
			return false;
		}
	}

}
