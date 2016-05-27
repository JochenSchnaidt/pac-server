package com.prodyna.pac.dto;

import java.util.Optional;

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
