package com.example.CartGalaxy.user.dao;

import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.CreateUserDTO;
import com.example.CartGalaxy.user.model.LoginUserDTO;
import com.example.CartGalaxy.user.model.User;
import com.example.CartGalaxy.user.model.UserDTO;

import java.sql.SQLException;

public interface UserDAO {
    UserDTO createUser(CreateUserDTO userDTO) throws SQLException, UserNotFoundException;
    UserDTO getUserById(int user_id) throws SQLException, UserNotFoundException;
    UserDTO getUserByEmail(String user_email) throws SQLException, UserNotFoundException;
    Boolean validateUser(LoginUserDTO userDTO) throws SQLException;
}
