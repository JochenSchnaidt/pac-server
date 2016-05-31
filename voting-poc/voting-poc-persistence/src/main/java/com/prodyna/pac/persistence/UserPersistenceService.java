package com.prodyna.pac.persistence;

import java.util.List;

import com.prodyna.pac.dto.UserDTO;

public interface UserPersistenceService {

    public UserDTO create(UserDTO data);

    public UserDTO update(UserDTO data);

    public UserDTO getByEmail(String email);
    public UserDTO getById(String id);

    public List<UserDTO> getAll();

    public void delete(String id);
    
    public boolean userExists(String id);

}
