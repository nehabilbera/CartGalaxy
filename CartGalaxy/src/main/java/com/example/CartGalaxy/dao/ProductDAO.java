package com.example.CartGalaxy.dao;

import com.example.CartGalaxy.dto.CreateProductDTO;
import com.example.CartGalaxy.dto.ProductDTO;
import com.example.CartGalaxy.dto.UpdateProductDTO;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    List<ProductDTO> getProductList() throws SQLException;
    ProductDTO getProduct(int product_id) throws SQLException;
    List<ProductDTO> addProducts(List<CreateProductDTO> products) throws SQLException;
    List<ProductDTO> updateProductList(List<UpdateProductDTO> update_products);
    ProductDTO updateProduct(UpdateProductDTO update_product, int product_id);
    String deleteProduct(int product_id);
}
