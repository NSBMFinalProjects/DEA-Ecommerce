<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Optional" %>
<%@ page import="nsbm.dea.web.dao.UserDAO" %>
<%@ page import="nsbm.dea.web.models.User" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
    <title>Shopping Cart</title>
</head>
<body>
<div class="product-list">
    <h2>Products</h2>
    <div id="product-container"></div>
</div>

<div class="cart">
    <h2>Cart</h2>
    <table id="cart-table">
        <thead>
        <tr>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody id="cart-container"></tbody>
    </table>
    <div id="totalPrice"></div>
    <div class="clearButton" id="clear-cart">
        <p class="pe-none">Clear cart</p>
    </div>
    <button id="proceed-to-checkout">Proceed to Checkout</button>
</div>

<script src="script.js"></script>
</body>
</html>
