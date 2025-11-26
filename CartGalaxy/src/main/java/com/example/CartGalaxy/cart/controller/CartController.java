package com.example.CartGalaxy.cart.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.cart.model.Cart;
import com.example.CartGalaxy.cart.model.CartItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @GetMapping
    public ApiResponse<List<Cart>> getCartList(){
        return ApiResponse.success(List.of(), "Get cart List");
    }

    @GetMapping("/{cart_id}")
    public ApiResponse<Cart> getCart(@PathVariable int cart_id){
        return ApiResponse.success(new Cart(), "Get cart having cart id : " + cart_id);
    }

    @PostMapping
    public ApiResponse<List<Cart>> addCart(@RequestBody List<Cart> cart){
        return ApiResponse.success(cart, "Cart added");
    }

    @PutMapping
    public ApiResponse<List<Cart>> updateCartList(@RequestBody List<Cart> update_cart){
        return ApiResponse.success(update_cart, "Updated cart");
    }

    @PutMapping("/{cart_id}")
    public ApiResponse<Cart> updateCart(@RequestBody Cart update_cart, @PathVariable int cart_id){
        return ApiResponse.success(update_cart, "Update cart having cart id : " + cart_id);
    }

    @DeleteMapping("/{cart_id}")
    public ApiResponse<String> deleteCart(@PathVariable int cart_id){
        return ApiResponse.success("Deleted", "Delete cart having cart id : " + cart_id);
    }


//----------------------------------------------------------------------------------------------------------------------


    @GetMapping("/{cart_id}/cartItems")
    public ApiResponse<List<CartItem>> getCartItemList(@PathVariable int cart_id){
        return ApiResponse.success(List.of(), "Get cart items list");
    }

    @GetMapping("/{cart_id}/cartItems/{cartItem_id}")
    public ApiResponse<CartItem> getCartItem(@PathVariable int cart_id, @PathVariable int cartItem_id){
        return ApiResponse.success(new CartItem(), "Get cart item having cartItem id : " + cartItem_id);
    }

    @PostMapping("/{cart_id}/cartItems")
    public ApiResponse<List<CartItem>> addCartItem(@PathVariable int cart_id, @RequestBody List<CartItem> cartItem){
        return ApiResponse.success(cartItem, "Cart items added");
    }

    @PutMapping("/{cart_id}/cartItems")
    public ApiResponse<List<CartItem>> updateCartItemList(@PathVariable int cart_id, @RequestBody List<CartItem> update_cartItem){
        return ApiResponse.success(update_cartItem, "Update cart items");
    }

    @PutMapping("/{cart_id}/cartItems/{cartItem_id}")
    public ApiResponse<CartItem> updateCartItem(@PathVariable int cart_id, @RequestBody CartItem update_cartItem, @PathVariable int cartItem_id){
        return ApiResponse.success(update_cartItem, "Update cart item having cartItem id : " + cartItem_id);
    }

    @DeleteMapping("/{cart_id}/cartItems/{cartItem_id}")
    public ApiResponse<String> deleteCartItem(@PathVariable int cart_id, @PathVariable int cartItem_id){
        return ApiResponse.success("Deleted", "Delete cart item having cartItem id : " + cartItem_id);
    }

}
