package com.prodyna.pac.persistence.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodyna.pac.dto.OptionDTO;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.exception.EntityNotFoundException;
import com.prodyna.pac.exception.ProcessingException;
import com.prodyna.pac.persistence.VotePersistenceService;
import com.prodyna.pac.persistence.entities.User;
import com.prodyna.pac.persistence.entities.Vote;
import com.prodyna.pac.persistence.repository.VoteRepository;

@Service
public class VotePersistenceServiceImpl implements VotePersistenceService {

    private final Logger   log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VoteRepository repository;

    @Override
    public VoteDTO create(VoteDTO data) {

    	 log.info("create reached");
    	
        //@formatter:off
	    Vote vote = new Vote.Builder()
	            .topic(data.getTopic())
	            .description(data.getDescription())
	            .editable(data.isEditable())
	            .createdBy(data.getCreatedBy())
	            .options(data.getOptions())
                .build();
        //@formatter:on

        Vote persisted = repository.insert(vote);

        if (null == persisted) {
            throw new ProcessingException("No vote created with topic: " + data.getTopic());
        }

        log.info("new vote created: " + persisted.toString());

        return convert(persisted);
    }

    @Override
    public VoteDTO update(VoteDTO data) {

        // @formatter:off
        Vote vote = new Vote.Builder()
                .id(data.getId())
                .topic(data.getTopic())
                .description(data.getDescription())
                .editable(data.isEditable())
                .createdBy(data.getCreatedBy())
                .options(data.getOptions())
                .creationDate(data.getCreationDate())
                .build();
        //@formatter:on

        Vote persisted = repository.save(vote);

        if (null == persisted) {
            throw new ProcessingException("No vote updated with id: " + data.getId());
        }

        log.info("vote updated: " + persisted.toString());

        return convert(persisted);
    }

    @Override
    public VoteDTO get(String id) {

        Vote vote = repository.findOne(id);

        if (null == vote) {
            throw new EntityNotFoundException("No vote found with id: " + id);
        }

        log.info("vote found: " + vote.toString());

        return convert(vote);
    }

    @Override
    public List<VoteDTO> getAllByUser(String userId) {

        List<VoteDTO> list = new ArrayList<>();

        List<Vote> allVotes = repository.findAll();

        allVotes.forEach(vote -> list.add(convert(vote)));

        return list;
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    private VoteDTO convert(Vote entity) {

        VoteDTO dto = new VoteDTO();
        dto.setId(entity.getId());
        dto.setTopic(entity.getTopic());
        dto.setDescription(entity.getDescription());
        dto.setEditable(entity.isEditable());
        dto.setCreatedBy(entity.getCreatedBy());

        final Set<OptionDTO> set = new HashSet<OptionDTO>();
        if (null != entity.getOptions()) {
            entity.getOptions().forEach(option -> set.add(new OptionDTO(option.getId(), option.getName(), option.getCounter())));
        }
        dto.setOptions(set);

        dto.setCreationDate(entity.getCreationDate());
        return dto;
    }

	@Override
	public List<VoteDTO> getAll() {

        List<Vote> allUsers = repository.findAll();

        List<VoteDTO> list = new ArrayList<VoteDTO>();

        allUsers.forEach(user -> list.add(convert(user)));

        return list;
	}

}
