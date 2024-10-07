package com.revshop.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revshop.dao.UsersDAO;
import com.revshop.model.Users;

@WebServlet("/UsersServlet")
public class UsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsersDAO usersDAO;

    @Override
    public void init() {
        usersDAO = new UsersDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("register".equalsIgnoreCase(action)) { // Adjusted here
            registerUser(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            loginUser(request, response);
        }   
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        long mobile = Long.parseLong(request.getParameter("mobile_num")); // Capture mobile
        String password = request.getParameter("password");
        String userRole = request.getParameter("user_role"); // BUYER or SELLER
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String address = request.getParameter("address");
        String businessName = null;

        if ("SELLER".equalsIgnoreCase(userRole)) {
            businessName = request.getParameter("business_name");
        }
        
        Users user = new Users();
        user.setEmail(email);
        user.setMobil_num(mobile); // Set the mobile number
        user.setPassword(password);
        user.setUser_role(userRole);
        user.setFirst_name(firstName);
        user.setLast_name(lastName);
        user.setAddress(address);
        user.setBusiness_name(businessName); // Null for buyers
        
        boolean isRegistered = usersDAO.registerUser(user);
        
        if (isRegistered) {
            response.sendRedirect("pages/register-success.jsp"); // Redirect to success page
        } else {
            response.sendRedirect("pages/register-fail.jsp"); // Redirect to failure page
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        Users user = usersDAO.loginUser(email, password);
        
        if (user != null) {
        	// User found, log success and store user ID in the session
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);  // Optional, store entire user object if needed


            // Different handling based on user role
            if ("BUYER".equalsIgnoreCase(user.getUser_role())) {  

                response.sendRedirect("pages/home.jsp");
            } else if ("SELLER".equalsIgnoreCase(user.getUser_role())) {
                // Store the seller's email in session for future use
                session.setAttribute("sellerEmail", user.getEmail());
                // Redirect seller to seller dashboard
                response.sendRedirect("pages/seller.jsp");
            }
        }else {
            response.sendRedirect("pages/login-fail.jsp");
        }
    }
}
