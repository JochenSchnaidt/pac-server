package com.prodyna.pac.dto;

import java.util.Optional;

/**
 * Meta data class to be used on operations. This class can be used to describe that an operation was successful even when no expected return value is provided. For example an empty list is returned.
 */
public class OperationResult {

	private final Optional<String> message;
	private final ResultState state;

	public OperationResult(ResultState state, Optional<String> message) {
		super();
		this.message = message;
		this.state = state;
	}

	public Optional<String> getMessage() {
		return message;
	}

	public ResultState getState() {
		return state;
	}

}
