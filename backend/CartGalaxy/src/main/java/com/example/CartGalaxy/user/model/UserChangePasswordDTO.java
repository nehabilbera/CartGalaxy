package com.example.CartGalaxy.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordDTO {
    private String old_password;
    private String new_password;
    private String confirm_password;
}
