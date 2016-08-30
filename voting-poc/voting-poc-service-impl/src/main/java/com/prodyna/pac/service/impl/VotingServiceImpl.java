package com.prodyna.pac.service.impl;

import java.util.Iterator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.ResultState;
import com.prodyna.pac.dto.SelectionDTO;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.dto.VotingDTO;
import com.prodyna.pac.persistence.UserPersistenceService;
import com.prodyna.pac.persistence.VotePersistenceService;
import com.prodyna.pac.service.VotingService;
import com.prodyna.pac.validation.ValidationService;

/**
 * Implementation class of {@code VotingService} and {@code UserVotingsDeletionService}.
 * 
 * @see VotingService
 * @see UserVotingsDeletionService
 */
@Service
public class VotingServiceImpl implements VotingService, UserVotingsDeletionService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VotePersistenceService voteService;

	@Autowired
	private UserPersistenceService userService;

	@Autowired
	private ValidationService validationService;

	@Override
	public VoteDTO create(VotingDTO data) {

		log.debug("create voting from data: " + data.toString());

		validationService.validateVotingData(data);
		log.debug("data passed validation");

		VoteDTO vote = voteService.get(data.getVoteId());
		UserDTO user = userService.getById(data.getUserId());

		vote.getOptions().forEach(option -> {
			if (option.getId().equals(data.getOptionId())) {
				option.incrementCounter();
			}
		});

		vote.setEditable(false);

		VoteDTO persistedVote = voteService.update(vote);
		log.debug("voting for vote with id [" + persistedVote.getId() + "] updated");

		SelectionDTO selection = new SelectionDTO(data.getVoteId(), data.getOptionId());
		user.getSelections().add(selection);

		userService.update(user);
		log.debug("voting for user with id [" + user.getId() + "] updated");

		OperationResult result = new OperationResult(ResultState.SUCCESS, Optional.of("vote counted"));
		persistedVote.setOperationResult(result);

		return persistedVote;
	}

	@Override
	public VoteDTO update(VotingDTO data) {
		log.debug("update voting from data: " + data.toString());

		validationService.validateVotingData(data);
		log.debug("data passed validation");

		VoteDTO vote = voteService.get(data.getVoteId());
		UserDTO user = userService.getById(data.getUserId());

		Iterator<SelectionDTO> iterator = user.getSelections().iterator();

		String oldValue = "";
		while (iterator.hasNext()) {
			SelectionDTO selection = iterator.next();
			if (selection.getVoteId().equals(data.getVoteId())) {
				oldValue = selection.getVotedOptionId();
				iterator.remove();
			}
		}

		SelectionDTO selection = new SelectionDTO(data.getVoteId(), data.getOptionId());
		user.getSelections().add(selection);
		userService.update(user);
		log.debug("voting for user with id [" + user.getId() + "] updated");

		final String finalOldValue = oldValue;
		vote.getOptions().forEach(option -> {
			if (option.getId().equals(data.getOptionId())) {
				option.incrementCounter();
			}
			if (option.getId().equals(finalOldValue)) {
				option.decrementCounter();
			}
		});

		VoteDTO persistedVote = voteService.update(vote);
		log.debug("voting for vote with id [" + persistedVote.getId() + "] updated");

		OperationResult result = new OperationResult(ResultState.SUCCESS, Optional.of("vote counted"));
		persistedVote.setOperationResult(result);

		return persistedVote;
	}

	@Override
	public OperationResult deleteVotings(String userId) {

		log.debug("remove votings from user [" + userId + "]");

		validationService.validateEntityId(userId);
		log.debug("data passed validation");

		UserDTO user = userService.getById(userId);
		Iterator<SelectionDTO> iterator = user.getSelections().iterator();

		while (iterator.hasNext()) {
			SelectionDTO selection = iterator.next();

			VoteDTO vote = voteService.get(selection.getVoteId());

			vote.getOptions().forEach(option -> {
				if (option.getId().equals(selection.getVotedOptionId())) {
					option.decrementCounter();
				}
			});

			voteService.update(vote);
		}

		log.debug("all votings from user with id [" + userId + "] deleted");

		return new OperationResult(ResultState.SUCCESS, Optional.of("votings deleted counted"));
	}
}
