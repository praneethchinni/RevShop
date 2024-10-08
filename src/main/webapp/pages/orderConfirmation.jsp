<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> <!-- Assuming you have a stylesheet -->
</head>
<body>

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
                    <p><strong>Total Amount:</strong> ${order.totalAmount}</p>
                    <p><strong>Shipping Address:</strong> ${order.shippingAddress}</p>
                    <p><strong>Status:</strong> ${order.status}</p>

                    <!-- Display Order Items -->
                    <h3>Items in this Order:</h3>
                    <table border="1" cellpadding="10">
                        <thead>
                            <tr>
                                <th>Product Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${order.orderItems}">
                                <tr>
                                    <td>${item.productName}</td>
                                    <td>${item.quantity}</td>
                                    <td>${item.price}</td>
                                    <td>${item.price.multiply(item.quantity)}</td> <!-- Total for this item -->
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

</body>
</html>
