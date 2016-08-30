package com.prodyna.pac.validation.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.dto.VotingDTO;
import com.prodyna.pac.exception.ValidationException;
import com.prodyna.pac.validation.ValidationService;

/**
 * Implementation class of {@code ValidationService}.
 * 
 * @see ValidationService
 */
@Component
public class ValidationServiceImpl implements ValidationService {

	@Override
	public void validateEntityId(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new ValidationException("id must not be empty");
		}
	}

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
	public void validatePassword(String password) {
		if (StringUtils.isEmpty(password)) {
			throw new ValidationException("password must not be empty");
		}
	}

	@Override
	public void validateVoteData(VoteDTO data, boolean isUpdate) {

		if (isUpdate) {
			validateEntityId(data.getId());
		}

		if (StringUtils.isEmpty(data.getTopic())) {
			throw new ValidationException("topic must not be empty");
		}
	}

	@Override
	public void validateVotingData(VotingDTO data) {

		if (StringUtils.isEmpty(data.getVoteId())) {
			throw new ValidationException("vote id must not be empty");
		}

		if (StringUtils.isEmpty(data.getUserId())) {
			throw new ValidationException("user id must not be empty");
		}

		if (StringUtils.isEmpty(data.getOptionId())) {
			throw new ValidationException("option id must not be empty");
		}
	}

}
