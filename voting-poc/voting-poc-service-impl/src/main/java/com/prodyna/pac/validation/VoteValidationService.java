package com.prodyna.pac.validation;

import com.prodyna.pac.dto.VoteDTO;

public interface VoteValidationService extends ValidationService {

    public void validateVoteData(VoteDTO data, boolean isUpdate);

}
