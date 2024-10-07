<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.revshop.dao.AddressDAO, com.revshop.model.Address" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shipping Address</title>
</head>
<body>

    <h2>Enter Shipping Address</h2>

    <!-- Display previous addresses if available -->
    <h3>Your Previous Addresses</h3>
    <%
    List<Address> previousAddresses = (List<Address>) request.getAttribute("previousAddresses");
    if (previousAddresses != null && !previousAddresses.isEmpty()) {
%>
    <form action="${pageContext.request.contextPath}/CartServlet" method="post">
        <input type="hidden" name="action" value="usePreviousAddress">
        <select name="previousAddressId">
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
        <input type="submit" value="Use This Address">
    </form>
<%
    } else {
%>
    <p>You have not added any addresses yet.</p>
<%
    }
%>


    <!-- Form to add a new address -->
    <h3>Add a New Address</h3>
    <form action="${pageContext.request.contextPath}/CartServlet" method="post">
        <input type="hidden" name="action" value="addNewAddress">
        First Name: <input type="text" name="firstName" required><br>
        Last Name: <input type="text" name="lastName" required><br>
        Door No. and Floor: <input type="text" name="doorNo" required><br>
        Name of Building: <input type="text" name="buildingName" required><br>
        Address: <input type="text" name="address" required><br>
        Landmark: <input type="text" name="landmark"><br>
        City: <input type="text" name="city" required><br>
        District: <input type="text" name="district" required><br>
        Pincode: <input type="text" name="pincode" required><br>
        <input type="submit" value="Confirm and Add Address">
    </form>

</body>
</html>
