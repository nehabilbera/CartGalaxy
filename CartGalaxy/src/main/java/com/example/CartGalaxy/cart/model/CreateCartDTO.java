package com.example.CartGalaxy.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartDTO {
    private int user_id;
    List<CreateCartItemDTO> createCartItemDTOList;
}
