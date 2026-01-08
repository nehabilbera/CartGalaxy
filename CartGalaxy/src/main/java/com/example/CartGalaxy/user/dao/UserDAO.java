package com.example.CartGalaxy.user.dao;

import com.example.CartGalaxy.user.exception.PasswordNotMatchException;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.*;

import java.sql.SQLException;

public interface UserDAO {
    UserDTO createUser(CreateUserDTO userDTO) throws SQLException, UserNotFoundException;
    UserDTO getUserById(int user_id) throws SQLException, UserNotFoundException;
    UserDTO getUserByEmail(String user_email) throws SQLException, UserNotFoundException;
    Boolean validateUser(LoginUserDTO userDTO) throws SQLException;
    void changePassword(UserChangePasswordDTO userChangePasswordDTO, String user_email) throws PasswordNotMatchException, SQLException;
}
