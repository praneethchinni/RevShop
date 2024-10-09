<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.revshop.model.Product" %>
<%
    if (request.getParameter("action") == null) {
        response.sendRedirect("../ProductServlet?action=getProducts");
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> <!-- For responsive design -->
    <title>Seller Portal</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">RevShop Seller Portal</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#" onclick="showAddProduct()">Add Product</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/SoldItemsServlet">View Sold Items</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-4">
        <div id="addProductSection" style="display: none;">
            <h2>Add a New Product</h2>
            <form action="${pageContext.request.contextPath}/ProductServlet" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="addProduct">

                <div class="form-group">
                    <label for="name">Product Name:</label>
                    <input type="text" class="form-control" name="name" required>
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" name="description" required></textarea>
                </div>

                <div class="form-group">
                    <label for="price">Price:</label>
                    <input type="text" class="form-control" name="price" required>
                </div>

                <div class="form-group">
                    <label for="category">Category:</label>
                    <input type="text" class="form-control" name="category" required>
                </div>

                <div class="form-group">
                    <label for="subcategory">Subcategory:</label>
                    <input type="text" class="form-control" name="subcategory" required>
                </div>

                <div class="form-group">
                    <label for="stock">Stock:</label>
                    <input type="number" class="form-control" name="stock" required>
                </div>

                <div class="form-group">
                    <label for="image">Product Image:</label>
                    <input type="file" class="form-control" name="image" required>
                </div>

                <button type="submit" class="btn btn-primary">Add Product</button>
            </form>
        </div>

        <h2>Your Products</h2>
        <div class="row">
            <%
                List<Product> products = (List<Product>) request.getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (Product product : products) {
            %>
            <div class="col-md-4 mb-4">
                <div class="card">
                    <img src="data:image/jpeg;base64,<%= product.getImageBase64() %>" class="card-img-top" alt="<%= product.getName() %>">
                    <div class="card-body">
                        <h5 class="card-title"><%= product.getName() %></h5>
                        <p class="card-text"><%= product.getDescription() %></p>
                        <p class="card-text">Price: <%= product.getPrice() %></p>
                        <p class="card-text">Stock: <%= product.getStock() %></p>
                        <form action="${pageContext.request.contextPath}/ProductServlet" method="post" style="display: inline;">
    						<input type="hidden" name="action" value="deleteProduct">
    						<input type="hidden" name="productId" value="<%= product.getId() %>">
    						<button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this product?');">
        					<i class="fas fa-trash"></i> Delete
    						</button>
						</form>
                    </div>
                </div>
            </div>
            <%
                    }
                } else {
            %>
            <div class="alert alert-warning" role="alert">
                No products found.
            </div>
            <%
                }
            %>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        function showAddProduct() {
            document.getElementById('addProductSection').style.display = 'block';
        }
    </script>
</body>
</html>
