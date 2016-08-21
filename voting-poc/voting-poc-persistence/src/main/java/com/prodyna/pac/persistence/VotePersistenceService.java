package com.prodyna.pac.persistence;

import java.util.List;

import com.prodyna.pac.dto.VoteDTO;

public interface VotePersistenceService {

	public VoteDTO create(VoteDTO data);

	public VoteDTO update(VoteDTO data);

	public VoteDTO get(String id);

	public List<VoteDTO> getAllByUser(String userid);

	public void delete(String id);

	public List<VoteDTO> getAll();

	// convenience methods

	// public List<VoteDTO> getAllByUser(String id);

	// public List<VoteDTO> getAllByDate(Optional<Date> start, Optional<Date>
	// end);

	// public List<VoteDTO> getAllByTag(TagDTO... tags);

}
