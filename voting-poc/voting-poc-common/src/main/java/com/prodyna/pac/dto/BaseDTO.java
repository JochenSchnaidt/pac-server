package com.prodyna.pac.dto;

public abstract class BaseDTO {

    protected OperationResult operationResult;

    public OperationResult getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(OperationResult operationResult) {
        this.operationResult = operationResult;
    }
}
