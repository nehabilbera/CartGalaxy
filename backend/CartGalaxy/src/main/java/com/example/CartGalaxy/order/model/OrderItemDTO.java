package com.example.CartGalaxy.order.model;

import com.example.CartGalaxy.product.model.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private ProductDTO product;
    private int quantity;
    private float price_at_purchase;
}
