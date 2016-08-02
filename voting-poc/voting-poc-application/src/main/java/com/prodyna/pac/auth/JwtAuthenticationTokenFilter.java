package com.prodyna.pac.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.prodyna.pac.dto.AuthenticationDTO;
import com.prodyna.pac.service.AuthenticationService;
import com.prodyna.pac.service.TokenService;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.authentication.header}")
    private String tokenHeader;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String rawToken = httpRequest.getHeader(tokenHeader);
        log.debug("rawToken in filter: " + rawToken);

        if (null == SecurityContextHolder.getContext().getAuthentication()) {

            AuthenticationDTO jwtAuthentication = tokenService.checkValidation(rawToken);

            log.debug("jwtAuthentication created: " + jwtAuthentication.toString());

            if (jwtAuthentication.isAuthenticated()) {
                UsernamePasswordAuthenticationToken authentication = authService.authenticate(jwtAuthentication);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            log.error("some kind of authentication found: " + SecurityContextHolder.getContext().getAuthentication().toString());
        }
        chain.doFilter(request, response);
    }
}
