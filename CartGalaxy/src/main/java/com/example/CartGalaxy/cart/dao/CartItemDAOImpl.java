package com.example.CartGalaxy.cart.dao;

import com.example.CartGalaxy.cart.model.CartItemDTO;
import com.example.CartGalaxy.product.dao.ProductDAO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.product.model.ProductDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartItemDAOImpl implements CartItemDAO{

    private final ProductDAO productDAO;
    private static Connection conn;
    private final DataSource dataSource;

    public CartItemDAOImpl(ProductDAO productDAO, DataSource dataSource) throws SQLException {
        this.productDAO = productDAO;
        this.dataSource = dataSource;
        if(conn == null){
            conn = dataSource.getConnection();
            System.out.println("✅ CartItems table created");
        }
    }

    @Override
    public List<CartItemDTO> getAllCartItems(int user_id) throws SQLException, ProductNotFoundException {
        List<CartItemDTO> cartItemList = new ArrayList<>();
        String query = "SELECT product_id, SUM(quantity) AS quantity FROM cartItems WHERE user_id = ? GROUP BY product_id";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, user_id);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            int productId = rs.getInt("product_id");
            ProductDTO pdt = productDAO.getProduct(productId);
            int quantity = rs.getInt("quantity");
            CartItemDTO cartItemDTO = new CartItemDTO(
                    pdt,
                    quantity
            );
            cartItemList.add(cartItemDTO);
        }
        rs.close();
        ptst.close();
        return cartItemList;
    }

    @Override
    public void deleteCartItems(int user_id) throws SQLException {
        PreparedStatement ptst1 = conn.prepareStatement(
                "DELETE FROM cartitems WHERE user_id = ?"
        );
        ptst1.setInt(1, user_id);
        ptst1.executeUpdate();
        ptst1.close();
    }

}
