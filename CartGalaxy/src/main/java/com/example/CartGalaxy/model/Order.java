package com.example.CartGalaxy.model;

import java.time.LocalDate;

public class Order {
    private int order_id;
    private int user_id;
    private LocalDate ordered_date;
    private String status;
    private float transaction_amount;

    public Order() {
    }

    public Order(int order_id, int user_id, LocalDate ordered_date, String status, float transaction_amount) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.ordered_date = ordered_date;
        this.status = status;
        this.transaction_amount = transaction_amount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDate getOrdered_date() {
        return ordered_date;
    }

    public void setOrdered_date(LocalDate ordered_date) {
        this.ordered_date = ordered_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(float transaction_amount) {
        this.transaction_amount = transaction_amount;
    }
}
