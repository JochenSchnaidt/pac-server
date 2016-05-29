package com.prodyna.pac.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.prodyna.pac.auth.JwtAuthenticationResponse;
import com.prodyna.pac.dto.AuthenticationDTO;

public interface TokenService {

    public JwtAuthenticationResponse generateToken(String email, String id, Collection<GrantedAuthority> authorities);

    public AuthenticationDTO checkValidation(String rawToken);

}
