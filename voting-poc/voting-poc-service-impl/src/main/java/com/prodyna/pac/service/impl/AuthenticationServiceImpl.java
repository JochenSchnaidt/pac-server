package com.prodyna.pac.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.prodyna.pac.auth.JwtAuthenticationRequest;
import com.prodyna.pac.dto.AuthenticationDTO;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.service.AuthenticationService;
import com.prodyna.pac.service.UserService;
import com.prodyna.pac.validation.UserValidationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService service;

    @Autowired
    private UserValidationService validationService;

    @Override
    public UsernamePasswordAuthenticationToken authenticate(JwtAuthenticationRequest authenticationRequest) {

        UserDTO user = service.get(authenticationRequest.getEmail());

        validationService.validatePassword(authenticationRequest.getPassword());
        log.debug("data passed validation");

        if (BCrypt.checkpw(authenticationRequest.getPassword(), user.getPassword())) {
            log.info("user authenticated");

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getId(), PASSWORD, user.getAuthorities());
            log.info("authentication created");

            return authentication;
        }

        log.info("user [" + authenticationRequest.getEmail() + "] not authenticated");
        throw new UsernameNotFoundException("User [" + authenticationRequest.getEmail() + "]  not found");
    }

    @Override
    public UsernamePasswordAuthenticationToken authenticate(AuthenticationDTO jwtAuthentication) {

        UserDTO user = service.get(jwtAuthentication.getEmail());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getId(), PASSWORD, user.getAuthorities());
        log.info("authentication created");

        return authentication;
    }

}
