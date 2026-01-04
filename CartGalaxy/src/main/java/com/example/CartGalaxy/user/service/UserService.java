package com.example.CartGalaxy.user.service;

import com.example.CartGalaxy.user.exception.InvalidUserCredentialException;
import com.example.CartGalaxy.user.exception.UserAlreadyExistsException;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.CreateUserDTO;
import com.example.CartGalaxy.user.model.LoginUserDTO;
import com.example.CartGalaxy.user.model.User;
import com.example.CartGalaxy.user.model.UserDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public interface UserService {
    UserDTO userRegistration(CreateUserDTO userDTO) throws SQLException, UserNotFoundException, UserAlreadyExistsException;
    UserDTO userLogin(LoginUserDTO userDTO) throws UserNotFoundException, SQLException, InvalidUserCredentialException;
}
