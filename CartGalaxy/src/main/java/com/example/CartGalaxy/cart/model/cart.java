package com.example.CartGalaxy.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private int user_id;
    private LocalDate created_at;
    private String status;
}
