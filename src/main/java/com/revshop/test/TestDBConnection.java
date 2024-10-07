package com.revshop.test;

import java.sql.Connection;
import com.revshop.util.DBUtil;

public class TestDBConnection {

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Attempt to get a connection
            connection = DBUtil.getConnection();

            // Check if the connection is successful
            if (connection != null) {
                System.out.println("Database connection is working correctly!");
            } else {
                System.out.println("Failed to establish database connection.");
            }
        } catch (Exception e) {
            // Print exception details in case of failure
            System.out.println("An error occurred while trying to connect to the database:");
            e.printStackTrace();
        } finally {
            // Close the connection after the test
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("Error closing the connection.");
                    e.printStackTrace();
                }
            }
        }
    }
}
