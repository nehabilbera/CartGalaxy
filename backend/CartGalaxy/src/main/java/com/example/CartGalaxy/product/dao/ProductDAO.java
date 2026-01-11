package com.example.CartGalaxy.product.dao;

import com.example.CartGalaxy.product.model.CreateProductDTO;
import com.example.CartGalaxy.product.model.ProductDTO;
import com.example.CartGalaxy.product.model.UpdateProductDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    List<ProductDTO> getProductList() throws SQLException;
    ProductDTO getProduct(int product_id) throws SQLException, ProductNotFoundException;
    List<ProductDTO> addProducts(List<CreateProductDTO> products) throws SQLException;
    List<ProductDTO> updateProductList(List<UpdateProductDTO> update_products);
    ProductDTO updateProduct(UpdateProductDTO update_product, int product_id);
    String deleteProduct(int product_id) throws SQLException, ProductNotFoundException;
}
