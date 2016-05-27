package com.prodyna.pac.service;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.VoteDTO;

public interface VoteService {

    public VoteDTO create(VoteDTO data);

    public VoteDTO update(VoteDTO data);

    public VoteDTO get(String id);

    public ListWrapperDTO<VoteDTO> getAllByUser(String userId);

    public OperationResult delete(String id);
}
