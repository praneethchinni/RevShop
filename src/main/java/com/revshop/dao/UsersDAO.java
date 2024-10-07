package com.revshop.dao;

import java.sql.*;

import com.revshop.model.Users;
import com.revshop.util.DBUtil;

public class UsersDAO {
    // Method to register a user
    public boolean registerUser(Users user) {
        String query = "INSERT INTO users(email, mobile, password, user_role, first_name, last_name, address, business_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            // Set parameters for the query
            ps.setString(1, user.getEmail());
            ps.setLong(2, user.getMobil_num());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUser_role());
            ps.setString(5, user.getFirst_name());
            ps.setString(6, user.getLast_name());
            ps.setString(7, user.getAddress());
            ps.setString(8, user.getBusiness_name());

            // Execute the query and return success status
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Users loginUser(String email, String password) {
    	Users user = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new Users();
                    
                    
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setUser_role(rs.getString("user_role"));
                    user.setFirst_name(rs.getString("first_name"));
                    user.setLast_name(rs.getString("last_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
 // UserDAO.java

    public Users getUserByEmail(String email) {
        Users user = null;
//        String query = "SELECT p.*, u.first_name, u.last_name, u.address, u.business_name FROM products p INNER JOIN (SELECT email, first_name, last_name, address, business_name FROM users WHERE email = ? LIMIT 1) u ON p.seller_email = u.email";
        String query = "SELECT first_name, last_name, address, business_name FROM users WHERE email = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Users();
//                System.out.println("First Name: " + rs.getString("first_name"));
//                System.out.println("Last Name: " + rs.getString("last_name"));
//                System.out.println("Address: " + rs.getString("address"));
//                System.out.println("Business Name: " + rs.getString("business_name"));
                
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setAddress(rs.getString("address"));
                user.setBusiness_name(rs.getString("business_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
