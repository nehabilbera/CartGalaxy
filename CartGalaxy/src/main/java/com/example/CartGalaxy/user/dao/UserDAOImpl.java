package com.example.CartGalaxy.user.dao;

import com.example.CartGalaxy.cart.exception.CartNotExistsException;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements UserDAO{

    private static Connection conn;
    private final DataSource dataSource;

    public UserDAOImpl(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        if(conn == null){
            conn = dataSource.getConnection();
            PreparedStatement ptst = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "user_id INT PRIMARY KEY," +
                            "username VARCHAR(100)" +
                            ")"
            );
            ptst.executeUpdate();
            System.out.println("✅ users table created");
            ptst.close();
        }
    }


    @Override
    public Boolean getUser(int user_id) throws SQLException, UserNotFoundException {
        String query = "SELECT * FROM users WHERE user_id=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, user_id);
        ResultSet rs = ptst.executeQuery();

        User user = new User();

        if(rs.next()){
            user.setUser_id(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
        }
        else{
            throw new UserNotFoundException("User Not Found");
        }
        return true;
    }
}
