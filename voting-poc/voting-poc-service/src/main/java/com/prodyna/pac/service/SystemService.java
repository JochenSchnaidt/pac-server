package com.prodyna.pac.service;

import com.prodyna.pac.dto.SystemDataDTO;

/**
 * Service checks availability of core systems like database.
 */
public interface SystemService {

	/**
	 * Checks availability of required core systems like database
	 * 
	 * @return A {@code SystemDataDTO} containing the system information
	 */
	public SystemDataDTO checkSystem();
}
