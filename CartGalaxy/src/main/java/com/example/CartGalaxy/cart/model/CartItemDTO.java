package com.example.CartGalaxy.cart.model;

import com.example.CartGalaxy.product.model.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private ProductDTO product;
    private int quantity;
}

