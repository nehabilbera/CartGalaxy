package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.order.model.OrderItemDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDAO {
        List<OrderItemDTO> getOrderItemList(String order_id) throws SQLException, ProductNotFoundException;
}
