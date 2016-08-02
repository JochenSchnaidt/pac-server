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
import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.service.VoteService;

@RestController
@RequestMapping("/vote")
public class VoteController extends AbstractController {

    private static final String VOTE_PATH = "/vote/";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VoteService service;

    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<VoteDTO> createVote(@RequestBody VoteDTO data, UriComponentsBuilder ucb) {

        log.info("create vote from: " + data.toString());

        VoteDTO persisted = service.create(data);
        checkOperationResult(persisted, persisted.getTopic());
        log.info("vote created with id [" + persisted.getId() + "]");

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path(VOTE_PATH).path(persisted.getId()).build().toUri();
        headers.setLocation(locationUri);

        ResponseEntity<VoteDTO> responseEntity = new ResponseEntity<VoteDTO>(persisted, headers, HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping(path = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<VoteDTO> updateVote(@RequestBody VoteDTO data, UriComponentsBuilder ucb) {

        log.info("update vote from: " + data.toString());

        VoteDTO updated = service.update(data);
        checkOperationResult(updated, updated.getTopic());
        log.info("vote updated with id [" + updated.getId() + "]");

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path(VOTE_PATH).path(updated.getId()).build().toUri();
        headers.setLocation(locationUri);

        ResponseEntity<VoteDTO> responseEntity = new ResponseEntity<VoteDTO>(updated, headers, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(path = "/{voteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<VoteDTO> getVote(@PathVariable String voteId, UriComponentsBuilder ucb) {

        log.info("get vote with id [" + voteId + "]");

        VoteDTO entity = service.get(voteId);
        checkOperationResult(entity, entity.getTopic());
        log.info("vote found and will be returned");

        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb.path(VOTE_PATH).path(entity.getId()).build().toUri();
        headers.setLocation(locationUri);

        ResponseEntity<VoteDTO> responseEntity = new ResponseEntity<VoteDTO>(entity, headers, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<VoteDTO>> getAllVote(@PathVariable String userId, UriComponentsBuilder ucb) {

        log.info("get all votes for user with id [" + userId + "]");
        ListWrapperDTO<VoteDTO> wrapper = service.getAllByUser(userId);
        checkOperationResult(wrapper, "no entities found");
        log.info("found " + wrapper.getList().size() + " entities");

        ResponseEntity<List<VoteDTO>> responseEntities = new ResponseEntity<List<VoteDTO>>(wrapper.getList(), HttpStatus.OK);
        return responseEntities;
    }

    @RequestMapping(path = "/{voteId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> deleteVote(@PathVariable String voteId, UriComponentsBuilder ucb) {

        log.info("delete vote with id [" + voteId + "]");
        OperationResult result = service.delete(voteId);
        checkOperationResult(result, "error deleting vote " + voteId);
        log.info("vote deletion returned: " + result.getState());

        ResponseEntity<String> responseEntities = new ResponseEntity<String>(HttpStatus.OK);
        return responseEntities;
    }

}
