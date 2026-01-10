package com.example.CartGalaxy.cart.controller;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.cart.model.CartDTO;
import com.example.CartGalaxy.cart.model.CreateCartDTO;
import com.example.CartGalaxy.cart.service.CartService;
import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.order.Exception.InvalidOrderIdException;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import com.example.CartGalaxy.user.exception.UnauthorizedException;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<CartDTO>> getCart(HttpSession httpSession) throws SQLException, UnauthorizedException, CartNotExistsException, UserNotExistsException, InsufficientProductException, StockNotPresentForExistingProductException, ProductNotFoundException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        int user_id = Integer.parseInt(obj.toString());
        ApiResponse<CartDTO> res = ApiResponse.success(cartService.getCart(user_id), "Cart for user having user id : " + user_id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartDTO>> createCart(@RequestBody CreateCartDTO createCartDTO, HttpSession httpSession) throws SQLException, UserNotExistsException, ProductNotFoundException, CartNotExistsException, UserNotFoundException, InsufficientProductException, StockNotPresentForExistingProductException, UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        int user_id = Integer.parseInt(obj.toString());
        ApiResponse<CartDTO> res =  ApiResponse.success(cartService.createCart(createCartDTO, user_id), "CartItem added successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/checkout/")
    public ResponseEntity<ApiResponse<OrderDetailDTO>> checkout(HttpSession httpSession) throws UserNotFoundException, CartNotExistsException, SQLException, InsufficientProductException, UserNotExistsException, StockNotPresentForExistingProductException, InvalidOrderIdException, ProductNotFoundException, UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        int user_id = Integer.parseInt(obj.toString());
        ApiResponse<OrderDetailDTO> res = ApiResponse.success(cartService.checkout(user_id), "Checkout user successfully having user id : "+user_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
