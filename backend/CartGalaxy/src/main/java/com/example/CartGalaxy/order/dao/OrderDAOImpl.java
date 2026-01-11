package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.cart.dao.CartDAO;
import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.order.Exception.InvalidOrderIdException;
import com.example.CartGalaxy.order.Exception.OrderNotExistsForExistingUser;
import com.example.CartGalaxy.order.model.*;
import com.example.CartGalaxy.product.dao.ProductDAO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.product.model.ProductDTO;
import com.example.CartGalaxy.stock.dao.StockDAO;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import com.example.CartGalaxy.stock.model.StockDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO{
    private static Connection conn;
    private final DataSource dataSource;
    private final OrderItemDAO orderItemDAO;
    private final ProductDAO productDAO;
    private final StockDAO stockDAO;
    private final CartDAO cartDAO;

    public OrderDAOImpl(DataSource dataSource, OrderItemDAO orderItemDAO, ProductDAO productDAO, StockDAO stockDAO, CartDAO cartDAO) throws SQLException {
        this.dataSource = dataSource;
        this.orderItemDAO = orderItemDAO;
        this.productDAO = productDAO;
        this.stockDAO = stockDAO;
        this.cartDAO = cartDAO;
        if(conn == null){
            conn = dataSource.getConnection();
            System.out.println("✅ Order table created");
        }
    }

    private static String generateOrderId(){
        return "OD"+System.currentTimeMillis();
    }

    @Override
    public List<OrderDTO> getOrderList(int user_id) throws SQLException, OrderNotExistsForExistingUser {
        System.out.println("DAO");
        List<OrderDTO> orderList = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, user_id);
        ResultSet rs = ptst.executeQuery();

        if(!rs.isBeforeFirst()){
            rs.close();
            throw new OrderNotExistsForExistingUser("Order not found for existing user having user_id : "+user_id);
        }
            while (rs.next()) {
                OrderDTO ord = new OrderDTO(
                        rs.getString("order_id"),
                        rs.getDate("ordered_date").toLocalDate(),
                        rs.getString("status"),
                        rs.getFloat("transaction_amount")
                );
                orderList.add(ord);
            }
            rs.close();
            System.out.println("Return DAO");
            return orderList;

    }


    @Override
    public OrderDetailDTO getOrder(String order_id, int user_id) throws SQLException, ProductNotFoundException, InsufficientProductException, StockNotPresentForExistingProductException, InvalidOrderIdException {
        List<OrderItemDTO> orderItems;
        orderItems = orderItemDAO.getOrderItemList(order_id);
        String query = "SELECT * FROM orders WHERE order_id= ? AND user_id=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setString(1, order_id);
        ptst.setInt(2, user_id);
        ResultSet rs = ptst.executeQuery();

        OrderDetailDTO orderDetail = new OrderDetailDTO();

        if(rs.next()){
            orderDetail.setOrder_id(order_id);
            orderDetail.setOrdered_date(rs.getDate("ordered_date").toLocalDate());
            orderDetail.setStatus(rs.getString("status"));
            orderDetail.setTransaction_amount(rs.getFloat("transaction_amount"));
            orderDetail.setOrder_items(orderItems);
        }
        else{
            throw new InvalidOrderIdException("Order Id : "+order_id+" is invalid");
        }
        rs.close();
        return orderDetail;
    }

    //todo: make use of db lock to perform database manipulation
    @Override
    public OrderDetailDTO createOrder(CreateOrderDTO orderDTO, int user_id) throws SQLException, ProductNotFoundException, InsufficientProductException, CartNotExistsException, UserNotExistsException, StockNotPresentForExistingProductException, InvalidOrderIdException {

        if(cartDAO.getCart(user_id)==null) return null;
        float total_amount = 0;
        try {
            conn.setAutoCommit(false);
            String query = "INSERT INTO orders (order_id, user_id, ordered_date, status, transaction_amount) " + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ptst = conn.prepareStatement(query);
            String order_id = generateOrderId();
            ptst.setString(1, order_id);
            ptst.setInt(2, user_id);
            ptst.setDate(3, Date.valueOf(LocalDate.now()));
            ptst.setString(4, "PLACED");
            ptst.setFloat(5, total_amount);
            ptst.executeUpdate();
            ptst.close();

            for (CreateOrderItemDTO orderItem : orderDTO.getOrder_items()) {
                int product_id = orderItem.getProduct_id();
                ProductDTO product = productDAO.getProduct(product_id);
                int quantity = orderItem.getQuantity();
                StockDTO stock = stockDAO.getStock(product.getProduct_id());
                if(stock.getAvailable_quantity() < quantity) {
                    throw new InsufficientProductException("Insufficient stock for product having product id : " + product.getProduct_id());
                }
                total_amount += product.getDiscounted_price() * quantity;

                String query2 = "INSERT INTO orderItems (order_id, product_id, quantity, price_at_purchase) " + "VALUES (?, ?, ?, ?)";
                PreparedStatement ptst2 = conn.prepareStatement(query2);
                ptst2.setString(1, order_id);
                ptst2.setInt(2, product_id);
                ptst2.setInt(3, quantity);
                ptst2.setFloat(4, product.getDiscounted_price());

                ptst2.executeUpdate();
                ptst2.close();


                String reduce_stock = "UPDATE stocks SET available_quantity = available_quantity - ? " +
                        "WHERE product_id = ?";
                PreparedStatement ptst3 = conn.prepareStatement(reduce_stock);
                ptst3.setInt(1, quantity);
                ptst3.setInt(2, product_id);
                ptst3.executeUpdate();
                ptst3.close();
            }


            PreparedStatement ptst4 = conn.prepareStatement(
                    "UPDATE orders SET transaction_amount = ? WHERE order_id = ?"
            );
            ptst4.setFloat(1, total_amount);
            ptst4.setString(2, order_id);
            ptst4.executeUpdate();
            ptst4.close();

            cartDAO.deleteCart(user_id);

            conn.commit();
            conn.setAutoCommit(true);
            return getOrder(order_id, user_id);
        }
        catch(Exception e){
            conn.rollback();
            conn.setAutoCommit(true);
            throw e;
        }
    }
}
