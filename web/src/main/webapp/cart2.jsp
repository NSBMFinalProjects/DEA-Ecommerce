<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .product-list, .cart {
            margin: 20px;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        h2 {
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            margin: 10px 0;
            border: none;
            cursor: pointer;
            text-align: center;
            display: inline-block;
            font-size: 16px;
        }

        button:hover {
            background-color: #45a049;
        }
        .clearButton {
            padding: 5px 20px;
            background-color: #e7e7e7;
            margin-top: 10px;
            box-shadow: 5px 5px 0px #ffffff;
            transition: 0.4s ease-in-out;
        }

        .clearButton p {
            color: #203c55;
            font-size: 20px;
            margin: auto;
            font-weight: 600;
        }

        #totalPrice {
            margin-top: 20px;
            font-size: 20px;
            font-weight: bold;
        }
        #proceed-to-checkout {
            background-color: #008CBA;
            padding: 10px 20px;
            margin: 10px 0;
            border: none;
            cursor: pointer;
            text-align: center;
            display: inline-block;
            font-size: 16px;
        }

        #proceed-to-checkout:hover {
            background-color: #007B9A;
        }
        @media (max-width: 768px) {
            .product-list, .cart {
                margin: 10px;
                padding: 10px;
            }

            h2 {
                font-size: 18px;
            }

            button {
                padding: 8px 16px;
                font-size: 14px;
            }
        }

    </style>
    <title>Shopping Cart</title>
</head>
<body>
<div class="product-list">
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
<script>
    function addToCart(product) {
        let cart = JSON.parse(localStorage.getItem('cart')) || { subTotal: 0, totalQuty: 0, cart: [] };
        let existingProductIndex = cart.cart.findIndex(item => item.id === product.id && item.categoy.id === product.categoy.id && item.color.id === product.color.id);
        if (existingProductIndex >= 0) {
            cart.cart[existingProductIndex].quantity += product.quantity;
            cart.cart[existingProductIndex].subTotal = cart.cart[existingProductIndex].price * cart.cart[existingProductIndex].quantity;
        } else {
            product.subTotal = product.price * product.quantity;
            cart.cart.push(product);
            cart.totalQuty += 1;
        }
        cart.subTotal = cart.cart.reduce((total, item) => total + item.subTotal, 0);
        localStorage.setItem('cart', JSON.stringify(cart));
        updateCartDisplay(cart);
    }

    function updateCartDisplay(cart) {
        const cartContainer = document.getElementById('cart-container');
        cartContainer.innerHTML = '';
        let totalPrice = 0;

        if (cart.cart.length > 0) {
            cart.cart.forEach(item => {
                totalPrice += item.price * item.quantity;

                let row = document.createElement('tr');
                row.innerHTML = `
                            <td>${item.title}</td>
                            <td>${item.quantity}</td>
                            <td>$${item.price}</td>
                            <td>$${item.price * item.quantity}</td>
                            <td><button onclick="removeFromCart(${item.id})">Remove</button></td>
                        `;
                cartContainer.appendChild(row);
            });
            document.getElementById('totalPrice').textContent = `Total: $${totalPrice.toFixed(2)}`;
        } else {

            let row = document.createElement('tr');
            row.innerHTML = `<td colspan="5">Your cart is empty.</td>`;
            cartContainer.appendChild(row);
        }

        document.getElementById('totalPrice').textContent = `Total Price: $${totalPrice.toFixed(2)}`;
    }



    function removeFromCart(productId) {
        let cart = JSON.parse(localStorage.getItem('cart')) || { cart: [] };
        cart.cart = cart.cart.filter(item => item.id !== productId);
        localStorage.setItem('cart', JSON.stringify(cart));
        updateCartDisplay(JSON.parse(localStorage.getItem('cart')));
    }

    function clearCart() {
        localStorage.removeItem('cart');
        updateCartDisplay({ subTotal: 0, totalQuty: 0, cart: [] });
    }

    document.addEventListener('DOMContentLoaded', () => {
        fetch('product.json')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch cart data');
                }
                return response.json();
            })
            .then(product => {

                addToCart(product);
                updateCartDisplay(JSON.parse(localStorage.getItem('cart')));
            })
            .catch(error => {
                console.error('Error loading cart:', error);
                alert('Failed to load cart data');
            });
    });

    document.getElementById('clear-cart').addEventListener('click', clearCart);

    document.getElementById('proceed-to-checkout').addEventListener('click', function() {
        const cart = JSON.parse(localStorage.getItem('cart')) || { cart: [] };
        const cartItems = cart.cart.map(item => `item=${encodeURIComponent(JSON.stringify(item))}`).join('&');

        window.location.href = `checkout.jsp?${cartItems}`;
    });

</script>


</body>
</html>

