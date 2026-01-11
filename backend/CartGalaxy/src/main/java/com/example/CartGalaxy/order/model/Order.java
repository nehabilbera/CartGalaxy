package com.example.CartGalaxy.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order {
    private int order_id;
    private int user_id;
    private LocalDate ordered_date;
    private String status;
    private float transaction_amount;
}
