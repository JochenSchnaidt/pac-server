package com.prodyna.pac.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prodyna.pac.persistence.entities.User;

/**
 * Database interface to communicate with the actual database via Spring Data Repository.
 * <p>
 * This class explicitly works with MongoDB.
 *
 * @see MongoRepository
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

	/**
	 * Returns an existing user from given email address
	 *
	 * @param email
	 *            the given email address
	 * @return the according user
	 */
	public User findByEmail(String email);

}
