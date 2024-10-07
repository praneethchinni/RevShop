<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.revshop.model.Product, com.revshop.model.Users" %>

<%
    Users seller = (Users) request.getAttribute("seller");
    List<Product> sellerProducts = (List<Product>) request.getAttribute("sellerProducts");
%>

<%--  
<% 
    System.out.println("Seller Details in JSP:");
    System.out.println("First Name: " + seller.getFirst_name());
    System.out.println("Last Name: " + seller.getLast_name());
    System.out.println("Address: " + seller.getAddress());
    System.out.println("Business Name: " + seller.getBusiness_name());
%> 
--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Seller Details</title>
</head>
<body>
    <h2>Seller Details</h2>
    <p><strong>First Name:</strong> <%= seller.getFirst_name() %></p>
    <p><strong>Last Name:</strong> <%= seller.getLast_name() %></p>
    <p><strong>Address:</strong> <%= seller.getAddress() %></p>
    <p><strong>Business Name:</strong> <%= seller.getBusiness_name() %></p>

    <h2>Products by <%= seller.getBusiness_name() %></h2>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Image</th>
        </tr>
        <%
            for (Product product : sellerProducts) {
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
        %>
    </table>
</body>
</html>
