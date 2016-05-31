package com.prodyna.pac.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.security.Roles;

public interface VoteService {

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public VoteDTO create(VoteDTO data);

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public VoteDTO update(VoteDTO data);

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public VoteDTO get(String id);

    public ListWrapperDTO<VoteDTO> getAllByUser(String userId);

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public OperationResult delete(String id);
}
