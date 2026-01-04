package com.example.CartGalaxy.cart.dao;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.cart.model.*;
import com.example.CartGalaxy.product.dao.ProductDAO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.product.model.ProductDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO{

    private final CartItemDAO cartItemDAO;
    private static Connection conn;
    private final DataSource dataSource;
    private final ProductDAO productDAO;

    public CartDAOImpl( CartItemDAO cartItemDAO, DataSource dataSource, ProductDAO productDAO) throws SQLException {
        this.cartItemDAO = cartItemDAO;
        this.dataSource = dataSource;
        this.productDAO = productDAO;
    }

    @Override
    public CartDTO getCart(int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException {
        String query = "SELECT * FROM cart WHERE user_id=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, user_id);
        ResultSet rs = ptst.executeQuery();

        CartDTO cartDTO = new CartDTO();

        if(rs.next()){
            cartDTO.setUser_id(rs.getInt("user_id"));

            List<CartItemDTO> cartItems = cartItemDAO.getAllCartItems(rs.getInt("user_id"));
            cartDTO.setCart_items(cartItems);

            int total_quantity = 0;
            float total_price = 0;

            for(CartItemDTO item : cartItems){
                total_price += (item.getQuantity()*item.getProduct().getDiscounted_price());
                total_quantity += item.getQuantity();
            }

            cartDTO.setTotal_items(total_quantity);
            cartDTO.setTotal_price(total_price);
        }
        else{
            throw new CartNotExistsException("Cart not exists!");
        }

        rs.close();
        ptst.close();
        return cartDTO;
    }

    @Override
    public Boolean cartExists(int user_id) throws SQLException, CartNotExistsException {
        PreparedStatement ps =
                conn.prepareStatement("SELECT user_id FROM cart WHERE user_id = ?");
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public CartDTO createCart(CreateCartDTO createCartDTO) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException {
        PreparedStatement userCheck = conn.prepareStatement(
                "SELECT user_id FROM users WHERE user_id = ?"
        );
        userCheck.setInt(1, createCartDTO.getUser_id());
        ResultSet userRs = userCheck.executeQuery();

        if (!userRs.next()) {
            throw new UserNotExistsException("User does not exist");
        }

        PreparedStatement cartCheck = conn.prepareStatement(
                "SELECT user_id FROM cart WHERE user_id = ?"
        );
        cartCheck.setInt(1, createCartDTO.getUser_id());
        ResultSet cartRs = cartCheck.executeQuery();

        if (!cartRs.next()) {
            PreparedStatement insertCart = conn.prepareStatement(
                    "INSERT INTO cart (user_id, created_at, status) VALUES (?, ?, ?)"
            );
            insertCart.setInt(1, createCartDTO.getUser_id());
            insertCart.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            insertCart.setString(3, "ACTIVE");
            insertCart.executeUpdate();
            insertCart.close();
        }


        for(CreateCartItemDTO cartItem : createCartDTO.getCreateCartItemDTOList()){
            int product_id = cartItem.getProduct_id();
            ProductDTO product = productDAO.getProduct(product_id);
        }

        float total_amount = 0;
        int total_quantity = 0;
        for (CreateCartItemDTO pdt : createCartDTO.getCreateCartItemDTOList()) {
            ProductDTO product = productDAO.getProduct(pdt.getProduct_id());
            total_quantity += pdt.getQuantity();
            total_amount += product.getDiscounted_price() * pdt.getQuantity();
        }


        for(CreateCartItemDTO cartItem : createCartDTO.getCreateCartItemDTOList()){
            int product_id = cartItem.getProduct_id();
            ProductDTO product = productDAO.getProduct(product_id);
            int quantity = cartItem.getQuantity();

            String query2 =  "INSERT INTO cartItems (user_id, product_id, quantity) " +
                    "VALUES (?, ?, ?) ";
            PreparedStatement ptst2 = conn.prepareStatement(query2);
            ptst2.setInt(1, createCartDTO.getUser_id());
            ptst2.setInt(2, product_id);
            ptst2.setInt(3, quantity);

            ptst2.executeUpdate();
            ptst2.close();
        }

        return getCart(createCartDTO.getUser_id());
    }

    @Override
    public CartDTO updateCart(CreateCartDTO createCartDTO) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException {
        for (CreateCartItemDTO item : createCartDTO.getCreateCartItemDTOList()) {
            productDAO.getProduct(item.getProduct_id());
        }

        for (CreateCartItemDTO item : createCartDTO.getCreateCartItemDTOList()) {
            int product_id = item.getProduct_id();
            String query = "SELECT * FROM cartitems WHERE product_id = ?";
            PreparedStatement ptst = conn.prepareStatement(query);
            ptst.setInt(1, product_id);
            ResultSet rs = ptst.executeQuery();
            if(rs.next()){//exists kr rha
                int prev_quantity = rs.getInt("quantity");
                int new_quantity = prev_quantity + item.getQuantity();

                String query1 = "UPDATE cartitems SET quantity = ? WHERE user_id = ? AND product_id = ?";
                PreparedStatement ptst1 = conn.prepareStatement(query1);
                ptst1.setInt(1, new_quantity);
                ptst1.setInt(2, createCartDTO.getUser_id());
                ptst1.setInt(3, product_id);
                ptst1.executeUpdate();
                ptst1.close();
            }
            else{
                String query2 = "INSERT INTO cartitems (user_id, product_id, quantity) VALUES (?,?,?)";
                PreparedStatement ptst2 = conn.prepareStatement(query2);
                ptst2.setInt(1, createCartDTO.getUser_id());
                ptst2.setInt(2, item.getProduct_id());
                ptst2.setInt(3, item.getQuantity());
                ptst2.addBatch();
                ptst2.executeBatch();
                ptst2.close();
            }
        }

        return getCart(createCartDTO.getUser_id());
    }

    @Override
    public String deleteCart(int user_id) throws SQLException {
        cartItemDAO.deleteCartItems(user_id);
        String query = "DELETE FROM cart WHERE user_id=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, user_id);
        int rs = ptst.executeUpdate();
        if (rs>0) {
            return "Cart deleted successfully for user id: " + user_id;
        }
        else{
            return "No cart found for user id: " + user_id;
        }
    }
}
