package com.example.CartGalaxy.user.service;

import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public interface UserService {
    Boolean getUser(int user_id) throws UserNotFoundException, SQLException;
}
