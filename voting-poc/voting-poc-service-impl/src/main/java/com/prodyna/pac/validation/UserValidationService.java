package com.prodyna.pac.validation;

import com.prodyna.pac.dto.UserDTO;

public interface UserValidationService extends ValidationService {

    public void validateUserData(UserDTO data);

    public void validateEmail(String email);

    public void validatePassword(String password);
}
