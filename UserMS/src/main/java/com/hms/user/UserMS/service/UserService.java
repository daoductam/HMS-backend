package com.hms.user.UserMS.service;

import com.hms.user.UserMS.dto.UserDTO;
import com.hms.user.UserMS.exception.HmsException;

public interface UserService {
    void registerUser(UserDTO userDTO);
    UserDTO loginUser (UserDTO userDTO);
    UserDTO getUserById(Long id);
    UserDTO updateUser(UserDTO userDTO);
    UserDTO getUser(String email);
}
