package com.prodyna.pac.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prodyna.pac.persistence.entities.Vote;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

}
