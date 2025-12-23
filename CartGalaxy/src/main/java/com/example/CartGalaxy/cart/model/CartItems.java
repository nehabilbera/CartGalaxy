package com.example.CartGalaxy.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItems {
    private int cart_id;
    private int product_id;
    private int quantity;
    private int price_at_purchase;
}
