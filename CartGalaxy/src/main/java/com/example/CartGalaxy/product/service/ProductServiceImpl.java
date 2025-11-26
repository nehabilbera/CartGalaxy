package com.example.CartGalaxy.product.service;

import com.example.CartGalaxy.product.dao.ProductDAO;
import com.example.CartGalaxy.product.model.CreateProductDTO;
import com.example.CartGalaxy.product.model.ProductDTO;
import com.example.CartGalaxy.product.model.UpdateProductDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public List<ProductDTO> getProductList() throws SQLException {
        return productDAO.getProductList();
    }

    @Override
    public ProductDTO getProduct(int product_id) throws SQLException, ProductNotFoundException {
        return productDAO.getProduct(product_id);
    }

    @Override
    public List<ProductDTO> addProducts(List<CreateProductDTO> products) throws SQLException {
        return productDAO.addProducts(products);
    }

    @Override
    public List<ProductDTO> updateProductList(List<UpdateProductDTO> update_products) {
        return productDAO.updateProductList(update_products);
    }

    @Override
    public ProductDTO updateProduct(UpdateProductDTO update_product, int product_id) {
        return productDAO.updateProduct(update_product, product_id);
    }

    @Override
    public String deleteProduct(int product_id) throws SQLException, ProductNotFoundException {
        return productDAO.deleteProduct(product_id);
    }
}
