package com.example.CartGalaxy.model;

public class Stock {
    private int stock_id;
    private int product_id;
    private int available_quantity;
    private int total_quantity;

    public Stock() {}

    public Stock(int stock_id, int product_id, int available_quantity, int total_quantity) {
        this.stock_id = stock_id;
        this.product_id = product_id;
        this.available_quantity = available_quantity;
        this.total_quantity = total_quantity;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }
}
