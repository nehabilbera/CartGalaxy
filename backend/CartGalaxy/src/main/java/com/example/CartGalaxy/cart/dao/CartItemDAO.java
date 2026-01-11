package com.example.CartGalaxy.cart.dao;

import com.example.CartGalaxy.cart.model.CartItemDTO;
import com.example.CartGalaxy.cart.model.CreateCartItemDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface CartItemDAO {
    List<CartItemDTO> getAllCartItems(int cart_id) throws SQLException, ProductNotFoundException, InsufficientProductException, StockNotPresentForExistingProductException;
    void deleteCartItems(int user_id) throws SQLException;
}
