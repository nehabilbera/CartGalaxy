package com.example.CartGalaxy.cart.service;

import com.example.CartGalaxy.cart.dao.CartDAO;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.cart.model.CartDTO;
import com.example.CartGalaxy.cart.model.CreateCartDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class CartServiceImpl implements CartService{

    private final CartDAO cartDAO;

    public CartServiceImpl(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @Override
    public CartDTO getCart(int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException {
        return cartDAO.getCart(user_id);
    }

    @Override
    public CartDTO createCart(CreateCartDTO createCartDTO) throws SQLException, UserNotExistsException, ProductNotFoundException {
        return cartDAO.createCart(createCartDTO);
    }
}
