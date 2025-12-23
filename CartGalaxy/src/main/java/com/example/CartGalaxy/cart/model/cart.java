package com.example.CartGalaxy.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class cart {
    private int cart_id;
    private int user_id;
    private LocalDate created_at;
    private String status;
    private int total_quantity;
    private float total_price_at_purchase;
}
