package com.example.CartGalaxy.order.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.order.Exception.InvalidOrderIdException;
import com.example.CartGalaxy.order.Exception.OrderNotExistsForExistingUser;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.order.service.OrderService;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ApiResponse<List<OrderDTO>> getOrderList(HttpSession httpSession) throws SQLException, OrderNotExistsForExistingUser {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        int user_id = Integer.parseInt(obj.toString());
        return ApiResponse.success(orderService.getOrdersList(user_id), "Get Order list");
    }

    @GetMapping("/{order_id}")
    public ApiResponse<OrderDetailDTO> getOrder(@PathVariable String order_id, HttpSession httpSession) throws SQLException, ProductNotFoundException, InsufficientProductException, StockNotPresentForExistingProductException, InvalidOrderIdException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        int user_id = Integer.parseInt(obj.toString());
        return ApiResponse.success(orderService.getOrder(order_id, user_id), "Get order of order_id : " + order_id);
    }
}
