package com.revshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/RevShop"; // Update with your DB details
    private static final String USER = "root"; // Update with your DB credentials
    private static final String PASSWORD = "12345678"; // Update with your DB credentials

    public static Connection getConnection() throws SQLException {
		// Optionally load the driver class (usually not needed with modern JDBC)
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException("MySQL JDBC Driver not found", e);
		}
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	
}
