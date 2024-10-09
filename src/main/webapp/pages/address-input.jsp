<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.revshop.dao.AddressDAO, com.revshop.model.Address" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Shipping Address</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center mb-4">Enter Shipping Address</h2>

        <!-- Display previous addresses if available -->
        <div class="card mb-4">
            <div class="card-header">
                <h3>Your Previous Addresses</h3>
            </div>
            <div class="card-body">
                <%
                List<Address> previousAddresses = (List<Address>) request.getAttribute("previousAddresses");
                if (previousAddresses != null && !previousAddresses.isEmpty()) {
                %>
                    <form action="${pageContext.request.contextPath}/CartServlet" method="post">
                        <input type="hidden" name="action" value="usePreviousAddress">
                        <div class="form-group">
                            <label for="previousAddressId">Select a previous address:</label>
                            <select class="form-control" id="previousAddressId" name="previousAddressId">
                                <%
                                for (Address address : previousAddresses) {
                                    String addressText = address.getFirstName() + " " + address.getLastName() + ", " +
                                                         address.getDoorNo() + ", " + address.getBuildingName() + ", " +
                                                         address.getAddress() + ", " + address.getLandmark() + ", " +
                                                         address.getCity() + ", " + address.getDistrict() + ", " +
                                                         address.getPincode();
                                %>
                                <option value="<%= address.getId() %>"><%= addressText %></option>
                                <%
                                }
                                %>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Use This Address And Confirm order</button>
                    </form>
                <%
                } else {
                %>
                    <p>You have not added any addresses yet.</p>
                <%
                }
                %>
            </div>
        </div>

        <!-- Form to add a new address -->
        <div class="card">
            <div class="card-header">
                <h3>Add a New Address</h3>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/CartServlet" method="post">
                    <input type="hidden" name="action" value="addNewAddress">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="firstName">First Name</label>
                            <input type="text" class="form-control" id="firstName" name="firstName" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="lastName">Last Name</label>
                            <input type="text" class="form-control" id="lastName" name="lastName" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="doorNo">Door No. and Floor</label>
                        <input type="text" class="form-control" id="doorNo" name="doorNo" required>
                    </div>
                    <div class="form-group">
                        <label for="buildingName">Name of Building</label>
                        <input type="text" class="form-control" id="buildingName" name="buildingName" required>
                    </div>
                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" class="form-control" id="address" name="address" required>
                    </div>
                    <div class="form-group">
                        <label for="landmark">Landmark</label>
                        <input type="text" class="form-control" id="landmark" name="landmark">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="city">City</label>
                            <input type="text" class="form-control" id="city" name="city" required>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="district">District</label>
                            <input type="text" class="form-control" id="district" name="district" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pincode">Pincode</label>
                        <input type="text" class="form-control" id="pincode" name="pincode" required>
                    </div>
                    <button type="submit" class="btn btn-success">Confirm Order</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
