<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.revshop.model.Product, com.revshop.model.Users" %>

<%
    Users seller = (Users) request.getAttribute("seller");
    List<Product> sellerProducts = (List<Product>) request.getAttribute("sellerProducts");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Seller Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        function addToCart(productId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/CartServlet',
                method: 'POST',
                data: {
                    action: 'addToCart',
                    productId: productId,
                    quantity: 1
                },
                success: function(response) {
                    console.log("Product ID " + productId + " added to cart.");
                    alert("Product added to cart!");
                },
                error: function(xhr, status, error) {
                    console.error("Error adding to cart: " + status + ", " + error);
                    alert("Failed to add product to cart.");
                }
            });
        }
    </script>
</head>
<body>
    <div class="container mt-4">
        <h2 class="mb-4">Seller Details</h2>
        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title">Seller Information</h5>
                <p><strong>First Name:</strong> <%= seller.getFirst_name() %></p>
                <p><strong>Last Name:</strong> <%= seller.getLast_name() %></p>
                <p><strong>Address:</strong> <%= seller.getAddress() %></p>
                <p><strong>Business Name:</strong> <%= seller.getBusiness_name() %></p>
            </div>
        </div>

        <h2>Products by <%= seller.getBusiness_name() %></h2>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Image</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Product product : sellerProducts) {
                %>
                <tr>
                    <td><%= product.getName() %></td>
                    <td><%= product.getDescription() %></td>
                    <td>$<%= product.getPrice() %></td>
                    <td>
                        <%
                            if (product.getStock() <= 0) {
                        %>
                            <span class="text-danger">Out of Stock</span>
                        <%
                            } else {
                                out.print(product.getStock());
                            }
                        %>
                    </td>
                    <td><img src="data:image/jpeg;base64,<%= product.getImageBase64() %>" alt="<%= product.getName() %>" width="100" height="100"></td>
                    <td>
                        <%
                            if (product.getStock() > 0) {
                        %>
                            <button type="button" class="btn btn-success" onclick="addToCart(<%= product.getId() %>)">Add to Cart</button>
                        <%
                            } else {
                        %>
                            <button disabled class="btn btn-secondary">Out of Stock</button>
                        <%
                            }
                        %>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>
