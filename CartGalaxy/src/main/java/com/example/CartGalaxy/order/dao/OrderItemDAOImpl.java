package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.order.model.OrderItemDTO;
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
public class OrderItemDAOImpl implements OrderItemDAO{

    private static Connection conn;
    private DataSource dataSource;
    private final ProductDAO productDAO;

    //TODO: use composite key instead of primary key
    public OrderItemDAOImpl(DataSource dataSource, ProductDAO productDAO) throws SQLException {
        this.dataSource = dataSource;
        this.productDAO = productDAO;
        if (conn == null) {
            conn = dataSource.getConnection();
            PreparedStatement ptst = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS orderItems (" +
                            "order_id VARCHAR(100), " +
                            "product_id INT, " +
                            "quantity INT, " +
                            "price_at_purchase FLOAT, " +
                            "PRIMARY KEY (order_id, product_id)"+
                            ")"
            );
            System.out.println("✅ OrderItem connection established!");
            ptst.executeUpdate();
            ptst.close();
        }
    }

    @Override
    public List<OrderItemDTO> getOrderItemList(String order_id) throws SQLException, ProductNotFoundException {
        List<OrderItemDTO> orderItemList = new ArrayList<>();
        String query = "SELECT product_id, SUM(quantity) AS quantity, SUM(price_at_purchase) AS price_at_purchase FROM orderItems where order_id = ? GROUP BY product_id";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setString(1, order_id);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            int productId = rs.getInt("product_id");       // Get product_id from DB
            ProductDTO pdt = productDAO.getProduct(productId);

            OrderItemDTO ord = new OrderItemDTO(
                    pdt,
                    rs.getInt("quantity"),
                    rs.getFloat("price_at_purchase")
            );
            orderItemList.add(ord);
        }
        rs.close();
        ptst.close();
        return orderItemList;
    }
}
