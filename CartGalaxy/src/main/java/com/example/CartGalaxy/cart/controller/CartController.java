package com.example.CartGalaxy.cart.controller;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.cart.model.CartDTO;
import com.example.CartGalaxy.cart.model.CreateCartDTO;
import com.example.CartGalaxy.cart.service.CartService;
import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{user_id}")
    public ApiResponse<CartDTO> getCart(@PathVariable int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException {
        return ApiResponse.success(cartService.getCart(user_id), "Cart for user having user id : " + user_id);
    }

    @PostMapping
    public ApiResponse<CartDTO> createCart(@RequestBody CreateCartDTO createCartDTO) throws SQLException, UserNotExistsException, ProductNotFoundException, CartNotExistsException, UserNotFoundException {
        return ApiResponse.success(cartService.createCart(createCartDTO), "CartItem added successfully!");
    }

    @GetMapping("/checkout/{user_id}")
    public ApiResponse<OrderDetailDTO> checkout(@PathVariable int user_id) throws UserNotFoundException, CartNotExistsException, SQLException, InsufficientProductException, ProductNotFoundException {
        return ApiResponse.success(cartService.checkout(user_id), "Checkout user successfully having user id : "+user_id);
    }
}
