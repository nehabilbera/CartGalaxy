package com.example.CartGalaxy.controller;

import com.example.CartGalaxy.model.ApiResponse;
import com.example.CartGalaxy.model.Stock;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @GetMapping
    public ApiResponse<List<Stock>> getStockList() {
        return ApiResponse.success(List.of(), "Get list of all stocks");
    }

    @GetMapping("/{stock_id}")
    public ApiResponse<Stock> getStock(@PathVariable int stock_id) {
        return ApiResponse.success(new Stock(), "Get stock having stock id : " + stock_id);
    }

    @PostMapping
    public ApiResponse<List<Stock>> addStock(@RequestBody List<Stock> stocks) {
        return ApiResponse.success(stocks, "Stocks added");
    }

    @PutMapping
    public ApiResponse<List<Stock>> updateStockList(@RequestBody List<Stock> update_stocks) {
        return ApiResponse.success(update_stocks, "Update stock list");
    }

    @PutMapping("/{stock_id}")
    public ApiResponse<Stock> updateStock(@PathVariable int stock_id, @RequestBody Stock update_stock) {
        return ApiResponse.success(update_stock, "Update stock having stock id : " + stock_id);
    }

    @DeleteMapping("/{stock_id}")
    public ApiResponse<String> deleteStock(@PathVariable int stock_id) {
        return ApiResponse.success("Deleted", "Delete stock having stock id : " + stock_id);
    }
}
