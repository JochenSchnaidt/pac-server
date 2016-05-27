package com.prodyna.pac.persistence.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.prodyna.pac.persistence.SystemPersistenceService;
import com.prodyna.pac.persistence.entities.Vote;

@Service
public class SystemPersistenceServiceImpl implements SystemPersistenceService {

    @Autowired
    MongoTemplate mongoTemplate;
    
    @Override
    public void checkAvailability() {
      
        mongoTemplate.collectionExists(Vote.class);

    }

}
