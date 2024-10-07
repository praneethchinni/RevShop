<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shipping Address</title>
</head>
<body>
    <h2>Enter Shipping Address</h2>
    
    <form action="${pageContext.request.contextPath}/AddressServlet" method="post">
        <div>
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" required>
        </div>
        
        <div>
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" required>
        </div>
        
        <div>
            <label for="doorNo">Door No. and Floor:</label>
            <input type="text" id="doorNo" name="doorNo" required>
        </div>
        
        <div>
            <label for="buildingName">Name of Building:</label>
            <input type="text" id="buildingName" name="buildingName" required>
        </div>
        
        <div>
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" required>
        </div>
        
        <div>
            <label for="landmark">Landmark:</label>
            <input type="text" id="landmark" name="landmark">
        </div>
        
        <div>
            <label for="city">City:</label>
            <input type="text" id="city" name="city" required>
        </div>
        
        <div>
            <label for="district">District:</label>
            <input type="text" id="district" name="district" required>
        </div>
        
        <div>
            <label for="pincode">Pincode:</label>
            <input type="text" id="pincode" name="pincode" required>
        </div>
        
        <div>
            <input type="submit" name="action" value="Add Address">
        </div>
        
        <div>
            <input type="submit" name="action" value="Add Another Address">
        </div>
    </form>
</body>
</html>
