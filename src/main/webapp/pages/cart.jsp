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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .card {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">RevShop</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/CartServlet">My Cart</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/OrderConfirmationServlet">My Orders</a>
                </li>
            </ul>
            <a class="btn btn-danger" href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
        </div>
    </nav>

    <div class="container mt-4">
        <h2>Your Cart</h2>
        
        <div class="row">
            <%
                List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
                double total = 0;

                // Check if cartItems is null or empty
                if (cartItems != null && !cartItems.isEmpty()) {
                    for (CartItem item : cartItems) {
                        total += item.getProduct().getPrice() * item.getQuantity();
            %>
            <div class="col-md-4">
                <div class="card">
                    <img class="card-img-top" src="data:image/jpeg;base64,<%= item.getProduct().getImageBase64() %>" alt="<%= item.getProduct().getName() %>">
                    <div class="card-body">
                        <h5 class="card-title"><%= item.getProduct().getName() %></h5>
                        <p class="card-text">Quantity: <%= item.getQuantity() %></p>
                        <p class="card-text">Price: $<%= item.getProduct().getPrice() %></p>
                        <form action="${pageContext.request.contextPath}/CartServlet" method="post">
                            <input type="hidden" name="action" value="removeFromCart">
                            <input type="hidden" name="productId" value="<%= item.getProduct().getId() %>">
                            <input type="number" name="removeQuantity" min="1" max="<%= item.getQuantity() %>" value="1">
                            <input type="submit" value="Remove" class="btn btn-danger">
                        </form>
                    </div>
                </div>
            </div>
            <%
                    }
                } else {
            %>
            <div class="col-12">
                <div class="alert alert-warning" role="alert">
                    Your cart is empty.
                </div>
            </div>
            <%
                }
            %>
        </div>

        <p>Total Price: $<%= total %></p>

        <form action="${pageContext.request.contextPath}/CartServlet" method="post">
            <input type="hidden" name="action" value="confirmOrder">
            <input type="submit" value="Confirm Order" class="btn btn-success">
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
