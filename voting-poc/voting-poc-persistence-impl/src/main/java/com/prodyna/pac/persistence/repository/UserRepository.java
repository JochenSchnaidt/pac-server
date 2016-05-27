package com.prodyna.pac.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prodyna.pac.persistence.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

     public User findByEmail(String email);

    // public List<User> findByLastName(String lastName);

}