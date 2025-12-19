package com.example.CartGalaxy.order.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.order.model.CreateOrderDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.order.service.OrderService;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<List<OrderDTO>> getOrderList() throws SQLException {
        System.out.println("Controller");
        return ApiResponse.success(orderService.getOrdersList(), "Get Order list");
    }

    @GetMapping("/{order_id}")
    public ApiResponse<OrderDetailDTO> getOrder(@PathVariable String order_id) throws SQLException, ProductNotFoundException {
        return ApiResponse.success(orderService.getOrder(order_id), "Get order of order_id : " + order_id);
    }

    @PostMapping
    public ApiResponse<OrderDetailDTO> createOrder(@RequestBody CreateOrderDTO order) throws SQLException, ProductNotFoundException {
        return ApiResponse.success(orderService.createOrder(order), "Order created successfully!");
    }
}
