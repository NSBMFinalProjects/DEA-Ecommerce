<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
    />
    <style>
        @import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");

        * {
            font-family: "Poppins", sans-serif !important;
        }
        li {
            list-style: none !important;
        }
        a {
            text-decoration: none;
        }
        .topcard:hover {
            box-shadow: 5px 5px 0px #ffffff7c !important;
            transform: scale(0.99) !important;
        }
        .buttonCommon:hover {
            box-shadow: 3px 3px 0px #ffffff7c !important;
            transform: scale(0.99) !important;
        }
        table {
            border-collapse: collapse;
            border-spacing: 0;
            width: 100%;
        }
        th,
        td {
            text-align: left;
            padding: 8px;
            color: #ffffff;
        }
    </style>
    <title>Title</title>
</head>
<body>
<%@include file="header.html"%>

<section>
    <div
            style="
          width: 100%;
          background-color: #c7c7c7c7;
          display: flex;
          padding: 50px 0px 50px 0px;
        "
    >
        <div
                style="
            width: 80%;
            padding: 40px;
            background-color: #203c55;
            margin: auto;
            box-shadow: 10px 10px 0px #ffffff;
          "
        >
            <div style="display: flex; width: 100%">
                <div style="width: 25%; padding: 20px">
                    <div
                            style="
                  width: 100%;
                  height: 250px;
                  background-color: #ffffff;
                  box-shadow: 10px 10px 0px #c3c3c3;
                  overflow: hidden;
                  justify-content: center;
                  display: flex;
                "
                    >
                        <img
                                src="assets/profilePlaceholder.jpg"
                                style="width: auto"
                                alt=""
                        />
                    </div>
                    <a href="#"
                    ><div
                            class="buttonCommon"
                            style="
                    background-color: #ffffff;
                    width: 100%;
                    color: #203c55;
                    font-size: 22px;
                    font-weight: bold;
                    padding-left: 15px;
                    padding-right: 15px;
                    box-shadow: 6px 6px 0px #c3c3c3;
                    transition: 0.4s ease-in-out;
                    margin-top: 50px;
                  "
                    >
                        <center>
                            <p
                                    class="pe-none"
                                    style="padding-top: 5px; padding-bottom: 5px"
                            >
                                Edit profile
                            </p>
                        </center>
                    </div></a
                    >
                </div>
                <div style="width: 75%; padding: 20px 40px 20px 40px">
                    <p style="color: #ffffff; font-size: 30px; font-weight: 600">
                        User Details
                    </p>
                    <div
                            style="
                  background-color: #ffffff;
                  width: 100%;
                  height: 2px;
                  margin-top: -10px;
                "
                    ></div>
                    <div
                            style="
                  display: flex;
                  justify-content: space-between;
                  margin-top: 40px;
                "
                    >
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            First Name
                        </p>
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            Hello
                        </p>
                    </div>
                    <div
                            style="
                  display: flex;
                  justify-content: space-between;
                  margin-top: -10px;
                "
                    >
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            Last Name
                        </p>
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            World
                        </p>
                    </div>
                    <div
                            style="
                  display: flex;
                  justify-content: space-between;
                  margin-top: -10px;
                "
                    >
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            Email
                        </p>
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            helloworld@gmail.com
                        </p>
                    </div>
                    <div
                            style="
                  display: flex;
                  justify-content: space-between;
                  margin-top: -10px;
                "
                    >
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            Contact Number
                        </p>
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            1234567890
                        </p>
                    </div>
                    <div
                            style="
                  display: flex;
                  justify-content: space-between;
                  margin-top: -10px;
                "
                    >
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            Shipping Address
                        </p>
                        <p style="font-size: 18px; color: #ffffff; font-weight: 600">
                            No 150, testing, testing.
                        </p>
                    </div>
                </div>
            </div>
            <div style="padding: 20px; margin-top: -10px">
                <p style="color: #ffffff; font-size: 30px; font-weight: 600">
                    Order Details
                </p>
                <div
                        style="background-color: #ffffff; width: 100%; height: 2px"
                ></div>

                <div style="overflow-x: auto">
                    <table>
                        <tr>
                            <th>Item Name</th>
                            <th>Ordered Date</th>
                            <th>Quantity</th>
                            <th>Prize</th>
                            <th>Current State</th>
                        </tr>
                        <tr>
                            <td>TEXT PRINT T-SHIRT</td>
                            <td>2024.03.29</td>
                            <td>3</td>
                            <td>LKR 5000</td>
                            <td>Shipped</td>
                        </tr>
                        <tr>
                            <td>HERACLIO FOURNIER SWEATSHIRT</td>
                            <td>2024.04.03</td>
                            <td>2</td>
                            <td>LKR 3000</td>
                            <td>Delivered</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>

<%@include file="footer.html"%>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"
></script>
</body>
</html>
