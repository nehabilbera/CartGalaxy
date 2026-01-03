package com.example.CartGalaxy.user.dao;

import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.User;

import java.sql.SQLException;

public interface UserDAO {
    Boolean getUser(int user_id) throws SQLException, UserNotFoundException;
}
