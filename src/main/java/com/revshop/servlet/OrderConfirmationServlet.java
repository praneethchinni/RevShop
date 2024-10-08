package com.revshop.servlet;

import com.revshop.dao.OrderDAO;
import com.revshop.model.Order;
import com.revshop.model.OrderItem;
import com.revshop.model.Users;
import com.revshop.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//@WebServlet("/OrderConfirmationServlet")
public class OrderConfirmationServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("loggedInUser");
        
        if (currentUser == null) {
            response.sendRedirect("pages/Signin.jsp");
            return;
        }
        
        String userEmail = currentUser.getEmail(); // Assuming email is stored in session object
        int userId = getUserIdByEmail(userEmail);
        
        System.out.println("Userid in OrderConfirmationServlet: "+userId);
        
        // Get orders for the user
        List<Order> orders = orderDAO.getOrdersByUserId(userId);
        
     // Fetch order items for each order
        for (Order order : orders) {
            List<OrderItem> items = orderDAO.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItems(items);
        }
        System.out.println("Number of orders: " + orders.size());

        // Set orders as request attribute
        request.setAttribute("orders", orders);

        // Forward to orderConfirmation.jsp
        request.getRequestDispatcher("pages/orderConfirmation.jsp").forward(request, response);
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
