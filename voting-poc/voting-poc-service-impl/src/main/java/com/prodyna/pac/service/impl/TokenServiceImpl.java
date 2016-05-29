package com.prodyna.pac.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prodyna.pac.auth.JwtAuthenticationResponse;
import com.prodyna.pac.dto.AuthenticationDTO;
import com.prodyna.pac.service.TokenService;
import com.prodyna.pac.validation.TokenValidationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements TokenService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String CLAIM_KEY_CREATED = "created";
    private final String CLAIM_KEY_ID = "id";
    private final String CLAIM_KEY_EMAIL = "email";
    private final String CLAIM_KEY_AUTHORITIES = "authorites";

    @Value("${authentication.jwt.secret}")
    String secret;

    @Autowired
    private TokenValidationService validationService;

    @Override
    public JwtAuthenticationResponse generateToken(String email, String id, Collection<GrantedAuthority> authorities) {

        validationService.validateEntityId(id);
        validationService.validateEmail(email);
        validationService.validateAuthorities(authorities);
        log.debug("data passed validation");

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ID, id);
        claims.put(CLAIM_KEY_EMAIL, email);
        claims.put(CLAIM_KEY_AUTHORITIES, authorities);
        claims.put(CLAIM_KEY_CREATED, new Date());

        String generatedToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        log.debug("authentication token created: " + generatedToken);

        JwtAuthenticationResponse response = new JwtAuthenticationResponse(generatedToken);
        log.debug("jwt authentication created: " + response.toString());

        return response;
    }

    @Override
    public AuthenticationDTO checkValidation(String rawToken) {

        if (!StringUtils.hasText(rawToken)) {
            return new AuthenticationDTO(null, null, false);
        }

        Claims claims = getClaimsFromToken(rawToken);

        if (null == claims) {
            return new AuthenticationDTO(null, null, false);
        }

        // check format
        if (!claims.containsKey(CLAIM_KEY_ID)) {
            return new AuthenticationDTO(null, null, false);
        }
        if (!claims.containsKey(CLAIM_KEY_EMAIL)) {
            return new AuthenticationDTO(null, null, false);
        }
        if (!claims.containsKey(CLAIM_KEY_AUTHORITIES)) {
            return new AuthenticationDTO(null, null, false);
        }
        if (!claims.containsKey(CLAIM_KEY_CREATED)) {
            return new AuthenticationDTO(null, null, false);
        }

        LocalDateTime created = LocalDateTime.ofInstant(Instant.ofEpochMilli((long) claims.get(CLAIM_KEY_CREATED)), ZoneId.systemDefault());

        if (LocalDateTime.now().isAfter(created.plusWeeks(1L))) {
            return new AuthenticationDTO(null, null, false);
        } else {
            return new AuthenticationDTO((String) claims.get(CLAIM_KEY_ID), (String) claims.get(CLAIM_KEY_EMAIL), true);    
        }
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            log.debug("claims: " + claims.toString());
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
