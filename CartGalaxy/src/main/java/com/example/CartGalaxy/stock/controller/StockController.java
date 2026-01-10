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
import com.example.CartGalaxy.user.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<StockDTO>>> getStockList(HttpSession httpSession) throws SQLException, UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        ApiResponse<List<StockDTO>> res = ApiResponse.success(stockService.getStockList(), "Get stock list");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ApiResponse<StockDTO>> getStock(@PathVariable int product_id, HttpSession httpSession) throws SQLException, ProductNotFoundException, StockNotPresentForExistingProductException, UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        ApiResponse<StockDTO> res = ApiResponse.success(stockService.getStock(product_id), "Get stock having product id : " + product_id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<StockDTO>>> addStocks(@RequestBody List<CreateStockDTO> stocks, HttpSession httpSession) throws SQLException, ProductNotFoundException, UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        ApiResponse<List<StockDTO>> res = ApiResponse.success(stockService.createStock(stocks), "Stocks added");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<List<Stock>>> updateStockList(@RequestBody List<Stock> update_stocks, HttpSession httpSession) throws UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        ApiResponse<List<Stock>> res = ApiResponse.success(stockService.updateStockList(update_stocks), "Updated stocks");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<ApiResponse<StockDTO>> updateStock(@RequestBody CreateStockDTO update_stock, @PathVariable int product_id, HttpSession httpSession) throws SQLException, ProductNotFoundException, UnauthorizedException {
        Object obj = httpSession.getAttribute("USER_ID");
        if(obj==null) throw new UnauthorizedException("User is not authorized");
        ApiResponse<StockDTO> res = ApiResponse.success(stockService.updateStock(update_stock), "Update stock having product id : " + product_id);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
