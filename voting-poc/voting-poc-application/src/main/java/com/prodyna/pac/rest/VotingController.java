package com.prodyna.pac.rest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.dto.VotingDTO;
import com.prodyna.pac.service.VotingService;

/**
 * REST controller to cover calls to {@code /voting/} path.
 */
@RestController
@RequestMapping("/voting")
public class VotingController extends AbstractController {

	private static final String VOTE_PATH = "/vote/";

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VotingService service;

	/**
	 * Creates a new voting from incoming data. Data is parsed and mapped to DTO by {@code Jackson}
	 *
	 * @param data
	 *            incoming DTO
	 * @param ucb
	 *            provided {@code Builder} to support URI requirements
	 * @return the vote after the voting was incorporated
	 */
	@RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VoteDTO> createVoting(@RequestBody VotingDTO data, UriComponentsBuilder ucb) {

		log.info("create voting from: " + data.toString());

		VoteDTO persisted = service.create(data);
		checkOperationResult(persisted, persisted.getTopic());
		log.info("voting created with id [" + persisted.getId() + "]");

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path(VOTE_PATH).path(persisted.getId()).build().toUri();
		headers.setLocation(locationUri);

		ResponseEntity<VoteDTO> responseEntity = new ResponseEntity<VoteDTO>(persisted, headers, HttpStatus.CREATED);
		return responseEntity;
	}

	/**
	 * Updates a voting from incoming data. Data is parsed and mapped to DTO by {@code Jackson}
	 *
	 * @param data
	 *            incoming DTO
	 * @param ucb
	 *            provided {@code Builder} to support URI requirements
	 * @return the updated vote after the changed voting was incorporated
	 */
	@RequestMapping(path = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VoteDTO> updateVote(@RequestBody VotingDTO data, UriComponentsBuilder ucb) {

		log.info("update voting from: " + data.toString());

		VoteDTO updated = service.update(data);
		checkOperationResult(updated, updated.getTopic());
		log.info("voting updated with id [" + updated.getId() + "]");

		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path(VOTE_PATH).path(updated.getId()).build().toUri();
		headers.setLocation(locationUri);

		ResponseEntity<VoteDTO> responseEntity = new ResponseEntity<VoteDTO>(updated, headers, HttpStatus.OK);
		return responseEntity;
	}

}