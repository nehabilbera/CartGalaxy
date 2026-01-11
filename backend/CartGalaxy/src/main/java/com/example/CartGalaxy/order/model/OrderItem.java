package com.example.CartGalaxy.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderItem {
    private int orderItem_id;
    private int order_id;
    private int product_id;
    private int quantity;
    private float price_at_purchase;
}