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
import com.example.CartGalaxy.user.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrderList(HttpSession httpSession) throws SQLException, OrderNotExistsForExistingUser, UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        int user_id = Integer.parseInt(obj.toString());
        ApiResponse<List<OrderDTO>> res = ApiResponse.success(orderService.getOrdersList(user_id), "Get Order list");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{order_id}")
    public ResponseEntity<ApiResponse<OrderDetailDTO>> getOrder(@PathVariable String order_id, HttpSession httpSession) throws SQLException, ProductNotFoundException, InsufficientProductException, StockNotPresentForExistingProductException, InvalidOrderIdException, UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        int user_id = Integer.parseInt(obj.toString());
        ApiResponse<OrderDetailDTO> res = ApiResponse.success(orderService.getOrder(order_id, user_id), "Get order of order_id : " + order_id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
