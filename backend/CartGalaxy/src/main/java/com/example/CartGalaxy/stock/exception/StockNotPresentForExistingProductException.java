package com.example.CartGalaxy.stock.exception;

public class StockNotPresentForExistingProductException extends Exception{
    public StockNotPresentForExistingProductException(String message) {
        super(message);
    }
}
