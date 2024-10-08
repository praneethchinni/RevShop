package com.revshop.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revshop.dao.AddressDAO;
import com.revshop.model.Address;
import com.revshop.model.Users;
import com.revshop.util.DBUtil;

/**
 * Servlet implementation class AddressServlet
 */
public class AddressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AddressDAO addressDAO = new AddressDAO(); // DAO for address operations
    
    // Retrieves the user's previous addresses to display in the address-input.jsp page
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("doGet called in AddressServlet");
    	
    	HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("loggedInUser");

        if (currentUser == null) {
            response.sendRedirect("pages/Signin.jsp");
            return;
        }

     // Get user ID using the email from the session
        String userEmail = currentUser.getEmail();
        int userId = getUserIdByEmail(userEmail);
                
        List<Address> previousAddresses = addressDAO.getAddressesByUserId(userId); // Check if this retrieves the addresses correctly
        request.setAttribute("previousAddresses", previousAddresses); // Set as request attribute
        request.getRequestDispatcher("pages/address-input.jsp").forward(request, response); // Forward to JSP
        
        System.out.println("User ID: " + userId);
        System.out.println("Previous Addresses: " + previousAddresses);
    }

    
    // Handles POST requests for saving or using addresses
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("loggedInUser");
        
        if (currentUser == null) {
            response.sendRedirect("pages/Signin.jsp");
            return;
        }

        // Get user ID using the email from the session
        String userEmail = currentUser.getEmail();
        int userId = getUserIdByEmail(userEmail);

        // Handle actions based on the type of request
        if ("usePreviousAddress".equals(action)) {
            // Use the selected previous address
            int addressId = Integer.parseInt(request.getParameter("previousAddressId"));
            Address selectedAddress = addressDAO.getAddressById(addressId); // Updated method call
            // Redirect or forward to order confirmation with the selected address
            if (selectedAddress != null) {
                request.setAttribute("selectedAddress", selectedAddress);
//                response.sendRedirect(request.getContextPath() + "/OrderConfirmationServlet");
                request.getRequestDispatcher("pages/orderConfirmation.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Address not found.");
            }
        } else if ("addNewAddress".equals(action)) {
            // Collect new address details
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String doorNo = request.getParameter("doorNo");
            String buildingName = request.getParameter("buildingName");
            String address = request.getParameter("address");
            String landmark = request.getParameter("landmark");
            String city = request.getParameter("city");
            String district = request.getParameter("district");
            String pincode = request.getParameter("pincode");

            // Create an Address object and save it to the database
            Address newAddress = new Address(userId, firstName, lastName, doorNo, buildingName, address, landmark, city, district, pincode);
            addressDAO.saveAddress(newAddress);

            // Redirect based on the action taken
            if ("Add Another Address".equals(request.getParameter("nextAction"))) {
                response.sendRedirect("pages/shipping.jsp");
            } else {
                response.sendRedirect("pages/orderConfirmation.jsp");
            }
        }
    }

    // Retrieves the user ID using the user's email
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

    // Retrieves the user's previous addresses to display in the address-input.jsp page
    
}
