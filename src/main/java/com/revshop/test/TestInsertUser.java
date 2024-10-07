package com.revshop.test;

import com.revshop.dao.UsersDAO;
import com.revshop.model.Users;

public class TestInsertUser {

    public static void main(String[] args) {
        // Create a sample user object
        Users user = new Users();
        user.setEmail("testuser@example.com");
        user.setMobil_num(7386957811L); // Sample mobile number
        user.setPassword("password123");
        user.setUser_role("BUYER"); // Sample user role
        user.setFirst_name("John");
        user.setLast_name("Doe");
//        user.setAddress("123 Main St, Anytown, USA");
        user.setBusiness_name(null); // No business name for BUYER

        // Instantiate UsersDAO
        UsersDAO usersDAO = new UsersDAO();

        // Try inserting the user into the database
        boolean isRegistered = usersDAO.registerUser(user);

        // Check the result
        if (isRegistered) {
            System.out.println("User successfully inserted into the database.");
        } else {
            System.out.println("Failed to insert user into the database.");
        }
    }
}
