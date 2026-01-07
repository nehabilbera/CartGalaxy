package com.example.CartGalaxy.stock.dao;

import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.model.CreateStockDTO;
import com.example.CartGalaxy.stock.model.Stock;
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
public class StockDAOImpl implements StockDAO{

    private static Connection conn;
    private final DataSource dataSource;

    public StockDAOImpl(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        if(conn == null){
            conn = dataSource.getConnection();
            System.out.println("✅ Stock table created");
        }
    }

    public void updateUsedStock(int product_id, int used_quantity) throws SQLException {
        String query = "UPDATE stocks " + "SET available_quantity = available_quantity - ? " + "WHERE product_id = ?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, used_quantity);
        ptst.setInt(2, product_id);
        ptst.executeUpdate();
        ptst.close();
    }

    @Override
    public List<StockDTO> getStockList() throws SQLException {
        List<StockDTO> stockList = new ArrayList<>();
        String query = "SELECT * FROM stocks";
        PreparedStatement ptst = conn.prepareStatement(query);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            StockDTO stock = new StockDTO(
                    rs.getInt("product_id"),
                    rs.getInt("available_quantity"),
                    rs.getInt("total_quantity")
            );
            stockList.add(stock);
        }
        rs.close();
        ptst.close();
        return stockList;
    }

    //todo: stock not available exception for existing product
    @Override
    public StockDTO getStock(int product_id) throws SQLException, ProductNotFoundException {
        String query = "SELECT * FROM stocks WHERE product_id=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, product_id);
        ResultSet rs = ptst.executeQuery();

        StockDTO stock = new StockDTO();

        if(rs.next()){
            stock.setProduct_id(rs.getInt("product_id"));
            stock.setAvailable_quantity(rs.getInt("available_quantity"));
            stock.setTotal_quantity(rs.getInt("total_quantity"));
            }
        else{
            throw new ProductNotFoundException("Product Not found");
        }

        rs.close();
        ptst.close();
        return stock;
    }

    @Override
    public List<StockDTO> createStock(List<CreateStockDTO> stocks) throws SQLException {
        for(CreateStockDTO stock : stocks){
            try{
                StockDTO st = getStock(stock.getProduct_id());
                String query = "UPDATE stocks SET available_quantity = available_quantity + ?, total_quantity = total_quantity + ? WHERE product_id = ?";
                PreparedStatement ptst = conn.prepareStatement(query);
                ptst.setInt(1, stock.getTotal_quantity());
                ptst.setInt(2, stock.getTotal_quantity());
                ptst.setInt(3, stock.getProduct_id());
                ptst.executeUpdate();
                ptst.close();

            }
            catch (ProductNotFoundException e) {
                String query = "INSERT INTO stocks (product_id, available_quantity, total_quantity) " + "VALUES (?, ?, ?)";
                PreparedStatement ptst = conn.prepareStatement(query);
                ptst.setInt(1, stock.getProduct_id());
                ptst.setInt(2, stock.getTotal_quantity());
                ptst.setInt(3, stock.getTotal_quantity());

                ptst.executeUpdate();
                ptst.close();
            }

        }

        List<StockDTO> stockList = new ArrayList<>();
        String sql = "SELECT * FROM (" + "SELECT * FROM stocks ORDER BY stock_id DESC LIMIT " + stocks.size() + ") AS t ORDER BY stock_id ASC";
        PreparedStatement ptst = conn.prepareStatement(sql);
        ResultSet rs = ptst.executeQuery();

        while(rs.next()){
            StockDTO stock = new StockDTO(
                    rs.getInt("product_id"),
                    rs.getInt("available_quantity"),
                    rs.getInt("total_quantity")
            );
            stockList.add(stock);
        }
        return stockList;
    }

    @Override
    public List<Stock> updateStockList(List<Stock> update_stock) {
        return List.of();
    }

    @Override
    public StockDTO updateStock(CreateStockDTO update_stock) throws SQLException, ProductNotFoundException {


        return null;
    }
}
