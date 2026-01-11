package com.example.CartGalaxy.stock.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateStockDTO {
    private int product_id;
    private int used_quantity;
}
