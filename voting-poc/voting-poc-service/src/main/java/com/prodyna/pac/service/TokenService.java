package com.prodyna.pac.service;

import com.prodyna.pac.dto.UserDTO;

public interface TokenService {

	public String generateToken(UserDTO user);

	public UserDTO parseUser(String token);

}
