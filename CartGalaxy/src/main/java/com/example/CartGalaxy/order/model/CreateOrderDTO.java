package com.example.CartGalaxy.order.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {

    private List<CreateOrderItemDTO> order_items;
}
