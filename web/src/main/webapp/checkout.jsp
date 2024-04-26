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

        .buttonCommon:hover {
            box-shadow: 3px 3px 0px #ffffff7c !important;
            transform: scale(0.99) !important;
        }
        .inputField {
            height: 50px;
            width: 100%;
            font-size: 16px;
            margin-top: -10px;
            padding-left: 10px;
            padding-right: 10px;
            margin-right: 50px;
        }
        .inputFieldContainer {
            font-size: 10px;
            color: #ffffff;
            float: left;
            width: 100%;
            padding-right: 20px;
            margin-bottom: 20px;
        }
        .inputHeadding {
            font-size: 14px;
            font-weight: 600;
            color: white;
        }
        .itemName {
            color: white;
            font-size: 18px;
            font-weight: 600;
        }
        .itemPrice {
            color: white;
            font-size: 18px;
            font-weight: 600;
        }
        .itemContainer {
            display: flex;
            justify-content: space-between;
            margin-top: -15px;
        }
    </style>
    <title>Title</title>

</head>
<body>
<%@include file="header.html"%>

<section
        class="viewportCheckout"
        style="
        width: 100%;
        display: flex;
        background-color: #e7e7e7;
        padding: 50px 0px 50px 0px;
      "
>
    <div
            style="
          width: 80%;
          background-color: #203c55;
          margin: auto;
          box-shadow: 10px 10px 0px #ffffff;
          padding: 40px;
        "
    >
        <p style="font-size: 40px; color: #ffffff; font-weight: 600">
            Checkout
        </p>
        <p
                style="
            font-size: 20px;
            color: #ffffff;
            font-weight: normal;
            margin-top: -20px;
          "
        >
            Enter your checkout details to purchase
        </p>
        <form>
            <div style="display: flex">
                <div class="inputFieldContainer">
                    <p class="inputHeadding">First Name:</p>
                    <input
                            type="text"
                            class="inputField"
                            placeholder="  Enter your first name"
                    />
                </div>
                <div class="inputFieldContainer">
                    <p class="inputHeadding">Last Name:</p>
                    <input
                            type="text"
                            class="inputField"
                            placeholder="  Enter your last name"
                    />
                </div>
            </div>
            <div style="display: flex">
                <div class="inputFieldContainer">
                    <p class="inputHeadding">Contact number:</p>
                    <input
                            type="text"
                            class="inputField"
                            placeholder="  Enter your contact number"
                    />
                </div>
                <div class="inputFieldContainer">
                    <p class="inputHeadding">Email:</p>
                    <input
                            type="email"
                            class="inputField"
                            placeholder="  Enter your email address"
                    />
                </div>
            </div>
            <div style="display: flex">
                <div class="inputFieldContainer">
                    <p class="inputHeadding">Address:</p>
                    <input
                            type="text"
                            class="inputField"
                            style="width: 100%"
                            placeholder="  Enter your shipping address"
                    />
                </div>
            </div>

            <p
                    style="
              font-size: 30px;
              color: #ffffff;
              font-weight: 600;
              margin-top: 20px;
            "
            >
                Note:
            </p>
            <p
                    style="
              font-size: 18px;
              color: #ffffff;
              font-weight: normal;
              margin-top: -15px;
            "
            >
                Payment method:Cash On Delivery. <br />
                Order will delivered within 2 to 4 business days across island wide.
            </p>

            <div
                    style="
              display: flex;
              height: 100%;
              width: 100%;
              align-items: center;
            "
            >
                <input type="checkbox" value="checkbox" />
                <p
                        style="
                font-size: 14px;
                margin-left: 20px;
                margin-top: 15px;
                font-weight: bold;
                color: white;
              "
                >
                    Accept our terms and conditions
                </p>
            </div>

            <div
                    style="
              width: 100%;
              height: 5px;
              margin-bottom: 30px;
              background-color: #ffffff;
            "
            ></div>

            <div>
                <div class="itemContainer">
                    <p class="itemName">item 1</p>
                    <p class="itemPrice">LKR 2490</p>
                </div>
                <div class="itemContainer">
                    <p class="itemName">item 2</p>
                    <p class="itemPrice">LKR 2490</p>
                </div>
                <div class="itemContainer">
                    <p class="itemName">item 3</p>
                    <p class="itemPrice">LKR 2490</p>
                </div>
            </div>

            <div style="display: flex; justify-content: space-between">
                <p
                        style="
                font-size: 18px;
                color: #ffffff;
                font-weight: 600;
                margin-top: 20px;
              "
                >
                    Subtotal
                </p>
                <p
                        style="
                font-size: 18px;
                color: #ffffff;
                font-weight: 600;
                margin-top: 20px;
              "
                >
                    LKR 5000
                </p>
            </div>
            <div
                    style="
              display: flex;
              justify-content: space-between;
              margin-top: -35px;
            "
            >
                <p
                        style="
                font-size: 18px;
                color: #ffffff;
                font-weight: 600;
                margin-top: 20px;
              "
                >
                    Tax
                </p>
                <p
                        style="
                font-size: 18px;
                color: #ffffff;
                font-weight: 600;
                margin-top: 20px;
              "
                >
                    -LKR 500
                </p>
            </div>

            <div
                    style="
              width: 100%;
              height: 5px;
              margin-bottom: 30px;
              background-color: #ffffff;
            "
            ></div>

            <div
                    style="
              display: flex;
              justify-content: space-between;
              margin-top: -35px;
            "
            >
                <p
                        style="
                font-size: 24px;
                color: #ffffff;
                font-weight: 600;
                margin-top: 20px;
              "
                >
                    Total
                </p>
                <p
                        style="
                font-size: 24px;
                color: #ffffff;
                font-weight: 600;
                margin-top: 20px;
              "
                >
                    LKR 4500
                </p>
            </div>

            <div style="width: 80%; padding: 20px; margin-left: auto; margin-right: auto; margin-top: 50px; background-color: #e7e7e7">
                <a href="#"
                ><div
                        class="buttonCommon"
                        style="
                  background-color: #203c55;
                  width: 80%;
                  color: white;
                  font-size: 22px;
                  font-weight: bold;
                  padding-left: 15px;
                  padding-right: 15px;
                  box-shadow: 6px 6px 0px #ffffff;
                  transition: 0.4s ease-in-out;
                  margin-left: auto;
                  margin-right: auto;
                  margin-top: 20px;
                "
                >
                    <center>
                        <p
                                class="pe-none"
                                style="padding-top: 5px; padding-bottom: 5px"
                        >
                            Checkout
                        </p>
                    </center>
                </div></a
                >
            </div>
        </form>
    </div>
</section>


<%@include file="footer.html"%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
