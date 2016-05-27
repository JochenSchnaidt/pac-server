package com.prodyna.pac.service;

import com.prodyna.pac.dto.ListWrapperDTO;
import com.prodyna.pac.dto.OperationResult;
import com.prodyna.pac.dto.UserDTO;

public interface UserService {

	public UserDTO create(UserDTO data);

	public UserDTO update(UserDTO data);

	public UserDTO get(String email);

	public ListWrapperDTO<UserDTO> getAll();

	public OperationResult delete(String id);

}
