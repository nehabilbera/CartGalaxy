package com.example.CartGalaxy.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private String order_id;
    private LocalDate ordered_date;
    private String status;
    private float transaction_amount;
    private List<OrderItemDTO> order_items;
}
