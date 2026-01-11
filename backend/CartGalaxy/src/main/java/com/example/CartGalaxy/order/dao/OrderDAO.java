package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.order.Exception.InvalidOrderIdException;
import com.example.CartGalaxy.order.Exception.OrderNotExistsForExistingUser;
import com.example.CartGalaxy.order.model.CreateOrderDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    List<OrderDTO> getOrderList(int user_id) throws SQLException, OrderNotExistsForExistingUser;

    OrderDetailDTO getOrder(String order_id, int user_id) throws SQLException, ProductNotFoundException, InsufficientProductException, StockNotPresentForExistingProductException, InvalidOrderIdException;

    OrderDetailDTO createOrder(CreateOrderDTO orderDTO, int user_id) throws SQLException, ProductNotFoundException, InsufficientProductException, CartNotExistsException, UserNotExistsException, StockNotPresentForExistingProductException,InvalidOrderIdException;
}
