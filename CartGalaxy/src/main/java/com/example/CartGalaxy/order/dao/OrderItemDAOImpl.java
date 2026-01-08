package com.example.CartGalaxy.order.dao;

import com.example.CartGalaxy.order.model.OrderItemDTO;
import com.example.CartGalaxy.product.dao.ProductDAO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.product.model.ProductDTO;
import com.example.CartGalaxy.stock.dao.StockDAO;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import com.example.CartGalaxy.stock.model.StockDTO;
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
    private final DataSource dataSource;
    private final ProductDAO productDAO;
    private final StockDAO stockDAO;

    public OrderItemDAOImpl(DataSource dataSource, ProductDAO productDAO, StockDAO stockDAO) throws SQLException {
        this.dataSource = dataSource;
        this.productDAO = productDAO;
        this.stockDAO = stockDAO;
        if(conn == null){
            conn = dataSource.getConnection();
            System.out.println("✅ OrderItems table created");
        }
    }

    @Override
    public List<OrderItemDTO> getOrderItemList(String order_id) throws SQLException, ProductNotFoundException, InsufficientProductException, StockNotPresentForExistingProductException {
        List<OrderItemDTO> orderItemList = new ArrayList<>();
        String query = "SELECT product_id, SUM(quantity) AS quantity, (SUM(price_at_purchase*quantity)) AS price_at_purchase FROM orderItems where order_id = ? GROUP BY product_id";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setString(1, order_id);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            int productId = rs.getInt("product_id");
            int quantity = rs.getInt("quantity");

            ProductDTO pdt = productDAO.getProduct(productId);
            StockDTO stock = stockDAO.getStock(productId);

            OrderItemDTO ord = new OrderItemDTO(
                    pdt,
                    quantity,
                    rs.getFloat("price_at_purchase")
            );
            orderItemList.add(ord);
        }
        rs.close();
        ptst.close();
        return orderItemList;
    }
}
