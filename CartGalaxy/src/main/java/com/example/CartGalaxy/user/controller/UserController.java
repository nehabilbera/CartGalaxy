package com.example.CartGalaxy.user.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.user.exception.InvalidUserCredentialException;
import com.example.CartGalaxy.user.exception.UserAlreadyExistsException;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.CreateUserDTO;
import com.example.CartGalaxy.user.model.LoginUserDTO;
import com.example.CartGalaxy.user.model.User;
import com.example.CartGalaxy.user.model.UserDTO;
import com.example.CartGalaxy.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO userRegistration(@RequestBody CreateUserDTO userDTO) throws SQLException, UserNotFoundException, UserAlreadyExistsException {
        return userService.userRegistration(userDTO);
    }
    @PostMapping("/login")
    public UserDTO userLogin(@RequestBody LoginUserDTO userDTO) throws UserNotFoundException, SQLException, InvalidUserCredentialException {
        return userService.userLogin(userDTO);
    }
    @PostMapping("/logout")
    public UserDTO userLogout(){
        return null;
    }
}
