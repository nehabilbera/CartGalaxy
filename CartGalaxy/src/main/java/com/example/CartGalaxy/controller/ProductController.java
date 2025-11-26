package com.example.CartGalaxy.controller;

import com.example.CartGalaxy.dto.CreateProductDTO;
import com.example.CartGalaxy.dto.ProductDTO;
import com.example.CartGalaxy.dto.UpdateProductDTO;
import com.example.CartGalaxy.model.ApiResponse;
import com.example.CartGalaxy.model.Product;
import com.example.CartGalaxy.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ApiResponse<List<ProductDTO>> getProductList() throws SQLException {
        return ApiResponse.success(productService.getProductList(), "Get product list");
    }

    @GetMapping("/{product_id}")
    public ApiResponse<ProductDTO> getProduct(@PathVariable int product_id) throws SQLException {
        return ApiResponse.success(productService.getProduct(product_id), "Get product having product id : " + product_id);
    }

    @PostMapping
    public ApiResponse<List<ProductDTO>> addProducts(@RequestBody List<CreateProductDTO> products) throws SQLException {
        return ApiResponse.success(productService.addProducts(products), "Products added");
    }

    @PutMapping
    public ApiResponse<List<ProductDTO>> updateProductList(@RequestBody List<UpdateProductDTO> update_products){
        return ApiResponse.success(productService.updateProductList(update_products), "Updated products");
    }

    @PutMapping("/{product_id}")
    public ApiResponse<ProductDTO> updateProduct(@RequestBody UpdateProductDTO update_product, @PathVariable int product_id){
        return ApiResponse.success(productService.updateProduct(update_product, product_id), "Update product having product id : " + product_id);
    }

    @DeleteMapping("/{product_id}")
    public ApiResponse<String> deleteProduct(@PathVariable int product_id){
        return ApiResponse.success(productService.deleteProduct(product_id), "Delete product having product id : " + product_id);
    }
}
