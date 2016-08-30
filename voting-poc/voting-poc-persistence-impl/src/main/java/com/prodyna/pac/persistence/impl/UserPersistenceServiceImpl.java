package com.prodyna.pac.persistence.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.prodyna.pac.dto.SelectionDTO;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.exception.EntityNotFoundException;
import com.prodyna.pac.exception.ProcessingException;
import com.prodyna.pac.persistence.UserPersistenceService;
import com.prodyna.pac.persistence.entities.User;
import com.prodyna.pac.persistence.repository.UserRepository;
import com.prodyna.pac.security.Roles;

/**
 * Implementation class of {@code UserPersistenceService}.
 * <p>
 * This class explicitly works with {@code Repository} and is therefore implementation neutral.
 *
 * @see UserPersistenceService
 */
@Service
public class UserPersistenceServiceImpl implements UserPersistenceService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository repository;

	@Override
	public UserDTO create(UserDTO data) {

		//@formatter:off
		User user = new User.Builder()
				.firstName(data.getFirstName())
				.lastName(data.getLastName())
				.email(data.getEmail())
				.password(data.getPassword())
				.isAdministrator(data.isAdministrator())
				.selections(data.getSelections())
				.build();
		//@formatter:on

		User persisted = repository.insert(user);

		if (null == persisted) {
			throw new ProcessingException("No user created with email: " + data.getEmail());
		}

		log.info("new user created: " + persisted.toString());

		return convert(persisted);
	}

	@Override
	public UserDTO update(UserDTO data) {

		//@formatter:off
        User user = new User.Builder()
                .id(data.getId())
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .email(data.getEmail())
                .password(data.getPassword())
                .isAdministrator(data.isAdministrator())
                .selections(data.getSelections())
                .creationDate(data.getCreationDate())
                .build();
        //@formatter:on

		User persisted = repository.save(user);

		if (null == persisted) {
			throw new ProcessingException("No user updated with id: " + data.getId());
		}

		log.info("user updated: " + persisted.toString());

		return convert(persisted);
	}

	@Override
	public UserDTO getByEmail(String email) {

		log.info("find user by email [" + email + "]");

		User user = repository.findByEmail(email);

		if (null == user) {
			throw new EntityNotFoundException("No user found to email: " + email);
		}

		log.info("user found: " + user.toString());

		return convert(user);
	}

	@Override
	public UserDTO getById(String id) {

		log.info("find user by id [" + id + "]");

		User user = repository.findOne(id);

		if (null == user) {
			throw new EntityNotFoundException("No user found to id: " + id);
		}

		log.info("user found: " + user.toString());

		return convert(user);
	}

	@Override
	public List<UserDTO> getAll() {

		List<User> allUsers = repository.findAll();

		List<UserDTO> list = new ArrayList<UserDTO>();

		allUsers.forEach(user -> list.add(convert(user)));

		return list;
	}

	@Override
	public void delete(String id) {
		repository.delete(id);
	}

	@Override
	public boolean userExists(String id) {
		return repository.exists(id);
	}

	/**
	 * Converter method to transform a given entity {@code User} to the application wide DTO {@code UserDTO}
	 *
	 * @param entity
	 *            the entity from the database
	 * @return the DTO
	 */
	private UserDTO convert(User entity) {

		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setEmail(entity.getEmail());
		dto.setPassword(entity.getPassword());

		final Set<SelectionDTO> set = new HashSet<SelectionDTO>();
		if (null != entity.getSelections()) {
			entity.getSelections().forEach(selection -> set.add(new SelectionDTO(selection.getVoteId().toString(), selection.getVotedOptionId())));
		}
		dto.setSelections(set);

		dto.getAuthorities().add(new SimpleGrantedAuthority(Roles.ROLE_USER));
		if (entity.isAdministrator()) {
			dto.getAuthorities().add(new SimpleGrantedAuthority(Roles.ROLE_ADMINISTRATOR));
		}

		dto.setAdministrator(entity.isAdministrator());
		dto.setCreationDate(entity.getCreationDate());
		return dto;
	}

}
