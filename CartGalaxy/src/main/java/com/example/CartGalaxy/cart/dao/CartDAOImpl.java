package com.example.CartGalaxy.cart.dao;

import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.cart.model.CartDTO;
import com.example.CartGalaxy.cart.model.CartItemDTO;
import com.example.CartGalaxy.cart.model.CreateCartDTO;
import com.example.CartGalaxy.cart.model.CreateCartItemDTO;
import com.example.CartGalaxy.product.dao.ProductDAO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.product.model.ProductDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO{

    private final CartItemDAO cartItemDAO;
    private static Connection conn;
    private final DataSource dataSource;
    private final ProductDAO productDAO;

    public CartDAOImpl(CartItemDAO cartItemDAO, DataSource dataSource, ProductDAO productDAO) throws SQLException {
        this.cartItemDAO = cartItemDAO;
        this.dataSource = dataSource;
        this.productDAO = productDAO;
        if(conn == null){
            conn = dataSource.getConnection();
            PreparedStatement ptst = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS cart (" +
                            "cart_id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "user_id INT, " +
                            "created_at DATETIME, " +
                            "status VARCHAR(20), "+
                            "total_quantity INT, " +
                            "total_price_at_purchase FLOAT " +
                            ")"
            );
            System.out.println("✅ Cart connection established!");
            ptst.executeUpdate();
            ptst.close();
        }
    }


    @Override
    public CartDTO getCart(int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException {

        String query = "SELECT * FROM cart WHERE user_id=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, user_id);
        ResultSet rs = ptst.executeQuery();

        CartDTO cartDTO = new CartDTO();

        if(rs.next()){
            cartDTO.setCart_id(rs.getInt("cart_id"));
            cartDTO.setUser_id(rs.getInt("user_id"));

            List<CartItemDTO> cartItems = cartItemDAO.getAllCartItems(rs.getInt("cart_id"));
            cartDTO.setCart_items(cartItems);

            int total_quantity = 0;
            float total_price = 0;

            for(CartItemDTO item : cartItems){
                total_quantity += item.getQuantity();
                total_price += item.getPrice_at_purchase();
            }

            cartDTO.setTotal_items(total_quantity);
            cartDTO.setTotal_price(total_price);
        }
        else{
            throw new UserNotExistsException("User not exists!");
        }

        rs.close();
        ptst.close();
        return cartDTO;
    }

    @Override
    public CartDTO createCart(CreateCartDTO createCartDTO) throws SQLException, ProductNotFoundException, UserNotExistsException {
        for(CreateCartItemDTO cartItem : createCartDTO.getCreateCartItemDTOList()){
            int product_id = cartItem.getProduct_id();
            ProductDTO product = productDAO.getProduct(product_id);
        }

        float total_amount = 0;
        int total_quantity = 0;
        for (CreateCartItemDTO pdt : createCartDTO.getCreateCartItemDTOList()) {
            ProductDTO product = productDAO.getProduct(pdt.getProduct_id());
            total_quantity += pdt.getQuantity();
            total_amount += product.getSelling_price() * pdt.getQuantity();
        }

        String query = "INSERT INTO cart (user_id, created_at, status, total_quantity, total_price_at_purchase) " + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, createCartDTO.getUser_id());
        ptst.setDate(2, Date.valueOf(LocalDate.now()));
        ptst.setString(3, "ACTIVE");
        ptst.setInt(4, total_quantity);
        ptst.setFloat(5, total_amount);
        ptst.executeUpdate();
        ptst.close();

        String query1 = "SELECT cart_id FROM cart ORDER BY cart_id DESC LIMIT 1";
        PreparedStatement ptst1 = conn.prepareStatement(query1);
        ResultSet rs = ptst1.executeQuery();

        int cart_id = 0;
        if (rs.next()) {
            cart_id = rs.getInt("cart_id");
        }
        ptst1.close();

        for(CreateCartItemDTO cartItem : createCartDTO.getCreateCartItemDTOList()){
            int product_id = cartItem.getProduct_id();
            ProductDTO product = productDAO.getProduct(product_id);
            int quantity = cartItem.getQuantity();

            String query2 = "INSERT INTO cartItems (cart_id, product_id, quantity, price_at_purchase) " + "VALUES (?, ?, ?, ?)";
            PreparedStatement ptst2 = conn.prepareStatement(query2);
            ptst2.setInt(1, cart_id);
            ptst2.setInt(2, product_id);
            ptst2.setInt(3, quantity);
            ptst2.setFloat(4, product.getSelling_price());

            ptst2.executeUpdate();
            ptst2.close();
        }

        return getCart(createCartDTO.getUser_id());
    }
}
