package com.example.CartGalaxy.user.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.common.model.SendEmailDTO;
import com.example.CartGalaxy.common.service.EmailService;
import com.example.CartGalaxy.user.exception.*;
import com.example.CartGalaxy.user.model.*;
import com.example.CartGalaxy.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> userRegistration(@RequestBody CreateUserDTO userDTO) throws SQLException, UserNotFoundException, UserAlreadyExistsException {
        UserDTO res = userService.userRegistration(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> userLogin(@RequestBody LoginUserDTO loginUserDTO, HttpSession httpSession) throws UserNotFoundException, SQLException, InvalidUserCredentialException {
        UserDTO user = userService.userLogin(loginUserDTO);
        httpSession.setAttribute("USER_EMAIL", user.getUser_email());
        httpSession.setAttribute("USER_ID", user.getUser_id());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //todo: return one time only
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> userLogout(HttpSession httpSession){
        Object obj = httpSession.getAttribute("USER_EMAIL");
        if(obj==null) return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null, "User logout successfully!"));
        String user_email = obj.toString();
        httpSession.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null, "User logout successfully!"));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<String>> user(HttpSession httpSession) throws UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_EMAIL");
        if(obj!=null){
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null,obj.toString()));
        }
        throw new UnauthorizedException("User is not authorized");
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO, HttpSession httpSession) throws SQLException, PasswordNotMatchException, InvalidUserCredentialException, PasswordMatchException, UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_EMAIL");
        if(obj!=null){
            userService.changePassword(userChangePasswordDTO, obj.toString());
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null, "Password Changed successfully for user : "+obj.toString()));
        }
        throw new UnauthorizedException("User is not authorized");
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<ApiResponse<String>> sendEmail(@RequestBody SendEmailDTO emailDTO){
        emailService.sendEmail(emailDTO);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null, "Email sent successfully!"));
    }
}
