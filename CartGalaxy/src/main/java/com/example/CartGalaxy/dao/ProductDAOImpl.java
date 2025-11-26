package com.example.CartGalaxy.dao;

import com.example.CartGalaxy.dto.CreateProductDTO;
import com.example.CartGalaxy.dto.ProductDTO;
import com.example.CartGalaxy.dto.UpdateProductDTO;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

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
            PreparedStatement ptst = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS products (" +
                            "product_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "selling_price FLOAT, " +
                            "discounted_price FLOAT, " +
                            "description VARCHAR(255), " +
                            "brand VARCHAR(255), " +
                            "category VARCHAR(255), " +
                            "image VARCHAR(255), " +
                            "availability BOOLEAN" +
                            ")"
            );
            System.out.println("✅ MySQL connection established!");
            ptst.executeUpdate();
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
                    rs.getString("description"),
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
    public ProductDTO getProduct(int product_id) throws SQLException {
        String query = "SELECT * FROM products WHERE product_id="+product_id;
        PreparedStatement ptst = conn.prepareStatement(query);
        ResultSet rs = ptst.executeQuery();

        ProductDTO product = new ProductDTO();

        if(rs.next()){
            product.setProduct_id(rs.getInt("product_id"));
            product.setSelling_price(rs.getFloat("selling_price"));
            product.setDiscounted_price(rs.getFloat("discounted_price"));
            product.setDescription(rs.getString("description"));
            product.setBrand(rs.getString("brand"));
            product.setCategory(rs.getString("category"));
            product.setImage(rs.getString("image"));
            product.setAvailability(rs.getBoolean("availability"));
        }
        return product;

    }

    int cnt=0;
    @Override
    public List<ProductDTO> addProducts(List<CreateProductDTO> products) throws SQLException {
        for(CreateProductDTO pdt : products){
            String query = "INSERT INTO products (selling_price, discounted_price, description, brand, category, image, availability) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ptst = conn.prepareStatement(query);
            ptst.setFloat(1, pdt.getSelling_price());
            ptst.setFloat(2, pdt.getDiscounted_price());
            ptst.setString(3, pdt.getDescription());
            ptst.setString(4, pdt.getBrand());
            ptst.setString(5, pdt.getCategory());
            ptst.setString(6, pdt.getImage());
            ptst.setBoolean(7, pdt.getAvailability());

            ptst.executeUpdate();
        }

        List<ProductDTO> productList = new ArrayList<>();

        for(CreateProductDTO pdt : products){
            ProductDTO pdtDTO = new ProductDTO();
            pdtDTO.setProduct_id(++cnt);
            pdtDTO.setSelling_price(pdt.getSelling_price());
            pdtDTO.setDiscounted_price(pdt.getDiscounted_price());
            pdtDTO.setDescription(pdt.getDescription());
            pdtDTO.setBrand(pdt.getBrand());
            pdtDTO.setCategory(pdt.getCategory());
            pdtDTO.setImage(pdt.getImage());
            pdtDTO.setAvailability(pdt.getAvailability());

            productList.add(pdtDTO);
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
    public String deleteProduct(int product_id) {
        return "";
    }
}
