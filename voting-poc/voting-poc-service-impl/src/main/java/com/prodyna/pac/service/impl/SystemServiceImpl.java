package com.prodyna.pac.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodyna.pac.dto.SystemDataDTO;
import com.prodyna.pac.persistence.SystemPersistenceService;
import com.prodyna.pac.service.SystemService;

/**
 * Implementation class of {@code SystemService}.
 * 
 * @see SystemService
 */
@Service
public class SystemServiceImpl implements SystemService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SystemPersistenceService persistenceService;

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method currently checks the database only and needs to be extended when other (infrastructure) components are added to the application
	 */
	@Override
	public SystemDataDTO checkSystem() {

		boolean dbAvailability = persistenceService.checkAvailability();

		SystemDataDTO dto = new SystemDataDTO();
		dto.setDatabaseIsAvailable(dbAvailability);

		log.info("system is fine");
		return dto;
	}

}
