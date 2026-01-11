package com.example.CartGalaxy.admin.controller;

import com.example.CartGalaxy.admin.model.Admin;
import com.example.CartGalaxy.common.model.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public ApiResponse<List<Admin>> getAdminList(){
        return ApiResponse.success(List.of(), "Get admin list");
    }

    @GetMapping("/{admin_id}")
    public ApiResponse<Admin> getAdmin(@PathVariable int admin_id){
        return ApiResponse.success(new Admin(), "Get admin having admin id : " + admin_id);
    }

    @PostMapping
    public ApiResponse<List<Admin>> addAdmins(@RequestBody List<Admin> admin){
        return ApiResponse.success(admin, "Admin added");
    }

    @PutMapping
    public ApiResponse<List<Admin>> updateAdminList(@RequestBody List<Admin> update_admin){
        return ApiResponse.success(update_admin, "Updated admin");
    }

    @PutMapping("/{admin_id}")
    public ApiResponse<Admin> updateAdmin(@RequestBody Admin update_admin, @PathVariable int admin_id){
        return ApiResponse.success(update_admin, "Update admin having admin id : " + admin_id);
    }

    @DeleteMapping("/{admin_id}")
    public ApiResponse<String> deleteAdmin(@PathVariable int admin_id){
        return ApiResponse.success("Deleted", "Delete admin having admin id : " + admin_id);
    }
}
