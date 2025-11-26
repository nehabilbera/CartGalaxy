package com.example.CartGalaxy.product.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    private float selling_price;
    private float discounted_price;
    private String description;
    private String brand;
    private String category;
    private String image;
    private Boolean availability;
}
