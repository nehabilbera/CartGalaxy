package com.example.CartGalaxy.order.Exception;

public class OrderNotExistsForExistingUser extends Exception{
    public OrderNotExistsForExistingUser(String message) {
        super(message);
    }
}
