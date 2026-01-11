package com.example.CartGalaxy.stock.service;

import com.example.CartGalaxy.product.dao.ProductDAO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.product.model.ProductDTO;
import com.example.CartGalaxy.stock.dao.StockDAO;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import com.example.CartGalaxy.stock.model.CreateStockDTO;
import com.example.CartGalaxy.stock.model.Stock;
import com.example.CartGalaxy.stock.model.StockDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService{

    private final StockDAO stockDAO;
    private final ProductDAO productDAO;

    public StockServiceImpl(StockDAO stockDAO, ProductDAO productDAO) {
        this.stockDAO = stockDAO;
        this.productDAO = productDAO;
    }

    @Override
    public List<StockDTO> getStockList() throws SQLException {
        return stockDAO.getStockList();
    }

    @Override
    public StockDTO getStock(int product_id) throws SQLException, ProductNotFoundException, StockNotPresentForExistingProductException {
        return stockDAO.getStock(product_id);
    }

    @Override
    public List<StockDTO> createStock(List<CreateStockDTO> stocks) throws SQLException, ProductNotFoundException {
        for(CreateStockDTO st : stocks){
            ProductDTO product = productDAO.getProduct(st.getProduct_id());
        }
        return stockDAO.createStock(stocks);
    }

    @Override
    public List<Stock> updateStockList(List<Stock> update_stock) {
        return stockDAO.updateStockList(update_stock);
    }

    @Override
    public StockDTO updateStock(CreateStockDTO update_stock) throws SQLException, ProductNotFoundException {
        return stockDAO.updateStock(update_stock);
    }
}
