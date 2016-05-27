package com.prodyna.pac.validation.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.VoteDTO;
import com.prodyna.pac.exception.ValidationException;
import com.prodyna.pac.validation.VoteValidationService;

@Component
public class VoteValidationServiceImpl extends ValidationServiceImpl implements VoteValidationService {

    @Override
    public void validateVoteData(VoteDTO data, boolean isUpdate) {

        if (isUpdate) {
            validateEntityId(data.getId());
        }

        if (StringUtils.isEmpty(data.getTopic())) {
            throw new ValidationException("topic must not be empty");
        }
    }

}
