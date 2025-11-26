package com.example.CartGalaxy.user.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @GetMapping
    public ApiResponse<List<User>> getUserList(){
        return ApiResponse.success(List.of(), "Get User list");
    }

    @GetMapping("/{user_id}")
    public ApiResponse<User> getUser(@PathVariable int user_id){
        return ApiResponse.success(new User(), "Get user having user id : " + user_id);
    }

    @PostMapping
    public ApiResponse<List<User>> addUsers(@RequestBody List<User> users){
        return ApiResponse.success(users, "Users added");
    }

    @PutMapping
    public ApiResponse<List<User>> updateUserList(@RequestBody List<User> update_users){
        return ApiResponse.success(update_users, "Updated users");
    }

    @PutMapping("/{user_id}")
    public ApiResponse<User> updateUser(@RequestBody User update_user, @PathVariable int user_id){
        return ApiResponse.success(update_user, "Update user having user id : " + user_id);
    }

    @DeleteMapping("/{user_id}")
    public ApiResponse<String> deleteUser(@PathVariable int user_id){
        return ApiResponse.success("Deleted", "Delete user having user id : " + user_id);
    }
}
