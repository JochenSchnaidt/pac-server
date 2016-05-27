package com.prodyna.pac.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.prodyna.pac.dto.SystemDataDTO;
import com.prodyna.pac.persistence.SystemPersistenceService;
import com.prodyna.pac.service.SystemService;

@Component
public class SystemServiceImpl implements SystemService {

    private final Logger             log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemPersistenceService persistenceService;

    @Override
    public SystemDataDTO checkSystem() {

        persistenceService.checkAvailability();

        log.info("system is fine");
        return new SystemDataDTO();
    }

}
