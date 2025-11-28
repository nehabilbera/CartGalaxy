package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.order.model.OrderDTO;
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

    public OrderItemDAOImpl(DataSource dataSource, ProductDAO productDAO) throws SQLException {
        this.dataSource = dataSource;
        this.productDAO = productDAO;
        if (conn == null) {
            conn = dataSource.getConnection();
            PreparedStatement ptst = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS orderItems (" +
                            "orderItem_id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "order_id INT, " +
                            "product_id INT, " +
                            "quantity INT, " +
                            "price_at_purchase FLOAT " +
                            ")"
            );
            System.out.println("✅ OrderItem connection established!");
            ptst.executeUpdate();
            ptst.close();
        }
    }

    @Override
    public List<OrderItemDTO> getOrderItemList(int order_id) throws SQLException, ProductNotFoundException {
        List<OrderItemDTO> orderItemList = new ArrayList<>();
//        ProductDTO pdt = productDAO.getProduct(product_id)
        String query = "SELECT product_id, SUM(quantity) AS quantity, AVG(price_at_purchase) AS price_at_purchase FROM orderItems where order_id = " + order_id + " GROUP BY product_id";
        PreparedStatement ptst = conn.prepareStatement(query);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            int productId = rs.getInt("product_id");       // Get product_id from DB
            ProductDTO pdt = productDAO.getProduct(productId);

            OrderItemDTO ord = new OrderItemDTO(
                    rs.getInt("quantity"),
                    rs.getFloat("price_at_purchase")
            );
            ord.setProduct(pdt);
            orderItemList.add(ord);
        }
        return orderItemList;
    }
}
