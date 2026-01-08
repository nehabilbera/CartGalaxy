package com.example.CartGalaxy.stock.controller;

import com.example.CartGalaxy.common.model.ApiResponse;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.product.model.CreateProductDTO;
import com.example.CartGalaxy.product.model.ProductDTO;
import com.example.CartGalaxy.product.model.UpdateProductDTO;
import com.example.CartGalaxy.product.service.ProductService;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import com.example.CartGalaxy.stock.model.CreateStockDTO;
import com.example.CartGalaxy.stock.model.Stock;
import com.example.CartGalaxy.stock.model.StockDTO;
import com.example.CartGalaxy.stock.service.StockService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ApiResponse<List<StockDTO>> getStockList(HttpSession httpSession) throws SQLException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        return ApiResponse.success(stockService.getStockList(), "Get stock list");
    }

    @GetMapping("/{product_id}")
    public ApiResponse<StockDTO> getStock(@PathVariable int product_id, HttpSession httpSession) throws SQLException, ProductNotFoundException, StockNotPresentForExistingProductException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        return ApiResponse.success(stockService.getStock(product_id), "Get stock having product id : " + product_id);
    }

    @PostMapping
    public ApiResponse<List<StockDTO>> addStocks(@RequestBody List<CreateStockDTO> stocks, HttpSession httpSession) throws SQLException, ProductNotFoundException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        return ApiResponse.success(stockService.createStock(stocks), "Stocks added");
    }

    @PutMapping
    public ApiResponse<List<Stock>> updateStockList(@RequestBody List<Stock> update_stocks, HttpSession httpSession){
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        return ApiResponse.success(stockService.updateStockList(update_stocks), "Updated stocks");
    }

    @PutMapping("/{product_id}")
    public ApiResponse<StockDTO> updateStock(@RequestBody CreateStockDTO update_stock, @PathVariable int product_id, HttpSession httpSession) throws SQLException, ProductNotFoundException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");
        return ApiResponse.success(stockService.updateStock(update_stock), "Update stock having product id : " + product_id);
    }
}
