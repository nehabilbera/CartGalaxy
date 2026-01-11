package com.example.CartGalaxy.product.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    private int product_id;
    private float selling_price;
    private float discounted_price;
    private String product_name;
    private String brand;
    private String category;
    private String image;
    private Boolean availability;
}