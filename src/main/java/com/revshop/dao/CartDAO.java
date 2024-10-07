package com.revshop.dao;

import java.io.IOException;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.revshop.model.Address;
import com.revshop.model.CartItem;
import com.revshop.model.Product;
import com.revshop.util.DBUtil;

public class CartDAO {

    // Method to add an item to the cart
    public void addToCart(int userId, int productId, int quantity) {
        String query = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
        	System.out.println("User ID from session: " + userId);
        	System.out.println("Product ID from product: " + productId);
        	System.out.println("Product Quantity: " + quantity);
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
//            ps.setInt(4, quantity); // Update quantity if product already exists in the cart
            ps.executeUpdate();
            System.out.println("CART CART CART");
            System.out.println("Added to cart: userId = " + userId + ", productId = " + productId + ", quantity = " + quantity);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the quantity of an existing item in the cart
    public void updateCartItem(int userId, int productId, int quantity) {
        String query = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, userId);
            ps.setInt(3, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // Method to get all items in a user's cart
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT c.quantity, p.* FROM cart c INNER JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                System.out.println("Retrieved product: " + rs.getString("name") + ", quantity: " + rs.getInt("quantity"));
                product.setId(rs.getInt("id"));  // Changed to "id" instead of "product_id"
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                
                // Fetch the image blob (you might want to handle this differently based on your needs)
                byte[] imageBytes = rs.getBytes("image_url");
                product.setImageUrl(imageBytes);  // Store the image as byte[] in Product
                
                CartItem cartItem = new CartItem(product, rs.getInt("quantity"));
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public void processOrder(int userId, Address shippingAddress, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<CartItem> cartItems = getCartItems(userId);
        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                decreaseStock(item.getProduct().getId(), item.getQuantity());
            }
            clearCart(userId);

            // Redirect to order confirmation page after successful order
            response.sendRedirect("pages/orderConfirmation.jsp");
        } else {
            System.out.println("Cart is empty");
            request.setAttribute("errorMessage", "Your cart is empty.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/cart.jsp");
            dispatcher.forward(request, response);
        }
    }

    
    // Method to remove an item from the cart
    public void removeFromCart(int userId, int productId, int quantity) {
        String query = "UPDATE cart SET quantity = quantity - ? WHERE user_id = ? AND product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();

            // Optionally, remove the item from the cart if the quantity reaches 0
            String deleteQuery = "DELETE FROM cart WHERE user_id = ? AND product_id = ? AND quantity <= 0";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setInt(1, userId);
                deleteStmt.setInt(2, productId);
                deleteStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to clear the cart after order confirmation
    public void clearCart(int userId) {
        String query = "DELETE FROM cart WHERE user_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to decrease stock of a product
    public void decreaseStock(int productId, int quantity) {
        String query = "UPDATE products SET stock = stock - ? WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // Method to check if a cart item already exists
    public CartItem getCartItem(int userId, int productId) {
        String query = "SELECT c.quantity, p.* FROM cart c INNER JOIN products p ON c.product_id = p.id WHERE c.user_id = ? AND c.product_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id")); // Correct column from the 'products' table
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                byte[] imageBytes = rs.getBytes("image_url");
                product.setImageUrl(imageBytes);  // Store the image as byte[] in Product
                
                return new CartItem(product, rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the item is not found in the cart
    }
}
