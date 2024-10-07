<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - RevShop</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <h2 class="text-center mt-5">Create an Account on RevShop</h2>
                <form action="../UsersServlet" method="POST" class="mt-4">
                    <input type="hidden" name="action" value="register"> <!-- Change to match the servlet -->
                    
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="firstName">First Name</label>
                                <input type="text" class="form-control" id="firstName" name="first_name" placeholder="Enter your first name" required>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="lastName">Last Name</label>
                                <input type="text" class="form-control" id="lastName" name="last_name" placeholder="Enter your last name" required>
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email" required>
                    </div>

                    <div class="form-group">
                        <label for="mobile">Mobile Number</label>
                        <input type="text" class="form-control" id="mobile_num" name="mobile_num" placeholder="Enter your mobile number" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required>
                    </div>
					
					<div class="form-group">
                        <label for="mobile">Address</label>
                        <input type="text" class="form-control" id="address" name="address" placeholder="Enter your Address" required>
                    </div>
					
                    <div class="form-group">
                        <label for="role">Select Role</label>
                        <select class="form-control" id="user_role" name="user_role" required onchange="ToggleSeller()"> <!-- Change to match the servlet -->
                            <option value="BUYER">Buyer</option>
                            <option value="SELLER">Seller</option>
                        </select>
                    </div>

                    <div id="businessNameDiv" class="form-group" style="display:none;">
                        <label for="businessName">Business Name (For Sellers)</label>
                        <input type="text" class="form-control" id="businessName" name="business_name" placeholder="Enter your business name">
                    </div>

                    <button type="submit" class="btn btn-success btn-block">Sign Up</button>
                </form>
                <p class="text-center mt-3">
                    Already have an account? <a href="Signin.jsp">Sign in here</a>.
                </p>

                
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!-- JavaScript to toggle business name input for sellers -->
    <script>
        function ToggleSeller() {
            const UserType = document.getElementById('user_role').value;
            const businessDiv = document.getElementById('businessNameDiv');
            if (UserType === "SELLER") {
                businessDiv.style.display = "block";
            } else {
                businessDiv.style.display = "none";
            }
        };
    </script>
</body>
</html>
