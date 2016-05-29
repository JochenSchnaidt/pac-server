package com.prodyna.pac.validation;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface TokenValidationService extends ValidationService {

    public void validateEmail(String email);
    
    public void validateAuthorities(Collection<GrantedAuthority> authorities);

}
