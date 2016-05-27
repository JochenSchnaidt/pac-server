package com.prodyna.pac.validation.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.prodyna.pac.exception.ValidationException;
import com.prodyna.pac.validation.ValidationService;

@Component
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validateEntityId(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new ValidationException("id must not be empty");
        }
    }

}
