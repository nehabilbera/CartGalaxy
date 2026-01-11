package com.example.CartGalaxy.order.service;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.order.Exception.InvalidOrderIdException;
import com.example.CartGalaxy.order.Exception.OrderNotExistsForExistingUser;
import com.example.CartGalaxy.order.dao.OrderDAO;
import com.example.CartGalaxy.order.model.CreateOrderDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
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
    public List<OrderDTO> getOrdersList(int user_id) throws SQLException, OrderNotExistsForExistingUser {
        return orderDAO.getOrderList(user_id);
    }

    @Override
    public OrderDetailDTO getOrder(String order_id, int user_id) throws SQLException, ProductNotFoundException, InsufficientProductException, StockNotPresentForExistingProductException, InvalidOrderIdException {
        return orderDAO.getOrder(order_id, user_id);
    }

    @Override
    public OrderDetailDTO createOrder(CreateOrderDTO orderDTO, int user_id) throws SQLException, ProductNotFoundException, InsufficientProductException, CartNotExistsException, UserNotExistsException, StockNotPresentForExistingProductException, InvalidOrderIdException {
        return orderDAO.createOrder(orderDTO, user_id);
    }
}
