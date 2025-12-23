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
        if (conn == null) {
            conn = dataSource.getConnection();
            PreparedStatement ptst = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS cartItems (" +
                            "cart_item_id INT PRIMARY KEY AUTO_INCREMENT," +
                            "cart_id INT," +
                            "product_id INT," +
                            "quantity INT," +
                            "price_at_purchase FLOAT," +
                            "FOREIGN KEY (cart_id) REFERENCES cart(cart_id)," +
                            "FOREIGN KEY (product_id) REFERENCES products(product_id)" +
                            ")"
            );
            System.out.println("✅ CartItems connection established!");
            ptst.executeUpdate();
            ptst.close();
        }
    }

    @Override
    public List<CartItemDTO> getAllCartItems(int cart_id) throws SQLException, ProductNotFoundException {
        List<CartItemDTO> cartItemList = new ArrayList<>();
        String query = "SELECT product_id, SUM(quantity) AS quantity, SUM(price_at_purchase) AS price_at_purchase FROM cartItems WHERE cart_id = ? GROUP BY product_id";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, cart_id);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            int productId = rs.getInt("product_id");
            ProductDTO pdt = productDAO.getProduct(productId);
            int quantity = rs.getInt("quantity");
            float total_price = pdt.getDiscounted_price()*quantity;
            CartItemDTO cartItemDTO = new CartItemDTO(
                    pdt,
                    quantity,
                    total_price
            );
            cartItemList.add(cartItemDTO);
        }
        rs.close();
        ptst.close();
        return cartItemList;
    }
}
