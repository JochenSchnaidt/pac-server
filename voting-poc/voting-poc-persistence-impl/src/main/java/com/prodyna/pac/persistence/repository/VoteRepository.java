package com.prodyna.pac.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prodyna.pac.persistence.entities.Vote;

/**
 * Database interface to communicate with the actual database via Spring Data Repository.
 * <p>
 * This class explicitly works with MongoDB.
 *
 * @see MongoRepository
 */
@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

}
