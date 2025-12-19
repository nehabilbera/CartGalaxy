package com.example.CartGalaxy.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStockDTO {
    private int product_id;
    private int total_quantity;
}
