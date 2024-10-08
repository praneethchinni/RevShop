<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.revshop.dao.ProductDAO, com.revshop.model.Product" %>
<%@ page import="java.util.*" %>
<%
    ProductDAO productDAO = new ProductDAO();
    List<Product> products = productDAO.getAllProducts();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home - RevShop</title>
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
        			console.log("AJAX request successful: Product ID " + productId + " added."); // Log success
            		alert("Product added to cart!");
        		},
        		error: function() {
        			console.log("AJAX request failed."); // Log error
            		alert("Failed to add product to cart.");
        		}
    		});
		}
	</script>
</head>
<body>
    <h2>Available Products</h2>
    <!-- Add a "My Cart" button -->
	<a href="${pageContext.request.contextPath}/CartServlet" class="btn btn-primary">My Cart</a>
	<a href = "${pageContext.request.contextPath}/OrderConfirmationServlet">My Orders</a>
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category</th>
            <th>Subcategory</th>
            <th>Stock</th>
            <th>Image</th>
        </tr>
        <%
            for (Product product : products) {
        %>
        <tr>
            <td><%= product.getName() %></td>
            <td><%= product.getDescription() %></td>
            <td><%= product.getPrice() %></td>
            <td><%= product.getCategory() %></td>
            <td><%= product.getSubcategory() %></td>
            <td><%= product.getStock() %></td>
            <td>
                <td><img src="data:image/jpeg;base64,<%= product.getImageBase64() %>" alt="<%= product.getName() %>" width="100" height="100"></td>
            </td>
            
            <td>
                <!-- Button to show seller details -->
                <form action="../ProductServlet" method="get">
                    <input type="hidden" name="action" value="showSellerDetails">
                    <input type="hidden" name="sellerEmail" value="<%= product.getSellerEmail() %>">
                    <input type="submit" value="View Seller Details">
                </form>
            </td>
            
            <td>
	            <!-- Modify the Add to Cart form to use AJAX -->
				<form id="addToCartForm" method="post">
					<input type="hidden" name="action" value="addToCart">
					<input type="hidden" name="productId" value="<%= product.getId() %>">
					<input type="hidden" name="quantity" value="1">
    				<button type="button" class="btn btn-success" onclick="addToCart(<%= product.getId() %>)">Add to Cart</button>
				</form>
            </td>
            
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
