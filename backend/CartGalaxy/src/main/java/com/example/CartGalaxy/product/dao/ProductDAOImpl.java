package com.example.CartGalaxy.product.dao;

import com.example.CartGalaxy.product.model.CreateProductDTO;
import com.example.CartGalaxy.product.model.ProductDTO;
import com.example.CartGalaxy.product.model.UpdateProductDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO{

    private static Connection conn;
    private DataSource dataSource;

    public ProductDAOImpl(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        if(conn == null){
            conn = dataSource.getConnection();
            System.out.println("✅ Product table created");
        }
    }


    @Override
    public List<ProductDTO> getProductList() throws SQLException {
        List<ProductDTO> productList = new ArrayList<>();
        String query = "SELECT * FROM products";
        PreparedStatement ptst = conn.prepareStatement(query);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            ProductDTO pdt = new ProductDTO(
                    rs.getInt("product_id"),
                    rs.getFloat("selling_price"),
                    rs.getFloat("discounted_price"),
                    rs.getString("product_name"),
                    rs.getString("brand"),
                    rs.getString("category"),
                    rs.getString("image"),
                    rs.getBoolean("availability")
            );
            productList.add(pdt);
        }
        return productList;
    }

    @Override
    public ProductDTO getProduct(int product_id) throws SQLException, ProductNotFoundException {
        String query = "SELECT * FROM products WHERE product_id="+product_id;
        PreparedStatement ptst = conn.prepareStatement(query);
        ResultSet rs = ptst.executeQuery();

        ProductDTO product = new ProductDTO();

        if(rs.next()){
            product.setProduct_id(rs.getInt("product_id"));
            product.setSelling_price(rs.getFloat("selling_price"));
            product.setDiscounted_price(rs.getFloat("discounted_price"));
            product.setProduct_name(rs.getString("product_name"));
            product.setBrand(rs.getString("brand"));
            product.setCategory(rs.getString("category"));
            product.setImage(rs.getString("image"));
            product.setAvailability(rs.getBoolean("availability"));
        }
        else{
            throw new ProductNotFoundException("Product not found with pdt_id : "+product_id);
        }
        return product;

    }


    @Override
    public List<ProductDTO> addProducts(List<CreateProductDTO> products) throws SQLException {
        for(CreateProductDTO pdt : products){
            String query = "INSERT INTO products (selling_price, discounted_price, product_name, brand, category, image, availability) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ptst = conn.prepareStatement(query);
            ptst.setFloat(1, pdt.getSelling_price());
            ptst.setFloat(2, pdt.getDiscounted_price());
            ptst.setString(3, pdt.getProduct_name());
            ptst.setString(4, pdt.getBrand());
            ptst.setString(5, pdt.getCategory());
            ptst.setString(6, pdt.getImage());
            ptst.setBoolean(7, pdt.getAvailability());

            ptst.executeUpdate();
            ptst.close();
        }

        List<ProductDTO> productList = new ArrayList<>();
        String sql = "SELECT * FROM (" + "SELECT * FROM products ORDER BY product_id DESC LIMIT " + products.size() + ") AS t ORDER BY product_id ASC";
        PreparedStatement ptst = conn.prepareStatement(sql);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            ProductDTO pdt = new ProductDTO(
                    rs.getInt("product_id"),
                    rs.getFloat("selling_price"),
                    rs.getFloat("discounted_price"),
                    rs.getString("product_name"),
                    rs.getString("brand"),
                    rs.getString("category"),
                    rs.getString("image"),
                    rs.getBoolean("availability")
            );
            productList.add(pdt);
        }
        return productList;
    }

    @Override
    public List<ProductDTO> updateProductList(List<UpdateProductDTO> update_products) {
        return List.of();
    }

    @Override
    public ProductDTO updateProduct(UpdateProductDTO update_product, int product_id) {
        return null;
    }

    @Override
    public String deleteProduct(int product_id) throws SQLException, ProductNotFoundException {
        ProductDTO product = getProduct(product_id);
        String query = "DELETE FROM products WHERE product_id="+product_id;
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.executeUpdate();
        ptst.close();
        return "Product deleted.";
    }
}
