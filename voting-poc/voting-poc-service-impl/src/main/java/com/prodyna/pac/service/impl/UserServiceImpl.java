package com.prodyna.pac.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.ResultState;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.persistence.UserPersistenceService;
import com.prodyna.pac.security.Roles;
import com.prodyna.pac.service.UserService;
import com.prodyna.pac.validation.ValidationService;

/**
 * Implementation class of {@code UserService}.
 * 
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserPersistenceService persistenceService;

	@Autowired
	private UserVotingsDeletionService votingService;

	@Autowired
	private ValidationService validationService;

	@Override
	public UserDTO create(UserDTO data) {

		log.debug("create user from data: " + data.toString());

		validationService.validateUserData(data);
		log.debug("data passed validation");

		data.setPassword(hashPassword(data.getPassword()));

		UserDTO persisted = persistenceService.create(data);
		log.debug("user created with id [" + persisted.getId() + "]");

		OperationResult result = new OperationResult(ResultState.SUCCESS, Optional.of("entity created"));
		persisted.setOperationResult(result);

		return persisted;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * The update is only performed, when the user changes it's own data or has administrator privileges.
	 */
	@Override
	public UserDTO update(UserDTO data) {

		log.debug("update user from data: " + data.toString());

		validationService.validateEntityId(data.getId());
		log.debug("data passed validation");

		UserDTO persisted = persistenceService.getById(data.getId());

		if (changed(persisted.getFirstName(), data.getFirstName())) {
			persisted.setFirstName(data.getFirstName());
		}

		if (changed(persisted.getLastName(), data.getLastName())) {
			persisted.setLastName(data.getLastName());
		}

		if (changed(persisted.getEmail(), data.getEmail())) {
			persisted.setEmail(data.getEmail());
		}

		if (changedPassword(persisted.getPassword(), data.getPassword())) {
			persisted.setPassword(hashPassword(data.getPassword()));
		}

		persisted.setAdministrator(data.isAdministrator());
		log.debug("user with new data: " + data.toString());

		if (isAuthorized(persisted.getId())) {
			UserDTO updated = persistenceService.update(persisted);
			log.debug("user updated with id [" + updated.getId() + "]");

			updated.setOperationResult(new OperationResult(ResultState.SUCCESS, Optional.empty()));
			return updated;
		} else {
			log.info("entity with id [" + data.getId() + "] is not editable");
			OperationResult result = new OperationResult(ResultState.FAILURE, Optional.of("entity is not editable"));
			data.setOperationResult(result);
			return data;
		}
	}

	@Override
	public UserDTO get(String email) {

		log.debug("find user from email [" + email + "]");

		validationService.validateEmail(email);
		log.debug("data passed validation");

		UserDTO persisted = persistenceService.getByEmail(email);
		log.debug("returning user with id [" + persisted.getId() + "]");

		OperationResult result = new OperationResult(ResultState.SUCCESS, Optional.empty());
		persisted.setOperationResult(result);

		return persisted;
	}

	@Override
	public ListWrapperDTO<UserDTO> getAll() {

		log.debug("find all users");

		ListWrapperDTO<UserDTO> dto = new ListWrapperDTO<>(persistenceService.getAll());
		log.debug("returning " + dto.getList().size() + " user");

		dto.setOperationResult(new OperationResult(ResultState.SUCCESS, Optional.empty()));
		return dto;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * The operation is only performed, when the user deletes it's own dataset or has administrator privileges.
	 */
	@Override
	public OperationResult delete(String id) {

		log.debug("delete user with id [" + id + "]");

		validationService.validateEntityId(id);
		log.debug("data passed validation");

		if ((!persistenceService.userExists(id)) && isAuthorized(id)) {

			OperationResult result = votingService.deleteVotings(id);
			if (!result.getState().equals(ResultState.SUCCESS)) {
				return new OperationResult(ResultState.FAILURE, Optional.of("was not able to remove the votings of the user"));
			}

			persistenceService.delete(id);

			log.debug("user with id [" + id + "] deleted");
			return new OperationResult(ResultState.SUCCESS, Optional.empty());
		} else {
			log.info("entity with id [" + id + "] is not editable");
			return new OperationResult(ResultState.FAILURE, Optional.of("entity is not deletable"));
		}
	}

	/**
	 * Checks if two {@code String} variables have the same value
	 * 
	 * @param oldValue
	 *            the already existing value
	 * @param newValue
	 *            the possibly new value
	 * @return {@code true} if the value changed, {@code false} otherwise
	 */
	private boolean changed(String oldValue, String newValue) {

		if (StringUtils.hasText(newValue) && !oldValue.equals(newValue)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a provided new password is acceptable. The new password must not be the same than the existing.
	 * 
	 * @param oldValue
	 *            the existing hash value of the old password
	 * @param newValue
	 *            the provided new password
	 * @return {@code true} if the new password is acceptable, {@code false} otherwise
	 */
	private boolean changedPassword(String oldValue, String newValue) {

		if (StringUtils.isEmpty(newValue)) {
			return false;
		}
		return !checkPassword(newValue, oldValue);
	}

	/**
	 * Creates a hash from a given password
	 * 
	 * @param password
	 *            the provided password in plain text
	 * @return the password's hash
	 */
	private final String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	/**
	 * Checks if a provided password is valid in combination with the stored hash value
	 * 
	 * @param password
	 *            the provided password in plain text
	 * @param hashed
	 *            the hashed password
	 * @return {@code true} if the password's hash is equal the stored hash, {@code false} otherwise
	 */
	private final boolean checkPassword(String password, String hashed) {
		return BCrypt.checkpw(password, hashed);
	}

	/**
	 * Checks if the current user has appropriate rights to perform a operation.
	 * 
	 * @param userId
	 *            the current user
	 * @return {@code true} if the user is authorized, {@code false} otherwise
	 */
	private boolean isAuthorized(String userId) {

		String authenticatedUsers = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (userId.equals(authenticatedUsers)) {
			return true;
		} else {
			Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

			for (GrantedAuthority authority : authorities) {
				if (authority.getAuthority().equals(Roles.ROLE_ADMINISTRATOR)) {
					return true;
				}
			}
			return false;
		}
	}

	// fulfill UserDetailsService requirements

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return get(username);
	}
}
