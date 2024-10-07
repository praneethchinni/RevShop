<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.revshop.dao.ProductDAO, com.revshop.model.Product" %>
<%@ page import="com.revshop.model.CartItem" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Cart</title>
</head>
<body>
    <h2>Your Cart</h2>
    <table border="1">
        <tr>
            <th>Product</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Image</th>
            <th>Actions</th>
        </tr>
        <%
            List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
            double total = 0;

            // Check if cartItems is null or empty
            if (cartItems != null && !cartItems.isEmpty()) {
                for (CartItem item : cartItems) {
                    total += item.getProduct().getPrice() * item.getQuantity();
        %>
        <tr>
            <td><%= item.getProduct().getName() %></td>
            <td><%= item.getQuantity() %></td>
            <td>$<%= item.getProduct().getPrice() %></td>
            <td>
                <td><img src="data:image/jpeg;base64,<%= item.getProduct().getImageBase64() %>" alt="<%= item.getProduct().getName() %>" width="100" height="100"></td>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/CartServlet" method="post">
                    <input type="hidden" name="action" value="removeFromCart">
                    <input type="hidden" name="productId" value="<%= item.getProduct().getId() %>">
                    <input type="number" name="removeQuantity" min="1" max="<%= item.getQuantity() %>" value="1">
                    <input type="submit" value="Remove">
                </form>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="4">Your cart is empty.</td>
        </tr>
        <%
            }
        %>
    </table>

    <p>Total Price: $<%= total %></p>

    <form action="${pageContext.request.contextPath}/CartServlet" method="post">
        <input type="hidden" name="action" value="confirmOrder">
        <input type="submit" value="Confirm Order">
    </form>
    
</body>
</html>
