package com.example.CartGalaxy.cart.service;

import com.example.CartGalaxy.cart.dao.CartDAO;
import com.example.CartGalaxy.cart.dao.CartItemDAO;
import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.cart.exception.UserNotExistsException;
import com.example.CartGalaxy.cart.model.CartDTO;
import com.example.CartGalaxy.cart.model.CartItemDTO;
import com.example.CartGalaxy.cart.model.CreateCartDTO;
import com.example.CartGalaxy.order.Exception.InvalidOrderIdException;
import com.example.CartGalaxy.order.dao.OrderDAO;
import com.example.CartGalaxy.order.model.CreateOrderDTO;
import com.example.CartGalaxy.order.model.CreateOrderItemDTO;
import com.example.CartGalaxy.order.model.OrderDetailDTO;
import com.example.CartGalaxy.product.exception.ProductNotFoundException;
import com.example.CartGalaxy.stock.exception.InsufficientProductException;
import com.example.CartGalaxy.stock.exception.StockNotPresentForExistingProductException;
import com.example.CartGalaxy.user.dao.UserDAO;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.UserDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    private final CartDAO cartDAO;
    private final UserDAO userDAO;
    private final CartItemDAO cartItemDAO;
    private final OrderDAO orderDAO;

    public CartServiceImpl(CartDAO cartDAO, UserDAO userDAO, CartItemDAO cartItemDAO, OrderDAO orderDAO) {
        this.cartDAO = cartDAO;
        this.userDAO = userDAO;
        this.cartItemDAO = cartItemDAO;
        this.orderDAO = orderDAO;
    }

    @Override
    public CartDTO getCart(int user_id) throws SQLException, ProductNotFoundException, UserNotExistsException, CartNotExistsException, InsufficientProductException, StockNotPresentForExistingProductException {
        return cartDAO.getCart(user_id);
    }

    @Override
    public CartDTO createCart(CreateCartDTO createCartDTO, int user_id) throws SQLException, UserNotExistsException, ProductNotFoundException, CartNotExistsException, UserNotFoundException, InsufficientProductException, StockNotPresentForExistingProductException {
        if (cartDAO.cartExists(user_id)) {
            return cartDAO.updateCart(createCartDTO, user_id);
        }
        return cartDAO.createCart(createCartDTO, user_id);
    }

    @Override
    public OrderDetailDTO checkout(int user_id) throws UserNotFoundException, SQLException, CartNotExistsException, ProductNotFoundException, InsufficientProductException, UserNotExistsException, StockNotPresentForExistingProductException,  InvalidOrderIdException {
        UserDTO userCheck = userDAO.getUserById(user_id);
        Boolean cartCheck = cartDAO.cartExists(user_id);
        List<CartItemDTO> cartItemDTO = cartItemDAO.getAllCartItems(user_id);
        List<CreateOrderItemDTO> createOrderItemDTOList = new ArrayList<>();

        for(CartItemDTO cartItem : cartItemDTO){
            int product_id = cartItem.getProduct().getProduct_id();
            int quantity = cartItem.getQuantity();

            CreateOrderItemDTO item = new CreateOrderItemDTO();
            item.setProduct_id(product_id);
            item.setQuantity(quantity);

            createOrderItemDTOList.add(item);
        }

        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setOrder_items(createOrderItemDTOList);
        return orderDAO.createOrder(createOrderDTO, user_id);
    }
}
