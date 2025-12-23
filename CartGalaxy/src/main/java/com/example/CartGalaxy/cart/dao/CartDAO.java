package com.example.CartGalaxy.cart.dao;

import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.cart.model.CartDTO;
import com.example.CartGalaxy.cart.model.CreateCartDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;

import java.sql.SQLException;

public interface CartDAO {
    CartDTO getCart(int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException;
    CartDTO createCart(CreateCartDTO createCartDTO) throws SQLException, ProductNotFoundException, UserNotExistsException;
}
