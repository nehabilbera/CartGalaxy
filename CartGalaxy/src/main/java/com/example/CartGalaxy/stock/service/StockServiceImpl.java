package com.example.CartGalaxy.stock.service;

import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.dao.StockDAO;
import com.example.CartGalaxy.stock.model.CreateStockDTO;
import com.example.CartGalaxy.stock.model.Stock;
import com.example.CartGalaxy.stock.model.StockDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class StockServiceImpl implements StockService{

    private final StockDAO stockDAO;

    public StockServiceImpl(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    @Override
    public List<StockDTO> getStockList() throws SQLException {
        return stockDAO.getStockList();
    }

    @Override
    public StockDTO getStock(int product_id) throws SQLException, ProductNotFoundException {
        return stockDAO.getStock(product_id);
    }

    @Override
    public List<StockDTO> createStock(List<CreateStockDTO> stocks) throws SQLException {
        return stockDAO.createStock(stocks);
    }

    @Override
    public List<Stock> updateStockList(List<Stock> update_stock) {
        return stockDAO.updateStockList(update_stock);
    }

    @Override
    public Stock updateStock(Stock update_stock, int product_id) {
        return stockDAO.updateStock(update_stock, product_id);
    }
}
