package com.example.CartGalaxy.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StockDTO {
    private int product_id;
    private int available_quantity;
    private int total_quantity;
}
