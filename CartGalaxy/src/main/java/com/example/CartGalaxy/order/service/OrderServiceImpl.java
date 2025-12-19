package com.example.CartGalaxy.order.service;

import com.example.CartGalaxy.order.dao.OrderDAO;
import com.example.CartGalaxy.order.model.CreateOrderDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public List<OrderDTO> getOrdersList() throws SQLException {
        System.out.println("Service");
        return orderDAO.getOrderList();
    }

    @Override
    public OrderDetailDTO getOrder(String order_id) throws SQLException, ProductNotFoundException {
        return orderDAO.getOrder(order_id);
    }

    @Override
    public OrderDetailDTO createOrder(CreateOrderDTO orderDTO) throws SQLException, ProductNotFoundException {
        return orderDAO.createOrder(orderDTO);
    }
}
