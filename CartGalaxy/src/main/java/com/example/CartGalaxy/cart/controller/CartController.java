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
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public ApiResponse<CartDTO> getCart(HttpSession httpSession) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException, InsufficientProductException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        int user_id = Integer.parseInt(obj.toString());
        return ApiResponse.success(cartService.getCart(user_id), "Cart for user having user id : " + user_id);
    }

    @PostMapping
    public ApiResponse<CartDTO> createCart(@RequestBody CreateCartDTO createCartDTO, HttpSession httpSession) throws SQLException, UserNotExistsException, ProductNotFoundException, CartNotExistsException, UserNotFoundException, InsufficientProductException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized!");
        int user_id = Integer.parseInt(obj.toString());
        return ApiResponse.success(cartService.createCart(createCartDTO, user_id), "CartItem added successfully!");
    }

    @GetMapping("/checkout/")
    public ApiResponse<OrderDetailDTO> checkout(HttpSession httpSession) throws UserNotFoundException, CartNotExistsException, SQLException, InsufficientProductException, ProductNotFoundException, UserNotExistsException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        int user_id = Integer.parseInt(obj.toString());
        return ApiResponse.success(cartService.checkout(user_id), "Checkout user successfully having user id : "+user_id);
    }
}
