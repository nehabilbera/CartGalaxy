package com.example.CartGalaxy.cart.service;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.cart.model.CartDTO;
import com.example.CartGalaxy.cart.model.CreateCartDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.user.exception.UserNotFoundException;

import java.sql.SQLException;

public interface CartService {
    CartDTO getCart(int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException;
    CartDTO createCart(CreateCartDTO createCartDTO) throws SQLException, UserNotExistsException, ProductNotFoundException, CartNotExistsException, UserNotFoundException;
    OrderDetailDTO checkout(int user_id) throws UserNotFoundException, SQLException, CartNotExistsException, ProductNotFoundException, InsufficientProductException;
}
