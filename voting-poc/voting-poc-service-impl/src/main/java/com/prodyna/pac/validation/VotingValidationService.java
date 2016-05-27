package com.prodyna.pac.validation;

import com.prodyna.pac.dto.VotingDTO;

public interface VotingValidationService extends ValidationService {

    public void validateVotingData(VotingDTO data);

}
