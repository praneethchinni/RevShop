package com.revshop.servlet;

import com.revshop.dao.CartDAO;
import com.revshop.dao.OrderDAO;
import com.revshop.model.CartItem;
import com.revshop.model.Order;
import com.revshop.model.OrderItem;
import com.revshop.model.Users;
import com.revshop.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/PlaceOrderServlet")
public class PlaceOrderServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO = new OrderDAO();
    private CartDAO cartDAO = new CartDAO();
     

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("loggedInUser");

        if (currentUser == null) {
            response.sendRedirect("pages/Signin.jsp");
            return;
        }

        String userEmail = currentUser.getEmail();
        int userId = getUserIdByEmail(userEmail);
        String shippingAddress = (String) session.getAttribute("shippingAddress");

        if (shippingAddress == null) {
            response.sendRedirect("pages/address-input.jsp");
            return;
        }

        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        if (cartItems == null || cartItems.isEmpty()) {
            request.setAttribute("errorMessage", "Your cart is empty.");
            request.getRequestDispatcher("pages/cart.jsp").forward(request, response);
            return;
        }

        // Calculate total amount
        BigDecimal totalAmountBD = cartItems.stream()
                .map(item -> BigDecimal.valueOf(item.getProduct().getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order(userId, new Date(), totalAmountBD, shippingAddress, "Pending");

        // Start transaction
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            int orderId = orderDAO.insertOrder(order);
            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem(orderId, cartItem.getProduct().getId(), cartItem.getQuantity(), BigDecimal.valueOf(cartItem.getProduct().getPrice()));
                orderDAO.insertOrderItem(orderItem);
            }
            cartDAO.clearCart(userId);
            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle rollback
        }

        response.sendRedirect("OrderConfirmationServlet");
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
