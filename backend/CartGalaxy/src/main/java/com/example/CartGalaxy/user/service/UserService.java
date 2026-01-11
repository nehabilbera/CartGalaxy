package com.example.CartGalaxy.user.service;

import com.example.CartGalaxy.user.exception.*;
import com.example.CartGalaxy.user.model.*;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public interface UserService {
    UserDTO userRegistration(CreateUserDTO userDTO) throws SQLException, UserNotFoundException, UserAlreadyExistsException;
    UserDTO userLogin(LoginUserDTO userDTO) throws UserNotFoundException, SQLException, InvalidUserCredentialException;
    void changePassword(UserChangePasswordDTO userChangePasswordDTO, String user_email) throws SQLException, PasswordNotMatchException, InvalidUserCredentialException, PasswordMatchException;
}
