<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>RevShop - Home Page</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: url('https://source.unsplash.com/1600x900/?shopping') no-repeat center center fixed;
            background-size: cover; /* Full background image */
            margin: 0;
            padding: 0;
            display: flex; /* Flexbox for centering */
            justify-content: center; /* Center horizontally */
            align-items: center; /* Center vertically */
            height: 100vh; /* Full viewport height */
        }

        .container {
            text-align: center;
            background: rgba(255, 255, 255, 0.3); /* Semi-transparent white */
            backdrop-filter: blur(10px); /* Frosted glass effect */
            border-radius: 15px; /* Rounded corners */
            padding: 40px 20px; /* Padding for spacing */
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.5); /* Shadow for depth */
            color: #333; /* Dark text color for readability */
            max-width: 600px; /* Max width for the container */
        }

        h2 {
            margin: 10px 0;
            font-size: 2.5em; /* Larger heading */
            color: #ff6b6b; /* Vibrant pinkish-red */
        }

        p {
            font-size: 1.2em; /* Increased font size for better readability */
            margin-bottom: 20px;
            color: #555; /* Darker text color for contrast */
        }

        button {
            padding: 12px 25px;
            background-color: #4caf50; /* Bright green */
            border: none;
            border-radius: 8px;
            color: white;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
        }

        button:hover {
            background-color: #388e3c; /* Darker green on hover */
            transform: scale(1.05); /* Slightly enlarge the button on hover */
        }

        .icon {
            font-size: 64px; /* Larger icon for emphasis */
            margin-bottom: 20px;
            color: #ff6b6b; /* Icon color matches the heading */
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="icon">
            <i class="fas fa-shopping-cart"></i>
        </div>
        <h2>Welcome to RevShop!</h2>
        <p>Your one-stop shop for all your needs. Sign in to explore our amazing products!</p>
        <form action="pages/Signin.jsp">
            <button type="submit">Login</button>
        </form>
    </div>
</body>
</html>
