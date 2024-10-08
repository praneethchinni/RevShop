package com.revshop.servlet;

import com.revshop.dao.ProductDAO;

import com.revshop.dao.UsersDAO;
import com.revshop.model.Product;
import com.revshop.model.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ProductServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ProductServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        System.out.println("doPost is triggered with action: " + action);

        if ("addProduct".equals(action)) {
            addProduct(request, response);
        } else if ("editStock".equals(action)) {
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int newStock = Integer.parseInt(request.getParameter("newStock"));

                // Update stock using ProductDAO
                ProductDAO productDAO = new ProductDAO();
                boolean updated = productDAO.updateStock(productId, newStock);

                if (updated) {
                    response.sendRedirect("your-success-page.jsp");
                } else {
                    request.setAttribute("errorMessage", "Failed to update stock for product ID: " + productId);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid product ID or stock value.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if("deleteProduct".equals(action)) {
            System.out.println("Delete product action triggered");

            int productId = Integer.parseInt(request.getParameter("productId"));
            boolean isDeleted = productDAO.deleteProductById(productId);

            if (isDeleted) {
                System.out.println("Product deleted successfully");
                response.sendRedirect("pages/seller.jsp");
            } else {
                System.out.println("Failed to delete product");
                response.sendRedirect("pages/error.jsp");
            }
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        System.out.println("doGet is triggered with action: " +action);

//        if ("getProducts".equals(action)) {
//            getProducts(request, response);
//        }
        
        if ("getProducts".equals(action)) {
            getProducts(request, response);
        } else if ("showSellerDetails".equals(action)) {
            showSellerDetails(request, response);
        }
    }

    // Method to add the product with image upload handling
    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form fields
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        
        HttpSession session = request.getSession(false); // Get the session, do not create a new one
        String sellerEmail = (String) session.getAttribute("sellerEmail");

        if (sellerEmail == null) {
            response.sendRedirect("Signin.jsp"); // Redirect to login if session is invalid or sellerEmail is missing
            return;
        }

        String category = request.getParameter("category");
        String subcategory = request.getParameter("subcategory");
        int stock = Integer.parseInt(request.getParameter("stock"));

        // Handle file upload and convert to byte array
        Part filePart = request.getPart("image"); // Retrieves <input type="file" name="image">
        byte[] imageBytes = null;

        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream();
                 ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                int nRead;
                byte[] data = new byte[16384];
                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                imageBytes = buffer.toByteArray(); // Convert image into byte array
            }
        }

        // Create product object with image
        Product product = new Product(name, description, price, sellerEmail, category, subcategory, imageBytes, stock);

        // Save product to the database
        boolean success = productDAO.addProduct(product);

        if (success) {
            response.sendRedirect("pages/seller-success.jsp");
        } else {
            response.sendRedirect("pages/seller-fail.jsp");
        }
    }

    // Method to retrieve products by seller
    private void getProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if(session != null) {
            String sellerEmail = (String) session.getAttribute("sellerEmail");

            if (sellerEmail != null) {
                List<Product> products = productDAO.getProductsBySellerEmail(sellerEmail);
                request.setAttribute("products", products);

                // Forward to JSP
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/seller.jsp");
                dispatcher.forward(request, response);  // Forward to JSP
                return;
            } else {
                // Redirect if sellerEmail is not present
                response.sendRedirect("login.jsp");
            }
        } else {
            // If session is null, redirect to login page
            response.sendRedirect("login.jsp");
        }
    }


    
 // ProductServlet.java

    private void showSellerDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sellerEmail = request.getParameter("sellerEmail");

        // Fetch seller details using sellerEmail
        UsersDAO userDAO = new UsersDAO();  // Assuming you have a UserDAO to fetch user details
        Users seller = userDAO.getUserByEmail(sellerEmail);

        // Fetch products by the seller
        List<Product> sellerProducts = productDAO.getProductsBySellerEmail(sellerEmail);

//        System.out.println("Seller Details in Servlet:");
//        System.out.println("First Name: " + seller.getFirst_name());
//        System.out.println("Last Name: " + seller.getLast_name());
//        System.out.println("Address: " + seller.getAddress());
//        System.out.println("Business Name: " + seller.getBusiness_name());

        
        // Set the attributes to forward to JSP
        request.setAttribute("seller", seller);
        request.setAttribute("sellerProducts", sellerProducts);

        // Forward to seller-details.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/seller-details.jsp");
        dispatcher.forward(request, response);
    }

    // Method to extract file name (not needed unless saving to disk, but left in for possible use)
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}