package com.example.CartGalaxy.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    private String order_id;
    private LocalDate ordered_date;
    private String status;
    private float transaction_amount;
}
