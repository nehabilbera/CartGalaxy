package com.example.CartGalaxy.order.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.order.model.CreateOrderDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.order.service.OrderService;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
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

    //todo: show order list according to user_id
    @GetMapping
    public ApiResponse<List<OrderDTO>> getOrderList(HttpSession httpSession) throws SQLException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        return ApiResponse.success(orderService.getOrdersList(), "Get Order list");
    }

    //todo: show order according to user_id
    @GetMapping("/{order_id}")
    public ApiResponse<OrderDetailDTO> getOrder(@PathVariable String order_id, HttpSession httpSession) throws SQLException, ProductNotFoundException, InsufficientProductException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        return ApiResponse.success(orderService.getOrder(order_id), "Get order of order_id : " + order_id);
    }
}
