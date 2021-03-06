package com.prodyna.pac.dto;

/**
 * System wide DTO for system health check.
 */
public class SystemDataDTO {

	// explicit default constructor for JSON mapping
	public SystemDataDTO() {
		super();
	}

	private boolean databaseIsAvailable;

	public boolean isDatabaseIsAvailable() {
		return databaseIsAvailable;
	}

	public void setDatabaseIsAvailable(boolean databaseIsAvailable) {
		this.databaseIsAvailable = databaseIsAvailable;
	}

}
