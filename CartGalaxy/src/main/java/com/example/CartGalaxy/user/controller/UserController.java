package com.example.CartGalaxy.user.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.user.exception.*;
import com.example.CartGalaxy.user.model.*;
import com.example.CartGalaxy.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public UserDTO userLogin(@RequestBody LoginUserDTO loginUserDTO, HttpSession httpSession) throws UserNotFoundException, SQLException, InvalidUserCredentialException {
        UserDTO user = userService.userLogin(loginUserDTO);
        httpSession.setAttribute("USER_EMAIL", user.getUser_email());
        httpSession.setAttribute("USER_ID", user.getUser_id());
        return user;
    }


    @PostMapping("/logout")
    public String userLogout(HttpSession httpSession){
        Object obj = httpSession.getAttribute("USER_EMAIL");
        if(obj==null) return "User logout successfully!";
        String user_email = obj.toString();
        httpSession.invalidate();
        return "User logout successfully!";
    }

    @GetMapping("/")
    public String user(HttpSession httpSession){
        Object obj = httpSession.getAttribute("USER_EMAIL");
        if(obj!=null){
            return obj.toString();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your are not authorized!");
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO, HttpSession httpSession) throws SQLException, PasswordNotMatchException, InvalidUserCredentialException, PasswordMatchException {
        Object obj = httpSession.getAttribute("USER_EMAIL");
        if(obj!=null){
            userService.changePassword(userChangePasswordDTO, obj.toString());
            return "Password Changed successfully for user : "+obj.toString();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your are not authorized!");
    }
}
