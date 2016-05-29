package com.prodyna.pac.validation.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.exception.ValidationException;
import com.prodyna.pac.validation.UserValidationService;

@Component
public class UserValidationServiceImpl extends ValidationServiceImpl implements UserValidationService {

    @Override
    public void validateUserData(UserDTO data) {

        if (StringUtils.isEmpty(data.getLastName())) {
            throw new ValidationException("lastname must not be empty");
        }

        if (StringUtils.isEmpty(data.getFirstName())) {
            throw new ValidationException("firstname must not be empty");
        }

        validateEmail(data.getEmail());

        validatePassword(data.getPassword());
    }

    @Override
    public void validateEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new ValidationException("email must not be empty");
        }
    }

    @Override
    public void validatePassword(String password) {
        if (StringUtils.isEmpty(password)) {
            throw new ValidationException("password must not be empty");
        }
    }
}
