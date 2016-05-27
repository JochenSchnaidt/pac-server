package com.prodyna.pac.service;

import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.dto.VotingDTO;

public interface VotingService {

    public VoteDTO create(VotingDTO data);

    public VoteDTO update(VotingDTO data);

}
