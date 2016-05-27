package com.prodyna.pac.validation.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.VotingDTO;
import com.prodyna.pac.exception.ValidationException;
import com.prodyna.pac.validation.VotingValidationService;

@Component
public class VotingValidationServiceImpl extends ValidationServiceImpl implements VotingValidationService {

    @Override
    public void validateVotingData(VotingDTO data) {

        if (StringUtils.isEmpty(data.getVoteId())) {
            throw new ValidationException("vote id must not be empty");
        }

        if (StringUtils.isEmpty(data.getUserId())) {
            throw new ValidationException("user id must not be empty");
        }

        if (StringUtils.isEmpty(data.getOptionId())) {
            throw new ValidationException("option id must not be empty");
        }

    }

}
