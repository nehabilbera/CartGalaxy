package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.order.model.GetOrderDTO;
import com.example.CartGalaxy.order.model.OrderDTO;
import com.example.CartGalaxy.order.model.OrderItemDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.product.model.ProductDTO;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO{
    private static Connection conn;
    private DataSource dataSource;
    private final OrderItemDAO orderItemDAO;

    public OrderDAOImpl(DataSource dataSource, OrderItemDAO orderItemDAO) throws SQLException {
        this.dataSource = dataSource;
        this.orderItemDAO = orderItemDAO;
        if(conn == null){
            conn = dataSource.getConnection();
            PreparedStatement ptst = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS orders (" +
                            "order_id INT AUTO_INCREMENT PRIMARY KEY, " +
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

    public List<OrderDTO> getOrderList() throws SQLException {
        List<OrderDTO> orderList = new ArrayList<>();
        String query = "SELECT * FROM orders";
        PreparedStatement ptst = conn.prepareStatement(query);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            OrderDTO ord = new OrderDTO(
                    rs.getInt("order_id"),
                    rs.getDate("ordered_date").toLocalDate(),
                    rs.getString("status"),
                    rs.getFloat("transaction_amount")
            );
            orderList.add(ord);
        }
        return orderList;
    }


    @Override
    public GetOrderDTO getOrder(int order_id) throws SQLException, ProductNotFoundException {
        List<OrderItemDTO> orderItems;
        orderItems = orderItemDAO.getOrderItemList(order_id);
        String query = "SELECT * FROM orders WHERE order_id="+order_id;
        PreparedStatement ptst = conn.prepareStatement(query);
        ResultSet rs = ptst.executeQuery();

        GetOrderDTO order = new GetOrderDTO();

        if(rs.next()){
            order.setOrdered_date(rs.getDate("ordered_date").toLocalDate());
            order.setStatus(rs.getString("status"));
            order.setTransaction_amount(rs.getFloat("transaction_amount"));
            order.setOrderItems(orderItems);
        }
        return order;
    }
}
