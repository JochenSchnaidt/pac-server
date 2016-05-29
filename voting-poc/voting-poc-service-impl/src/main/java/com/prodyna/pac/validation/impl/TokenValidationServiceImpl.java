package com.prodyna.pac.validation.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.prodyna.pac.exception.ValidationException;
import com.prodyna.pac.validation.TokenValidationService;

@Component
public class TokenValidationServiceImpl extends ValidationServiceImpl implements TokenValidationService {

    @Override
    public void validateEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new ValidationException("email must not be empty");
        }
    }

    @Override
    public void validateAuthorities(Collection<GrantedAuthority> authorities) {

        if (null == authorities) {
            throw new ValidationException("granted authorities must not be null");
        } else if (authorities.isEmpty()) {
            throw new ValidationException("granted authorities must not be empty");
        }
    }

}
