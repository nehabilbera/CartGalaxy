package com.example.CartGalaxy.user.service;

import com.example.CartGalaxy.user.dao.UserDAO;
import com.example.CartGalaxy.user.exception.InvalidUserCredentialException;
import com.example.CartGalaxy.user.exception.UserAlreadyExistsException;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.CreateUserDTO;
import com.example.CartGalaxy.user.model.LoginUserDTO;
import com.example.CartGalaxy.user.model.UserDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO userRegistration(CreateUserDTO userDTO) throws SQLException, UserAlreadyExistsException, UserNotFoundException {
        try {
            userDAO.getUserByEmail(userDTO.getUser_email());
            throw new UserAlreadyExistsException("User already exists");
        }
        catch (UserNotFoundException e) {
            return userDAO.createUser(userDTO);
        }
    }

    @Override
    public UserDTO userLogin(LoginUserDTO userDTO) throws UserNotFoundException, SQLException, InvalidUserCredentialException {
        Boolean res = userDAO.validateUser(userDTO);
        if(res==false){
            throw new InvalidUserCredentialException("Invalid Username or Password Credential");
        }
        return userDAO.getUserByEmail(userDTO.getUser_email());
    }
}
