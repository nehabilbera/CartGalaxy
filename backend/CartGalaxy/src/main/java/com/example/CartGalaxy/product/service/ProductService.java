package com.example.CartGalaxy.product.service;

import com.example.CartGalaxy.product.model.CreateProductDTO;
import com.example.CartGalaxy.product.model.ProductDTO;
import com.example.CartGalaxy.product.model.UpdateProductDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    public List<ProductDTO> getProductList() throws SQLException;
    public ProductDTO getProduct(int product_id) throws SQLException, ProductNotFoundException;
    public List<ProductDTO> addProducts(List<CreateProductDTO> products) throws SQLException;
    public List<ProductDTO> updateProductList(List<UpdateProductDTO> update_products);
    public ProductDTO updateProduct(UpdateProductDTO update_product, int product_id);
    public String deleteProduct(int product_id) throws SQLException, ProductNotFoundException;
}
