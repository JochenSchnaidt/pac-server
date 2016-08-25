package com.prodyna.pac.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prodyna.pac.auth.JwtAuthenticationRequest;
import com.prodyna.pac.auth.JwtAuthenticationResponse;
import com.prodyna.pac.service.AuthenticationService;
import com.prodyna.pac.service.TokenService;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthenticationController extends AbstractController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private TokenService tokenService;

    class Dummy{
    	private String  votingAuthenticationToken;
        private  String email;
        private  String password;
        
		public Dummy(String votingAuthenticationToken, String email, String password) {
			super();
			this.votingAuthenticationToken = votingAuthenticationToken;
			this.email = email;
			this.password = password;
		}

		public String getVotingAuthenticationToken() {
			return votingAuthenticationToken;
		}

		public void setVotingAuthenticationToken(String votingAuthenticationToken) {
			this.votingAuthenticationToken = votingAuthenticationToken;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
        
    }
    
    @RequestMapping(path = "/authOld/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<JwtAuthenticationResponse> createAuthenticationTokenOld(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        log.info("create authentication from: " + authenticationRequest.toString());

        UsernamePasswordAuthenticationToken authentication = authService.authenticate(authenticationRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final JwtAuthenticationResponse response = tokenService.generateToken(authenticationRequest.getEmail(), (String) authentication.getPrincipal(), authentication.getAuthorities());
        
        log.info("created authentication reply: " + response.toString());
        
        ResponseEntity<JwtAuthenticationResponse> responseEntity = new ResponseEntity<JwtAuthenticationResponse>(response, HttpStatus.OK);
        
        return responseEntity;
    }
    
    
    
    
    
    @RequestMapping(path = "/auth/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Dummy> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        log.info("create authentication from: " + authenticationRequest.toString());

        UsernamePasswordAuthenticationToken authentication = authService.authenticate(authenticationRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final JwtAuthenticationResponse response = tokenService.generateToken(authenticationRequest.getEmail(), (String) authentication.getPrincipal(), authentication.getAuthorities());
        
        log.info("created authentication reply: " + response.toString());
        
        ResponseEntity<Dummy> responseEntity = new ResponseEntity<Dummy>(new Dummy(response.getVotingAuthenticationToken(), authenticationRequest.getEmail(), authenticationRequest.getPassword() ), HttpStatus.OK);
        
        return responseEntity;
    }

    
}