package com.example.CartGalaxy.user.dao;

import com.example.CartGalaxy.user.exception.PasswordMatchException;
import com.example.CartGalaxy.user.exception.PasswordNotMatchException;
import com.example.CartGalaxy.user.exception.UserNotFoundException;
import com.example.CartGalaxy.user.model.CreateUserDTO;
import com.example.CartGalaxy.user.model.UserChangePasswordDTO;
import com.example.CartGalaxy.user.model.UserDTO;
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
            System.out.println("✅ users table created");
        }
    }

    @Override
    public UserDTO getUserById(int user_id) throws SQLException, UserNotFoundException {
        String query = "SELECT * FROM users WHERE user_id=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setInt(1, user_id);
        ResultSet rs = ptst.executeQuery();

        UserDTO user = new UserDTO();

        if(rs.next()){
            user.setUser_id(rs.getInt("user_id"));
            user.setUser_email(rs.getString("user_email"));
        }
        else{
            throw new UserNotFoundException("User Not Found with id : "+user_id);
        }
        return user;
    }

    @Override
    public UserDTO getUserByEmail(String user_email) throws SQLException, UserNotFoundException {
        String query = "SELECT * FROM users WHERE user_email=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setString(1, user_email);
        ResultSet rs = ptst.executeQuery();

        UserDTO user = new UserDTO();

        if(rs.next()){
            user.setUser_id(rs.getInt("user_id"));
            user.setUser_email(rs.getString("user_email"));
            return user;
        }
        else{
            throw new UserNotFoundException("User Not Found with email : "+ user_email);
        }
    }

    @Override
    public UserDTO createUser(CreateUserDTO userDTO) throws SQLException, UserNotFoundException {
        PreparedStatement ptst = conn.prepareStatement(
                "INSERT INTO users (user_email, user_password) VALUES (?,?)"
        );
        ptst.setString(1, userDTO.getUser_email());
        ptst.setString(2, userDTO.getUser_password());
        int rs = ptst.executeUpdate();
        return getUserByEmail(userDTO.getUser_email());
    }

    @Override
    public Boolean validateUser(String user_email, String user_password) throws SQLException {
        String query = "SELECT * FROM users WHERE user_email=? AND user_password=?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setString(1, user_email);
        ptst.setString(2, user_password);
        ResultSet rs = ptst.executeQuery();
        return rs.next();
    }

    @Override
    public void changePassword(UserChangePasswordDTO userChangePasswordDTO, String user_email) throws PasswordNotMatchException, SQLException, PasswordMatchException {
        String new_password = userChangePasswordDTO.getNew_password();
        String confirm_password = userChangePasswordDTO.getConfirm_password();
        String old_password = userChangePasswordDTO.getOld_password();

        if(!new_password.equals(confirm_password))
            throw new PasswordNotMatchException("Password does not match!");
        if(new_password.equals(old_password))
            throw new PasswordMatchException("Old password is same as new password");

        String query = "UPDATE users SET user_password = ? WHERE user_email = ?";
        PreparedStatement ptst = conn.prepareStatement(query);
        ptst.setString(1, new_password);
        ptst.setString(2, user_email);
        ptst.executeUpdate();
        ptst.close();
    }
}
