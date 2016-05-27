package com.prodyna.pac.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.prodyna.pac.dto.BaseDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.ResultState;
import com.prodyna.pac.exception.EntityNotFoundException;
import com.prodyna.pac.exception.ProcessingException;
import com.prodyna.pac.exception.ValidationException;

public abstract class AbstractController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    protected <T extends BaseDTO> void checkOperationResult(T dto, String additionalInfo) {
        checkOperationResult(dto.getOperationResult(), additionalInfo);
        dto.setOperationResult(null); // remove operation result
    }

    protected void checkOperationResult(OperationResult result, String additionalInfo) {

        if (result.getState().equals(ResultState.FAILURE)) {
            if (result.getMessage().isPresent()) {
                throw new ProcessingException(result.getMessage().get());
            } else {
                throw new ProcessingException(additionalInfo);
            }
        }
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error dataNotValid(ValidationException e) {
        return new Error("Data not accepted [" + e.getMessage() + "]");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error voteNotFound(EntityNotFoundException e) {
    	
    	log.warn("handled exception: " + e.getMessage());
    	
        return new Error("Entity [" + e.getMessage() + "] not found");
    }

    @ExceptionHandler(ProcessingException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Error errorWhileProcessing(ProcessingException e) {
        return new Error("Error while processing [" + e.getMessage() + "]");
    }

}
