<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.revshop.dao.ProductDAO, com.revshop.model.Product" %>
<%@ page import="java.util.*" %>
<%
    // Initialize searchQuery, handle null when no search is performed
    String searchQuery = request.getParameter("searchQuery");
    if (searchQuery == null) {
        searchQuery = ""; // Default to an empty string when no search is performed
    }
    
    ProductDAO productDAO = new ProductDAO();
    List<Product> products = productDAO.getAllProducts();
    
    // If there's a search query, call the search method; otherwise, get all products
    if (!searchQuery.trim().isEmpty()) {
        products = productDAO.searchProducts(searchQuery);
    } else {
        products = productDAO.getAllProducts(); // Method to fetch all products when no search query is provided
    }
    
    
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - RevShop</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
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
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">RevShop</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/CartServlet">My Cart</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/OrderConfirmationServlet">My Orders</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Signin.jsp">Logout</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-4">
        <h2>Welcome back! What would you like to shop for today?</h2>
        
        <!-- Search form -->
        <form action="home.jsp" method="get" class="form-inline mb-4">
            <input type="text" class="form-control mr-2" name="searchQuery" placeholder="Search products..." value="<%= searchQuery %>">
            <button type="submit" class="btn btn-primary">Search</button>
        </form>
        
        <div class="row">
            <%
                for (Product product : products) {
            %>
            <div class="col-md-4 mb-4">
                <div class="card">
                    <img class="card-img-top" src="data:image/jpeg;base64,<%= product.getImageBase64() %>" alt="<%= product.getName() %>" style="height: 200px; object-fit: cover;">
                    <div class="card-body">
                        <h5 class="card-title"><%= product.getName() %></h5>
                        <p class="card-text"><%= product.getDescription() %></p>
                        <p class="card-text">Price: $<%= product.getPrice() %></p>
                        <p class="card-text">Category: <%= product.getCategory() %></p>
                        <p class="card-text">Subcategory: <%= product.getSubcategory() %></p>
                        <p class="card-text">
                            Stock: 
                            <%
                                if (product.getStock() <= 0) {
                            %>
                                <span style="color:red;">Out of Stock</span>
                            <%
                                } else {
                                    out.print(product.getStock());
                                }
                            %>
                        </p>
                        <form action="../ProductServlet" method="get" class="mb-2">
                            <input type="hidden" name="action" value="showSellerDetails">
                            <input type="hidden" name="sellerEmail" value="<%= product.getSellerEmail() %>">
                            <input type="submit" value="View Seller Details" class="btn btn-info">
                        </form>
                        <form action="view-reviews.jsp" method="get" class="mb-2">
                            <input type="hidden" name="productId" value="<%= product.getId() %>">
                            <button type="submit" class="btn btn-secondary">View Reviews</button>
                        </form>
                        <%
                            if (product.getStock() > 0) {
                        %>
                        <button type="button" class="btn btn-success" onclick="addToCart(<%= product.getId() %>)">Add to Cart</button>
                        <%
                            } else {
                        %>
                        <button class="btn btn-secondary" disabled>Out of Stock</button>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
</body>
</html>
