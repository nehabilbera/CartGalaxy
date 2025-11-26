package com.example.CartGalaxy.model;

public class OrderItem {
    private int orderItem_id;
    private int product_id;
    private int order_id;
    private int quantity;
    private float price_at_purchase;

    public OrderItem() {}

    public OrderItem(int orderItem_id, int product_id, int order_id, int quantity, float price_at_purchase) {
        this.orderItem_id = orderItem_id;
        this.product_id = product_id;
        this.order_id = order_id;
        this.quantity = quantity;
        this.price_at_purchase = price_at_purchase;
    }

    public int getOrderItem_id() {
        return orderItem_id;
    }

    public void setOrderItem_id(int orderItem_id) {
        this.orderItem_id = orderItem_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice_at_purchase() {
        return price_at_purchase;
    }

    public void setPrice_at_purchase(float price_at_purchase) {
        this.price_at_purchase = price_at_purchase;
    }
}
