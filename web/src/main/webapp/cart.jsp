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
        <style>
            @import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
            * { font-family: "Poppins", sans-serif !important; }
            li { list-style: none !important; }
            a { text-decoration: none; }

            table {
                border-collapse: collapse;
                border-spacing: 0;
                width: 100%;
            }

            th,
            td {
                text-align: center;
                padding: 8px;
                color: #ffffff;
                font-size: 16px;
            }
            .clearButton:hover {
                box-shadow: 2px 2px 0px #ffffff !important;
                transform: scale(0.99);
            }
            .checkoutButton:hover {
                box-shadow: 4px 4px 0px #ffffff !important;
                transform: scale(0.99);
            }
        </style>
        <title>Title</title>

    </head>
    <body>
    <%@include file="header.html"%>

    <section
            style="width: 100%; background-color: #e7e7e7; padding: 60px 0px 60px 0px"
    >
        <div
                style="
              width: 80%;
              background-color: #203c55;
              box-shadow: 10px 10px 0px #ffffff;
              margin: auto;
              padding: 40px 40px 60px 40px;
            "
        >
            <p
                    style="
                color: #ffffff;
                font-size: 40px;
                width: 100%;
                text-align: left;
                font-weight: 600;
              "
            >
                Shopping Cart
            </p>
            <p
                    style="
                color: #ffffff;
                font-size: 20px;
                width: 100%;
                text-align: left;
                font-weight: normal;
                letter-spacing: 6px;
                margin-top: -15px;
              "
            >
                Checkout your cart
            </p>
            <div style="width: 100%; height: 3px; background-color: #ffffff"></div>
            <div style="overflow-x: auto; margin-top: 25px">
                <table>
                    <tr>
                        <th>Product</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Size</th>
                        <th>Ordered Date</th>
                        <th>Action</th>
                    </tr>
                    <tr>
                        <td style="width: 120px !important">
                            <img
                                    src="assets/Products/Men/m1/mp11.jpg"
                                    style="width: 120px"
                                    alt=""
                            />
                        </td>
                        <td>T-shirt 123</td>
                        <td style="width: 120px !important">5</td>
                        <td style="width: 120px !important">Small</td>
                        <td style="width: 200px !important">2024/08/26</td>
                        <td style="width: 50px !important">
                            <center>
                                <a href="#"><img src="assets/delete.svg" alt="" /></a>
                            </center>
                        </td>
                    </tr>
                </table>
            </div>
            <div
                    style="
                width: 100%;
                height: 3px;
                background-color: #ffffff;
                margin-top: 40px;
              "
            ></div>
            <div
                    style="
                width: 100%;
                display: flex;
                justify-content: end;
                padding-right: 5px;
              "
            >
                <div
                        class="clearButton"
                        style="
                  padding: 5px 20px 5px 20px;
                  background-color: #e7e7e7;
                  margin-top: 10px;
                  box-shadow: 5px 5px 0px #ffffff;
                  transition: 0.4s ease-in-out;
                "
                >
                    <p
                            class="pe-none"
                            style="
                    color: #203c55;
                    font-size: 20px;
                    margin: auto;
                    font-weight: 600;
                  "
                    >
                        Clear cart
                    </p>
                </div>
            </div>
            <p style="font-size: 35px; color: #ffffff; font-weight: 600">Note :</p>
            <p
                    style="
                font-size: 16px;
                color: #ffffff;
                font-weight: 500;
                margin-top: -10px;
              "
            >
                Payment method : Cash on Delivery ( COD )
            </p>
            <p
                    style="
                font-size: 16px;
                color: #ffffff;
                font-weight: 500;
                margin-top: -16px;
              "
            >
                Order will delivered within 3 to 4 business days across island wide.
            </p>
            <div
                    style="
                width: 100%;
                height: 3px;
                background-color: #ffffff;
                margin-top: 40px;
              "
            ></div>
            <div
                    style="
                width: 100%;
                display: flex;
                justify-content: center;
                margin-top: 40px;
              "
            >
                <div
                        class="checkoutButton"
                        style="
                  padding: 5px 20px 5px 20px;
                  width: 60%;
                  background-color: #e7e7e7;
                  margin-top: 10px;
                  box-shadow: 8px 8px 0px #ffffff;
                  transition: 0.4s ease-in-out;
                "
                        onclick="redirectToProductDetails()"
                >
                    <p
                            class="pe-none"
                            style="
                    color: #203c55;
                    font-size: 20px;
                    margin: auto;
                    text-align: center;
                    font-weight: 600;
                  "
                    >
                        Proceed to checkout
                    </p>
                </div>
            </div>
        </div>

        <script>
            function redirectToProductDetails() {
                window.location.href = 'checkout.jsp';
            }
        </script>
    </section>


    <%@include file="footer.html"%>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>
    </html>
