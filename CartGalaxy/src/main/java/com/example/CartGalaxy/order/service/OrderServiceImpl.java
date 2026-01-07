package com.example.CartGalaxy.order.service;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.order.dao.OrderDAO;
import com.example.CartGalaxy.order.model.CreateOrderDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
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
        return orderDAO.getOrderList();
    }

    @Override
    public OrderDetailDTO getOrder(String order_id) throws SQLException, ProductNotFoundException, InsufficientProductException {
        return orderDAO.getOrder(order_id);
    }

    @Override
    public OrderDetailDTO createOrder(CreateOrderDTO orderDTO, int user_id) throws SQLException, ProductNotFoundException, InsufficientProductException, CartNotExistsException, UserNotExistsException {
        return orderDAO.createOrder(orderDTO, user_id);
    }
}
