package com.revshop.dao;

import com.revshop.model.Product;

import com.revshop.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Method to add a product
    public boolean addProduct(Product product) {
        String query = "INSERT INTO products (name, description, price, seller_email, category, subcategory, image_url, stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection(); 
             PreparedStatement ps = con.prepareStatement(query)) {

            // Set the parameters for the prepared statement
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getSellerEmail());
            ps.setString(5, product.getCategory());
            ps.setString(6, product.getSubcategory());

            // Handle the image data (byte[])
            if (product.getImageUrl() != null) {
                ps.setBytes(7, product.getImageUrl());  // Use byte[] from Product object
            } else {
                ps.setNull(7, java.sql.Types.BLOB);  // Set as null if no image is provided
            }

            ps.setInt(8, product.getStock());

            return ps.executeUpdate() > 0;  // Returns true if at least one row is inserted

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Return false if an exception occurs
    }

    // Method to retrieve products by seller's email
    public List<Product> getProductsBySellerEmail(String sellerEmail) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products WHERE seller_email = ? order by created_at desc";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, sellerEmail);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setSellerEmail(resultSet.getString("seller_email"));
                product.setCategory(resultSet.getString("category"));
                product.setSubcategory(resultSet.getString("subcategory"));

                // Fetch the image blob
                byte[] imageBytes = resultSet.getBytes("image_url");
                product.setImageUrl(imageBytes);  // Store the image as byte[] in Product

                product.setStock(resultSet.getInt("stock"));
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }
    
 // Method to update the stock of a product
    public boolean updateStock(int productId, int newStock) {
        String sql = "UPDATE products SET stock = ? WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newStock);
            statement.setInt(2, productId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Log the error appropriately in a real application
        }

        return false;
    }

 // Delete product by product ID
    public boolean deleteProductById(int productId) {
        System.out.println("deleteProduct in ProductDAO - productId: " + productId);
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            System.out.println("Preparing to execute delete query");
            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            
            System.out.println("deleteProduct in ProductDAO - rowsAffected: " + rowsAffected);
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to retrieve all products (visible to buyers)
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products order by created_at desc";

        try (Connection con = DBUtil.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setSellerEmail(rs.getString("seller_email"));
                product.setCategory(rs.getString("category"));
                product.setSubcategory(rs.getString("subcategory"));

                // Fetch the image blob
                byte[] imageBytes = rs.getBytes("image_url");
                product.setImageUrl(imageBytes);  // Store the image as byte[] in Product

                product.setStock(rs.getInt("stock"));

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    
 // ProductDAO.java

    public Product getProductById(String productId) {
        Product product = null;
        String query = "SELECT * FROM products WHERE id = ?"; // Adjust the query according to your database schema

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id")); // Make sure to set all the necessary fields
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategory(rs.getString("category"));
                product.setSubcategory(rs.getString("subcategory"));
//                product.setImageBase64(rs.getString("image_base64")); // Assuming you have this field
                
             // Fetch the image blob
                byte[] imageBytes = rs.getBytes("image_url");
                product.setImageUrl(imageBytes);  // Store the image as byte[] in Product
                
                
                product.setSellerEmail(rs.getString("seller_email")); // Assuming you have this field
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public void decreaseStock(String productId) {
        String query = "UPDATE products SET stock = stock - 1 WHERE id = ?"; // Assuming you have an ID for each product

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Product getProductById(int productId) {  // Change String to int
        Product product = null;
        String query = "SELECT * FROM products WHERE id = ?"; 

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, productId);  // Use productId directly as int

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategory(rs.getString("category"));
                product.setSubcategory(rs.getString("subcategory"));
                byte[] imageBytes = rs.getBytes("image_url");
                product.setImageUrl(imageBytes);  
                product.setSellerEmail(rs.getString("seller_email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }
    
    public List<Product> searchProducts(String query) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? OR category LIKE ? OR subcategory LIKE ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            String searchQuery = "%" + query + "%"; // Search pattern for partial matching
            ps.setString(1, searchQuery);
            ps.setString(2, searchQuery);
            ps.setString(3, searchQuery);
            ps.setString(4, searchQuery);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setCategory(rs.getString("category"));
                product.setSubcategory(rs.getString("subcategory"));
                byte[] imageBytes = rs.getBytes("image_url");
                product.setImageUrl(imageBytes);  
                product.setSellerEmail(rs.getString("seller_email"));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


}
