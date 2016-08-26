package com.prodyna.pac.rest;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

	private static final String USER_PATH = "/user/";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService service;

	@RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO data, UriComponentsBuilder ucb) {

		log.info("create user from: " + data.toString());

		UserDTO persisted = service.create(data);
		checkOperationResult(persisted, persisted.getId());
		log.info("user created with id [" + persisted.getId() + "]");

		removeInternalData(persisted);

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path(USER_PATH).path(persisted.getEmail()).build().toUri();
		headers.setLocation(locationUri);

		ResponseEntity<UserDTO> responseEntity = new ResponseEntity<UserDTO>(persisted, headers, HttpStatus.CREATED);
		return responseEntity;
	}

	@RequestMapping(path = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO data, UriComponentsBuilder ucb) {

		log.info("update user from: " + data.toString());

		UserDTO updated = service.update(data);
		checkOperationResult(updated, updated.getId());
		log.info("user updated with id [" + updated.getId() + "]");

		removeInternalData(updated);

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path(USER_PATH).path(updated.getId()).build().toUri();
		headers.setLocation(locationUri);

		ResponseEntity<UserDTO> responseEntity = new ResponseEntity<UserDTO>(updated, headers, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(path = "/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserDTO> getUser(@PathVariable String email, UriComponentsBuilder ucb) {

		log.info("get user with id [" + email + "]");

		UserDTO entity = service.get(email);
		checkOperationResult(entity, entity.getId());
		log.info("user found and will be returned");

		removeInternalData(entity);

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path(USER_PATH).path(entity.getId()).build().toUri();
		headers.setLocation(locationUri);

		ResponseEntity<UserDTO> responseEntity = new ResponseEntity<UserDTO>(entity, headers, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(path = "all/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<UserDTO>> getAllUser(UriComponentsBuilder ucb) {

		log.info("get all users");

		ListWrapperDTO<UserDTO> wrapper = service.getAll();
		checkOperationResult(wrapper, "no entities found");
		log.info("found " + wrapper.getList().size() + " entities");

		wrapper.getList().forEach(user -> removeInternalData(user));

		ResponseEntity<List<UserDTO>> responseEntities = new ResponseEntity<List<UserDTO>>(wrapper.getList(), HttpStatus.OK);
		return responseEntities;
	}

	@RequestMapping(path = "/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> deleteUser(@PathVariable String userId, UriComponentsBuilder ucb) {

		log.info("delete user with id[ " + userId + "]");

		OperationResult result = service.delete(userId);
		checkOperationResult(result, "error deleting user " + userId);
		log.info("user deletion returned: " + result.getState());

		ResponseEntity<String> responseEntities = new ResponseEntity<String>(HttpStatus.OK);
		return responseEntities;
	}

	private void removeInternalData(UserDTO data) {
		data.setPassword(null);
		data.setAuthorities(null);
	}

}