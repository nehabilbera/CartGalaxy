package com.example.CartGalaxy.order.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.order.model.Order;
import com.example.CartGalaxy.order.model.OrderItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    public ApiResponse<List<Order>> getOrderList(){
        return ApiResponse.success(List.of(), "Get Order list");
    }

    @GetMapping("/{order_id}")
    public ApiResponse<Order> getOrder(@PathVariable int order_id){
        return ApiResponse.success(new Order(), "Get order having order id : " + order_id);
    }

    @PostMapping
    public ApiResponse<List<Order>> addOrders(@RequestBody List<Order> orders){
        return ApiResponse.success(orders, "Orders added");
    }

    @PutMapping
    public ApiResponse<List<Order>> updateOrderList(@RequestBody List<Order> update_orders){
        return ApiResponse.success(update_orders, "Updated Orders");
    }

    @PutMapping("/{order_id}")
    public ApiResponse<Order> updateOrder(@RequestBody Order update_order, @PathVariable int order_id){
        return ApiResponse.success(update_order, "Update order having order id : " + order_id);
    }

    @DeleteMapping("/{order_id}")
    public ApiResponse<String> deleteOrder(@PathVariable int order_id){
        return ApiResponse.success("Deleted", "Delete order having order id : " + order_id);
    }



//    ---------------------------------------------------------------------------------------------------------------

    @GetMapping("/{order_id}/orderItems")
    public ApiResponse<List<OrderItem>> getOrderItemList(@PathVariable int order_id){
        return ApiResponse.success(List.of(), "Get Order Item list");
    }

    @GetMapping("/{order_id}/orderItems/{orderItem_id}")
    public ApiResponse<OrderItem> getOrderItem(@PathVariable int order_id, @PathVariable int orderItem_id){
        return ApiResponse.success(new OrderItem(), "Get order item having orderItem id : " + orderItem_id);
    }

    @PostMapping("/{order_id}/orderItems")
    public ApiResponse<List<OrderItem>> addOrderItems(@PathVariable int order_id, @RequestBody List<OrderItem> orderItems){
        return ApiResponse.success(orderItems, "Order items added");
    }

    @PutMapping("/{order_id}/orderItems")
    public ApiResponse<List<OrderItem>> updateOrderItemList(@PathVariable int order_id, @RequestBody List<OrderItem> update_orderItems){
        return ApiResponse.success(update_orderItems, "Updated order items");
    }

    @PutMapping("/{order_id}/orderItems/{orderItem_id}")
    public ApiResponse<OrderItem> updateOrderItem(@PathVariable int order_id, @RequestBody OrderItem update_orderItem, @PathVariable int orderItem_id){
        return ApiResponse.success(update_orderItem, "Update order item having orderItem id : " + orderItem_id);
    }

    @DeleteMapping("/{order_id}/orderItems/{orderItem_id}")
    public ApiResponse<String> deleteOrderItem(@PathVariable int order_id, @PathVariable int orderItem_id){
        return ApiResponse.success("Deleted", "Delete order item having orderItem id : " + orderItem_id);
    }
}
