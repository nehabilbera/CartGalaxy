package com.example.CartGalaxy.order.service;

import com.example.CartGalaxy.order.model.GetOrderDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    List<OrderDTO> getOrdersList() throws SQLException;
    GetOrderDTO getOrder(int order_id) throws SQLException, ProductNotFoundException;
}
