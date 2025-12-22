package com.example.CartGalaxy.order.service;

import com.example.CartGalaxy.order.model.CreateOrderDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    List<OrderDTO> getOrdersList() throws SQLException;
    OrderDetailDTO getOrder(String order_id) throws SQLException, ProductNotFoundException, InsufficientProductException;
    OrderDetailDTO createOrder(CreateOrderDTO orderDTO) throws SQLException, ProductNotFoundException, InsufficientProductException;
}
