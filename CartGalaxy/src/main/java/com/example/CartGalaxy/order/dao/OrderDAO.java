package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.order.model.GetOrderDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    List<OrderDTO> getOrderList() throws SQLException;
    GetOrderDTO getOrder(int order_id) throws SQLException, ProductNotFoundException;
}
