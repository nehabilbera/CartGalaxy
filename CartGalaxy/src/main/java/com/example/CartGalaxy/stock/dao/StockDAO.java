package com.example.CartGalaxy.stock.dao;

import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import com.example.CartGalaxy.stock.model.CreateStockDTO;
import com.example.CartGalaxy.stock.model.Stock;
import com.example.CartGalaxy.stock.model.StockDTO;

import java.sql.SQLException;
import java.util.List;

public interface StockDAO {
    List<StockDTO> getStockList() throws SQLException;
    StockDTO getStock(int product_id) throws SQLException, ProductNotFoundException, StockNotPresentForExistingProductException;
    List<StockDTO> createStock(List<CreateStockDTO> stocks) throws SQLException;
    void updateUsedStock(int product_id, int used_quantity) throws SQLException;
    List<Stock> updateStockList(List<Stock> update_stock);
    StockDTO updateStock(CreateStockDTO update_stock) throws SQLException, ProductNotFoundException;
}
