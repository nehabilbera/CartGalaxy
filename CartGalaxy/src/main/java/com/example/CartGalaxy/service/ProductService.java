package com.example.CartGalaxy.service;

import com.example.CartGalaxy.dto.CreateProductDTO;
import com.example.CartGalaxy.dto.ProductDTO;
import com.example.CartGalaxy.dto.UpdateProductDTO;
import com.example.CartGalaxy.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    public List<ProductDTO> getProductList() throws SQLException;
    public ProductDTO getProduct(int product_id) throws SQLException;
    public List<ProductDTO> addProducts(List<CreateProductDTO> products) throws SQLException;
    public List<ProductDTO> updateProductList(List<UpdateProductDTO> update_products);
    public ProductDTO updateProduct(UpdateProductDTO update_product, int product_id);
    public String deleteProduct(int product_id);
}
