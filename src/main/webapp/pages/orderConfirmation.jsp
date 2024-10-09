<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .order-card {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
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
    <h1>Your Orders</h1>

    <!-- Check if there are any orders -->
    <c:choose>
        <c:when test="${not empty orders}">
            <div class="order-list">
                <!-- Loop through each order -->
                <c:forEach var="order" items="${orders}">
                    <div class="order-card">
                        <h2>Order ID: ${order.orderId}</h2>
                        <p><strong>Order Date:</strong> ${order.orderDate}</p>
                        <p><strong>Total Amount:</strong> $${order.totalAmount}</p>
                        <p><strong>Shipping Address:</strong> ${order.shippingAddress}</p>
                        <p><strong>Status:</strong> ${order.status}</p>

                        <!-- Display Order Items -->
                        <h3>Items in this Order:</h3>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Total</th>
                                    <th>Review</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${order.orderItems}">
                                    <tr>
                                        <td>${item.productName}</td>
                                        <td>${item.quantity}</td>
                                        <td>$${item.price}</td>
                                        <td>$${item.price.multiply(item.quantity)}</td> <!-- Total for this item -->
                                        
                                        <!-- Review Form -->
                                        <td>
                                            <form action="${pageContext.request.contextPath}/ReviewServlet" method="post">
                                                <input type="hidden" name="action" value="addReview"> <!-- Set the action parameter -->
                                                <input type="hidden" name="productId" value="${item.productId}"> <!-- Assuming you have productId in item -->
                                                <input type="hidden" name="orderId" value="${order.orderId}"> <!-- For reference -->
                                                <textarea name="reviewText" class="form-control" placeholder="Write your review..." required></textarea>
                                                <button type="submit" class="btn btn-primary mt-2">Submit Review</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <hr>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p>No orders found.</p>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
