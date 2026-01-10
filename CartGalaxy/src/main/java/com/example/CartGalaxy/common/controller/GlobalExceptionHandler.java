package com.example.CartGalaxy.common.controller;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.order.Exception.InvalidOrderIdException;
import com.example.CartGalaxy.order.Exception.OrderNotExistsForExistingUser;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import com.example.CartGalaxy.user.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartNotExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handlerCartNotExistsException(CartNotExistsException err){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handlerUserNotExistsException(UserNotExistsException err){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(InvalidOrderIdException.class)
    public ResponseEntity<ApiResponse<Object>> handlerInvalidOrderIdException(InvalidOrderIdException err){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(OrderNotExistsForExistingUser.class)
    public ResponseEntity<ApiResponse<Object>> handlerOrderNotExistsForExistingUser(OrderNotExistsForExistingUser err){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handlerProductNotFoundException(ProductNotFoundException err){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(InsufficientProductException.class)
    public ResponseEntity<ApiResponse<Object>> handlerInsufficientProductException(InsufficientProductException err){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(StockNotPresentForExistingProductException.class)
    public ResponseEntity<ApiResponse<Object>> handlerStockNotPresentForExistingProductException(StockNotPresentForExistingProductException err){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(InvalidUserCredentialException.class)
    public ResponseEntity<ApiResponse<Object>> handlerInvalidUserCredentialException(InvalidUserCredentialException err){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(PasswordMatchException.class)
    public ResponseEntity<ApiResponse<Object>> handlerPasswordMatchException(PasswordMatchException err){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ApiResponse<Object>> handlerPasswordNotMatchException(PasswordNotMatchException err){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handlerUserAlreadyExistsException(UserAlreadyExistsException err){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handlerUserNotFoundException(UserNotFoundException err){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(null,err.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnauthorizedException(UnauthorizedException err){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(null, err.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception err) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(null, "Internal Server Error"));
    }
}
