package com.prodyna.pac.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.UserDTO;
import com.prodyna.pac.security.Roles;

public interface UserService extends UserDetailsService {

    public UserDTO create(UserDTO data);

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public UserDTO update(UserDTO data);

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public UserDTO get(String email);

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public ListWrapperDTO<UserDTO> getAll();

    @PreAuthorize(value = Roles.HAS_ROLE_USER)
    public OperationResult delete(String id);
}
