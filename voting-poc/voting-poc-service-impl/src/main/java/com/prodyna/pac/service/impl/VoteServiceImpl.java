package com.prodyna.pac.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.ResultState;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.persistence.VotePersistenceService;
import com.prodyna.pac.service.VoteService;
import com.prodyna.pac.validation.VoteValidationService;

@Service
public class VoteServiceImpl implements VoteService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VotePersistenceService persistenceService;

	@Autowired
	private VoteValidationService validationService;

	@Override
	public VoteDTO create(VoteDTO data) {

		log.debug("create vote from data: " + data.toString());

		validationService.validateVoteData(data, false);
        log.debug("data passed validation");

        VoteDTO persisted = persistenceService.create(data);
        log.debug("vote created with id [" + persisted.getId() + "]");

        persisted.setOperationResult(new OperationResult(ResultState.SUCCESS, Optional.of("entity created")));

        return persisted;
    }

    @Override
    public VoteDTO update(VoteDTO data) {

        log.debug("update vote from data: ", data);

        validationService.validateVoteData(data, true);
        log.debug("data passed validation");

        VoteDTO persisted = persistenceService.get(data.getId());

        if (persisted.isEditable()) {

            if (changed(persisted.getTopic(), data.getTopic())) {
                persisted.setTopic(data.getTopic());
            }

            if (changed(persisted.getDescription(), data.getDescription())) {
                persisted.setDescription(data.getDescription());
            }

            if (persisted.isEditable() != data.isEditable()) {
                persisted.setEditable(data.isEditable());
            }

            persisted.getOptions().clear();
            persisted.getOptions().addAll(data.getOptions());

            VoteDTO updated = persistenceService.update(data);
            log.debug("vote updated with id [" + updated.getId() + "]");
            updated.setOperationResult(new OperationResult(ResultState.SUCCESS, Optional.empty()));
            return updated;
        } else {
            log.info("entity with id [" + persisted.getId() + "] is not editable");
            persisted.setOperationResult(new OperationResult(ResultState.FAILURE, Optional.of("entity is not editable")));
            return persisted;
        }
    }

    @Override
    public VoteDTO get(String id) {

        log.debug("find vote from id [" + id + "]");

        validationService.validateEntityId(id);
        log.debug("data passed validation");

        VoteDTO persisted = persistenceService.get(id);
        log.debug("returning vote with id [" + persisted.getId() + "]");

        persisted.setOperationResult(new OperationResult(ResultState.SUCCESS, Optional.empty()));

        return persisted;
    }

    @Override
    public ListWrapperDTO<VoteDTO> getAllByUser(String userId) {

        log.debug("find all votes from user [" + userId + "]");

        validationService.validateEntityId(userId);
        ListWrapperDTO<VoteDTO> dto = new ListWrapperDTO<>(persistenceService.getAllByUser(userId));
        log.debug("returning " + dto.getList().size() + " votes");

        dto.setOperationResult(new OperationResult(ResultState.SUCCESS, Optional.empty()));
        return dto;
    }

    @Override
    public OperationResult delete(String id) {

        log.debug("delete vote with id [" + id + "]");

        validationService.validateEntityId(id);
        log.debug("data passed validation");

        VoteDTO persisted = persistenceService.get(id);

        if (persisted.isEditable()) {
            persistenceService.delete(id);
            log.debug("vote with id [" + id + "] deleted");
            return new OperationResult(ResultState.SUCCESS, Optional.empty());
        } else {
            log.info("entity with id [" + id + "] is not editable");
            return new OperationResult(ResultState.FAILURE, Optional.of("entity is not editable"));
        }
    }

    private boolean changed(String oldValue, String newValue) {

        if (StringUtils.hasText(newValue) && !oldValue.equals(newValue)) {
            return true;
        }
        return false;
    }

	@Override
	public ListWrapperDTO<VoteDTO> getAll() {

        log.debug("find all votes");

        ListWrapperDTO<VoteDTO> dto = new ListWrapperDTO<>(persistenceService.getAll());
        log.debug("returning " + dto.getList().size() + " user");

        dto.setOperationResult(new OperationResult(ResultState.SUCCESS, Optional.empty()));
        return dto;
		
	}
}
