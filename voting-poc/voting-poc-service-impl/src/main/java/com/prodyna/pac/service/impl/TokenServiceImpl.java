package com.prodyna.pac.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.UserDTO;
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
	private final String CLAIM_KEY_ADMINISTRATOR = "administrator";
	private final String CLAIM_KEY_AUTHORITIES = "authorites";

	@Value("${jwt.authentication.secret}")
	private String secret;

	@Autowired
	private TokenValidationService validationService;

	@Override
	public String generateToken(UserDTO user) {

		validationService.validateEntityId(user.getId());
		validationService.validateEmail(user.getEmail());
		validationService.validateAuthorities(user.getAuthorities());
		log.debug("data passed validation");

		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_ID, user.getId());
		claims.put(CLAIM_KEY_EMAIL, user.getEmail());
		claims.put(CLAIM_KEY_ADMINISTRATOR, user.isAdministrator());
		claims.put(CLAIM_KEY_AUTHORITIES, user.getAuthorities());
		claims.put(CLAIM_KEY_CREATED, new Date());

		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
		log.debug("authentication token created: " + token);

		return token;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserDTO parseUser(String token) {

		if (!StringUtils.hasText(token)) {
			return null;
		}

		Claims claims = getClaimsFromToken(token);

		if (null == claims) {
			log.debug("no claims in token");
			return null;
		}

		// check format
		if (!claims.containsKey(CLAIM_KEY_ID)) {
			log.error("claims does not contain an ID");
			return null;
		}
		if (!claims.containsKey(CLAIM_KEY_EMAIL)) {
			log.error("claims does not contain an email");
			return null;
		}
		if (!claims.containsKey(CLAIM_KEY_AUTHORITIES)) {
			log.error("claims does not contain any granted authorities");
			return null;
		}
		if (!claims.containsKey(CLAIM_KEY_CREATED)) {
			log.error("claims does not contain any creation information");
			return null;
		}

		LocalDateTime created = LocalDateTime.ofInstant(Instant.ofEpochMilli((long) claims.get(CLAIM_KEY_CREATED)), ZoneId.systemDefault());

		if (LocalDateTime.now().isAfter(created.plusWeeks(1L))) {
			log.error("token is expired");
			return null;
		} else {

			UserDTO user = new UserDTO();
			user.setId((String) claims.get(CLAIM_KEY_ID));
			user.setEmail((String) claims.get(CLAIM_KEY_EMAIL));

			List<Map<String, String>> list = (List<Map<String, String>>) claims.get(CLAIM_KEY_AUTHORITIES);

			list.forEach(entry -> {
				entry.values().forEach(auth -> {
					user.getAuthorities().add(new SimpleGrantedAuthority(auth));
				});
			});

			log.debug("user created from token: " + user.toString());

			return user;
		}
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			log.debug("claims: " + claims.toString());
		} catch (Exception e) {
			log.error("Exception while parsing token: " +e.getMessage());
			if(log.isDebugEnabled()){
				e.printStackTrace();
			}
			claims = null;
		}
		return claims;
	}

}
