package com.prodyna.pac.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.dto.VotingDTO;
import com.prodyna.pac.security.Roles;

public interface VotingService {

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public VoteDTO create(VotingDTO data);

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public VoteDTO update(VotingDTO data);

}
