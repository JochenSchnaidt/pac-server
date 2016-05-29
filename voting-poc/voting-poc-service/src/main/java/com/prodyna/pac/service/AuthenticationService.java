package com.prodyna.pac.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.prodyna.pac.auth.JwtAuthenticationRequest;
import com.prodyna.pac.dto.AuthenticationDTO;

public interface AuthenticationService {

    public final static String PASSWORD = "DUMMY PASSWORD";

    public UsernamePasswordAuthenticationToken authenticate(JwtAuthenticationRequest authenticationRequest);

    public UsernamePasswordAuthenticationToken authenticate(AuthenticationDTO jwtAuthentication);
}
