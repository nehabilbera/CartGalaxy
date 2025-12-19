package com.example.CartGalaxy.order.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {
    private int user_id;
    private List<CreateOrderItemDTO> order_items;
}
