package com.example.CartGalaxy.cart.dao;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.cart.model.CartDTO;
import com.example.CartGalaxy.cart.model.CreateCartDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;

import java.sql.SQLException;

public interface CartDAO {
    CartDTO getCart(int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException, InsufficientProductException;
    CartDTO createCart(CreateCartDTO createCartDTO, int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException, InsufficientProductException;
    CartDTO updateCart(CreateCartDTO createCartDTO, int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException, InsufficientProductException;
    Boolean cartExists(int user_id) throws SQLException, CartNotExistsException;

    String deleteCart(int user_id) throws SQLException;
}
