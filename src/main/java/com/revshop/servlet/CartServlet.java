package com.revshop.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revshop.dao.CartDAO;
import com.revshop.dao.AddressDAO;  // New import for AddressDAO
import com.revshop.model.CartItem;
import com.revshop.model.Users;
import com.revshop.model.Address;  // Model class for Address
import com.revshop.util.DBUtil;

/**
 * Servlet implementation class CartServlet
 */

public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartDAO cartDAO = new CartDAO();
    private AddressDAO addressDAO = new AddressDAO();  // New DAO for address handling

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("loggedInUser");

        System.out.println("CartServlet doGet method triggered");

        // Check if the user is logged in
        if (currentUser == null) {
            response.sendRedirect("pages/Signin.jsp");
            return;
        }

        String userEmail = currentUser.getEmail();
        int userId = getUserIdByEmail(userEmail);

        System.out.println("User ID: " + userId);

        // Retrieve the cart items for the user
        List<CartItem> cartItems = cartDAO.getCartItems(userId);

        // Set cart items as a request attribute for the JSP
        request.setAttribute("cartItems", cartItems);
        System.out.println("Cart items: " + cartItems);
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/cart.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("CartServlet doPost method triggered");

        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("loggedInUser");
        
        CartDAO CartDAO = new CartDAO();

        if (currentUser == null) {
            // User is not logged in, redirect to login page
            response.sendRedirect("pages/Signin.jsp");
            return;
        }

        String userEmail = currentUser.getEmail(); // Assuming email is stored in session object
        int userId = getUserIdByEmail(userEmail);

        if (userId == 0) {
            // User ID could not be found, send an error response
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID could not be retrieved.");
            return;
        }

        String action = request.getParameter("action");
        System.out.println("Action: " + action);

        if ("addToCart".equals(action)) {
            // Handle adding a product to the cart
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            System.out.println("addToCart Servlet");
            System.out.println("Product ID: " + productId);
            System.out.println("Quantity: " + quantity);

            // Check if the product is already in the cart
            CartItem existingItem = cartDAO.getCartItem(userId, productId);
            if (existingItem != null) {
                // If product already exists in the cart, update the quantity
                cartDAO.updateCartItem(userId, productId, existingItem.getQuantity() + quantity);
                System.out.println("Product already in cart. Updating quantity.");
            } else {
                // If product is not in the cart, add a new entry
                cartDAO.addToCart(userId, productId, quantity);
                System.out.println("Product added to cart.");
            }

        } else if ("removeFromCart".equals(action)) {
            // Handle removing a product from the cart
            System.out.println("removeFromCart is called from jsp to servlet");
            int productId = Integer.parseInt(request.getParameter("productId"));
            int removeQuantity = Integer.parseInt(request.getParameter("removeQuantity"));

            // Remove the specified quantity
            cartDAO.removeFromCart(userId, productId, removeQuantity);

        } else if ("confirmOrder".equals(action)) {
            // When user clicks Confirm Order in cart.jsp, redirect to address-input.jsp
            System.out.println("Confirm Order action is triggered");
            response.sendRedirect(request.getContextPath() + "/AddressServlet");
            return;

        } else if ("usePreviousAddress".equals(action)) {
        	// Handle using previous address
            int previousAddressId = Integer.parseInt(request.getParameter("previousAddressId"));
            Address selectedAddress = addressDAO.getAddressById(previousAddressId);

            if (selectedAddress != null) {
                // Save the selected address and proceed with the order confirmation
                System.out.println("Using previous address with ID: " + previousAddressId);

                // Decrease stock and clear the cart
                CartDAO.processOrder(userId, selectedAddress, request, response);
                return;
            } else {
                request.setAttribute("errorMessage", "Selected address could not be found.");
            }
           
        } else if ("addNewAddress".equals(action)) {
            // Handle address input and confirm order
            System.out.println("Saving Address and Confirming Order");

            // Retrieve address information from request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String doorNo = request.getParameter("doorNo");
            String buildingName = request.getParameter("buildingName");
            String address = request.getParameter("address");
            String landmark = request.getParameter("landmark");
            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String pincode = request.getParameter("pincode");

            // Save the address in the database
            Address shippingAddress = new Address(userId, firstName, lastName, doorNo, buildingName, address, landmark, city, district, pincode);
            addressDAO.saveAddress(shippingAddress);

            // Decrease stock and clear the cart
            List<CartItem> cartItems = cartDAO.getCartItems(userId);
            if (cartItems != null && !cartItems.isEmpty()) {
                for (CartItem item : cartItems) {
                    cartDAO.decreaseStock(item.getProduct().getId(), item.getQuantity());
                }
                cartDAO.clearCart(userId);

                // Redirect to order confirmation page after successful order
                response.sendRedirect("pages/orderConfirmation.jsp");
                return;
            } else {
                System.out.println("Cart is empty");
                request.setAttribute("errorMessage", "Your cart is empty.");
            }
        }

        // Retrieve the updated cart items after performing actions
        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        request.setAttribute("cartItems", cartItems);
        // Forward the request to the cart page
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/cart.jsp");
        dispatcher.forward(request, response);
    }

    // Method to retrieve the user ID using the user's email
    private int getUserIdByEmail(String email) {
        int userId = 0;
        String query = "SELECT user_id FROM users WHERE email = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }
}
