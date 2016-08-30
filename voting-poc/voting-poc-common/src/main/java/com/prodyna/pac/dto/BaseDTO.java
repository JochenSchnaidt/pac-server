package com.prodyna.pac.dto;

/**
 * Abstract class for all DTOs to provide meta data of an operation via {@link OperationResult}.
 */
public abstract class BaseDTO {

    protected OperationResult operationResult;

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }
}
