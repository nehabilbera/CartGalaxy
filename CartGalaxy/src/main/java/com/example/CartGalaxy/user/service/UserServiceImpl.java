package com.example.CartGalaxy.user.service;

import com.example.CartGalaxy.user.dao.UserDAO;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Boolean getUser(int user_id) throws UserNotFoundException, SQLException {
        return userDAO.getUser(user_id);
    }
}
