package com.prodyna.pac.service.impl;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.ResultState;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.persistence.UserPersistenceService;
import com.prodyna.pac.security.Roles;
import com.prodyna.pac.service.UserService;
import com.prodyna.pac.validation.UserValidationService;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserPersistenceService persistenceService;

    @Autowired
    private UserVotingsDeletionService votingService;

    @Autowired
    private UserValidationService validationService;

    @Override
    public UserDTO create(UserDTO data) {

        log.debug("create user from data: " + data.toString());

        validationService.validateUserData(data);
        log.debug("data passed validation");

        data.setPassword(hashPassword(data.getPassword()));

        UserDTO persisted = persistenceService.create(data);
        log.debug("user created with id [" + persisted.getId() + "]");

        OperationResult result = new OperationResult(ResultState.SUCCESS, Optional.of("entity created"));
        persisted.setOperationResult(result);

        return persisted;
    }

    @Override
    public UserDTO update(UserDTO data) {

        log.debug("update user from data: " + data.toString());

        validationService.validateEntityId(data.getId());
        log.debug("data passed validation");

        UserDTO persisted = persistenceService.getById(data.getId());

        if (changed(persisted.getFirstName(), data.getFirstName())) {
            persisted.setFirstName(data.getFirstName());
        }

        if (changed(persisted.getLastName(), data.getLastName())) {
            persisted.setLastName(data.getLastName());
        }

        if (changed(persisted.getEmail(), data.getEmail())) {
            persisted.setEmail(data.getEmail());
        }

        if (changedPassword(persisted.getPassword(), data.getPassword())) {
            persisted.setPassword(hashPassword(data.getPassword()));
        }

        persisted.setAdministrator(data.isAdministrator());
        log.debug("user with new data: " + data.toString());

        if (isAuthorized(persisted.getId())) {
            UserDTO updated = persistenceService.update(persisted);
            log.debug("user updated with id [" + updated.getId() + "]");

            updated.setOperationResult(new OperationResult(ResultState.SUCCESS, Optional.empty()));
            return updated;
        } else {
            log.info("entity with id [" + data.getId() + "] is not editable");
            OperationResult result = new OperationResult(ResultState.FAILURE, Optional.of("entity is not editable"));
            data.setOperationResult(result);
            return data;
        }
    }

    @Override
    public UserDTO get(String email) {

        log.debug("find user from email [" + email + "]");

        validationService.validateEmail(email);
        log.debug("data passed validation");

        UserDTO persisted = persistenceService.getByEmail(email);
        log.debug("returning user with id [" + persisted.getId() + "]");

        OperationResult result = new OperationResult(ResultState.SUCCESS, Optional.empty());
        persisted.setOperationResult(result);

        return persisted;
    }

    @Override
    public ListWrapperDTO<UserDTO> getAll() {

        log.debug("find all users");

        ListWrapperDTO<UserDTO> dto = new ListWrapperDTO<>(persistenceService.getAll());
        log.debug("returning " + dto.getList().size() + " user");

        dto.setOperationResult(new OperationResult(ResultState.SUCCESS, Optional.empty()));
        return dto;
    }

    @Override
    public OperationResult delete(String id) {

        log.debug("delete user with id [" + id + "]");

        validationService.validateEntityId(id);
        log.debug("data passed validation");

        if ((!persistenceService.userExists(id)) && isAuthorized(id)) {

            OperationResult result = votingService.deleteVotings(id);
            if (!result.getState().equals(ResultState.SUCCESS)) {
                return new OperationResult(ResultState.FAILURE, Optional.of("was not able to remove the votings of the user"));
            }

            persistenceService.delete(id);

            log.debug("user with id [" + id + "] deleted");
            return new OperationResult(ResultState.SUCCESS, Optional.empty());
        } else {
            log.info("entity with id [" + id + "] is not editable");
            return new OperationResult(ResultState.FAILURE, Optional.of("entity is not deletable"));
        }
    }

    private boolean changed(String oldValue, String newValue) {

        if (StringUtils.hasText(newValue) && !oldValue.equals(newValue)) {
            return true;
        }
        return false;
    }

    private boolean changedPassword(String oldValue, String newValue) {

        if (StringUtils.isEmpty(newValue)) {
            return false;
        }
        return !checkPassword(newValue, oldValue);
    }

    private final String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private final boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

    private boolean isAuthorized(String userId) {

        String authenticatedUsers = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userId.equals(authenticatedUsers)) {
            return true;
        } else {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(Roles.ROLE_ADMINISTRATOR)) {
                    return true;
                }
            }
            return false;
        }
    }
}
