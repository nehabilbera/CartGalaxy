package com.example.CartGalaxy.user.service;

import com.example.CartGalaxy.user.dao.UserDAO;
import com.example.CartGalaxy.user.exception.*;
import com.example.CartGalaxy.user.model.CreateUserDTO;
import com.example.CartGalaxy.user.model.LoginUserDTO;
import com.example.CartGalaxy.user.model.UserChangePasswordDTO;
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
        Boolean res = userDAO.validateUser(userDTO.getUser_email(), userDTO.getUser_password());
        if(res==false){
            throw new InvalidUserCredentialException("Invalid Username or Password Credential");
        }
        return userDAO.getUserByEmail(userDTO.getUser_email());
    }


    @Override
    public void changePassword(UserChangePasswordDTO userChangePasswordDTO, String user_email) throws SQLException, PasswordNotMatchException, InvalidUserCredentialException, PasswordMatchException {
        Boolean validateUser = userDAO.validateUser(user_email, userChangePasswordDTO.getOld_password());
        if(!validateUser) throw new InvalidUserCredentialException("Old password is incorrect");

        if(!userChangePasswordDTO.getNew_password().equals(userChangePasswordDTO.getConfirm_password()))
            throw new PasswordNotMatchException("Password does not match!");

        if(userChangePasswordDTO.getNew_password().equals(userChangePasswordDTO.getOld_password()))
            throw new PasswordMatchException("Old password is same as new password");

        userDAO.changePassword(userChangePasswordDTO, user_email);
    }
}
