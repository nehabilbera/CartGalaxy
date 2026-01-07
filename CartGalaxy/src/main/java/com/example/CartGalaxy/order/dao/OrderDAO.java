package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.order.model.CreateOrderDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    List<OrderDTO> getOrderList() throws SQLException;
    OrderDetailDTO getOrder(String order_id) throws SQLException, ProductNotFoundException, InsufficientProductException;

    OrderDetailDTO createOrder(CreateOrderDTO orderDTO, int user_id) throws SQLException, ProductNotFoundException, InsufficientProductException, CartNotExistsException, UserNotExistsException;
}
