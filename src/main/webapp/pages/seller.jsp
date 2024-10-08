<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.revshop.model.Product" %>
<%
    // Redirect to fetch products on initial load
    if (request.getParameter("action") == null) {
        response.sendRedirect("../ProductServlet?action=getProducts");
    }
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Product - Seller Portal</title>
</head>
<body>
    <h2>Add a New Product</h2>
    <form action="${pageContext.request.contextPath}/ProductServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="addProduct">
        
        <label for="name">Product Name:</label>
        <input type="text" name="name" required><br>
        
        <label for="description">Description:</label>
        <textarea name="description" required></textarea><br>
        
        <label for="price">Price:</label>
        <input type="text" name="price" required><br>
        
        <label for="category">Category:</label>
        <input type="text" name="category" required><br>
        
        <label for="subcategory">Subcategory:</label>
        <input type="text" name="subcategory" required><br>
        
        <label for="stock">Stock:</label>
        <input type="number" name="stock" required><br>
        
        <label for="image">Product Image:</label>
        <input type="file" name="image" required><br>
        
        <input type="submit" value="Add Product">
    </form>

    <h2>Your Products</h2>
    <table>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Image</th>
        </tr>
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
                for (Product product : products) {
        %>
        <tr>
            <td><%= product.getName() %></td>
            <td><%= product.getDescription() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= product.getStock() %></td>
			<td><img src="data:image/jpeg;base64,<%= product.getImageBase64() %>" alt="<%= product.getName() %>" width="100" height="100"></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5">No products found.</td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>