package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.order.model.*;
import com.example.CartGalaxy.product.dao.ProductDAO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.product.model.ProductDTO;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
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

    public OrderDAOImpl(DataSource dataSource, OrderItemDAO orderItemDAO, ProductDAO productDAO) throws SQLException {
        this.dataSource = dataSource;
        this.orderItemDAO = orderItemDAO;
        this.productDAO = productDAO;
        if(conn == null){
            conn = dataSource.getConnection();
            PreparedStatement ptst = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS orders (" +
                            "order_id VARCHAR(100) PRIMARY KEY, " +
                            "user_id INT, " +
                            "ordered_date DATE, " +
                            "status VARCHAR(255), " +
                            "transaction_amount FLOAT " +
                            ")"
            );
            System.out.println("✅ Order connection established!");
            ptst.executeUpdate();
            ptst.close();
        }
    }

    private static String generateOrderId(){
        return "OD"+System.currentTimeMillis();
    }

    @Override
    public List<OrderDTO> getOrderList() throws SQLException {
        System.out.println("DAO");
        List<OrderDTO> orderList = new ArrayList<>();
        String query = "SELECT * FROM orders";
        PreparedStatement ptst = conn.prepareStatement(query);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
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
    public OrderDetailDTO getOrder(String order_id) throws SQLException, ProductNotFoundException, InsufficientProductException {
        List<OrderItemDTO> orderItems;
        orderItems = orderItemDAO.getOrderItemList(order_id);
        String query = "SELECT * FROM orders WHERE order_id= ?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setString(1, order_id);
        ResultSet rs = ptst.executeQuery();

        OrderDetailDTO orderDetail = new OrderDetailDTO();

        if(rs.next()){
            orderDetail.setOrdered_date(rs.getDate("ordered_date").toLocalDate());
            orderDetail.setStatus(rs.getString("status"));
            orderDetail.setTransaction_amount(rs.getFloat("transaction_amount"));
            orderDetail.setOrder_items(orderItems);
        }
        rs.close();
        return orderDetail;
    }

    //todo: make use of atomicity transaction --- savepoint, rollback, commit
    //todo: make use of db lock to perform database manipulation
    @Override
    public OrderDetailDTO createOrder(CreateOrderDTO orderDTO) throws SQLException, ProductNotFoundException, InsufficientProductException {
        for(CreateOrderItemDTO orderItem : orderDTO.getOrder_items()){
            int product_id = orderItem.getProduct_id();
            ProductDTO product = productDAO.getProduct(product_id);
        }

        float total_amount = 0;
        for (CreateOrderItemDTO pdt : orderDTO.getOrder_items()) {
            ProductDTO product = productDAO.getProduct(pdt.getProduct_id());
            total_amount += product.getSelling_price() * pdt.getQuantity();
        }

        String query = "INSERT INTO orders (order_id, user_id, ordered_date, status, transaction_amount) " + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setString(1, generateOrderId());
        ptst.setInt(2, orderDTO.getUser_id());
        ptst.setDate(3, Date.valueOf(LocalDate.now()));
        ptst.setString(4, "PENDING");
        ptst.setFloat(5, total_amount);
        ptst.executeUpdate();
        ptst.close();

        String query1 = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        PreparedStatement ptst1 = conn.prepareStatement(query1);
        ResultSet rs = ptst1.executeQuery();

        String order_id = null;
        if (rs.next()) {
            order_id = rs.getString("order_id");
        }
        ptst1.close();

        for(CreateOrderItemDTO orderItem : orderDTO.getOrder_items()){
            int product_id = orderItem.getProduct_id();
            ProductDTO product = productDAO.getProduct(product_id);
            int quantity = orderItem.getQuantity();

            String query2 = "INSERT INTO orderItems (order_id, product_id, quantity, price_at_purchase) " + "VALUES (?, ?, ?, ?)";
            PreparedStatement ptst2 = conn.prepareStatement(query2);
            ptst2.setString(1, order_id);
            ptst2.setInt(2, product_id);
            ptst2.setInt(3, quantity);
            ptst2.setFloat(4, product.getSelling_price());

            ptst2.executeUpdate();
            ptst2.close();
        }

        return getOrder(order_id);
    }
}
