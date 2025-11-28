package com.example.CartGalaxy.order.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.order.model.GetOrderDTO;
import com.example.CartGalaxy.order.model.Order;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.order.model.OrderItem;
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
        return ApiResponse.success(orderService.getOrdersList(), "Get Order list");
    }

    @GetMapping("/{order_id}")
    public ApiResponse<GetOrderDTO> getOrder(@PathVariable int order_id) throws SQLException, ProductNotFoundException {
        return ApiResponse.success(orderService.getOrder(order_id), "Get order of order_id : " + order_id);
    }
}
