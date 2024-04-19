<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

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
        .productCard:hover{
             box-shadow: 3px 3px 0px #ffffff !important;
             transform: scale(0.99) !important;
         }
    </style>
</head>
<body>
<%@include file="header.html"%>

<section style="background-color: #e7e7e7">
    <div style="width: 100%; display: flex">
        <div
                style="
            background-color: #203c55;
            width: 20%;
            height: 90vh;
            padding: 20px;
          "
        >
            <p style="color: white; font-size: 30px; font-weight: bold">
                Men's Wear
            </p>
            <p style="color: white; font-size: 16px; font-weight: normal">
                Availability:
            </p>
            <form action="#" style="margin-top: -10px; padding-left: 20px">
                <div>
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-outlined"
                    >In stock</label
                    >
                </div>
                <div style="margin-top: 5px">
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-2-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-2-outlined"
                    >Out of stock</label
                    >
                </div>
            </form>

            <p
                    style="
              color: white;
              font-size: 16px;
              font-weight: normal;
              margin-top: 20px;
            "
            >
                Price:
            </p>
            <form action="#" style="margin-top: -10px; padding-left: 20px">
                <div style="margin-top: 5px">
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-a-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-a-outlined"
                    >LKR 1000 or lower</label
                    >
                </div>
                <div style="margin-top: 5px">
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-b-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-b-outlined"
                    >LKR 1000 - 2000</label
                    >
                </div>
                <div style="margin-top: 5px">
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-c-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-c-outlined"
                    >LKR 2000 - 3000</label
                    >
                </div>
                <div style="margin-top: 5px">
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-d-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-d-outlined"
                    >LKR 3000 - 5000</label
                    >
                </div>
                <div style="margin-top: 5px">
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-e-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-e-outlined"
                    >LKR 5000 or higher</label
                    >
                </div>
            </form>

            <p
                    style="
              color: white;
              font-size: 16px;
              font-weight: normal;
              margin-top: 20px;
            "
            >
                Size:
            </p>
            <form action="#" style="margin-top: -10px; padding-left: 20px">
                <div style="margin-top: 5px">
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-small-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-small-outlined"
                    >Small</label
                    >
                </div>
                <div style="margin-top: 5px">
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-medium-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-medium-outlined"
                    >Medium</label
                    >
                </div>
                <div style="margin-top: 5px">
                    <input
                            type="checkbox"
                            class="btn-check"
                            id="btn-check-large-outlined"
                            autocomplete="off"
                    />
                    <label
                            style="font-size: 14px"
                            class="btn btn-outline-secondary"
                            for="btn-check-large-outlined"
                    >Large</label
                    >
                </div>
            </form>
        </div>
        <div
                style="
            background-color: #e7e7e7;
            width: 80%;
            height: 90vh;
            padding: 40px;
          "
        >
            <div
                    style="
              background-color: #d9d9d9;
              width: 300px;
              height: 500px;
              box-shadow: 10px 10px 0px #ffffff;
              padding: 20px;
              transition: 0.4s ease-in-out;
            "
                    class="productCard"
            >
                <div
                        style="
                background-color: #203c55;
                width: 100%;
                height: 70%;
                margin-left: auto;
                margin-right: auto;
                overflow: hidden;
              "
                >
                    <img src="assets/tmp1.jpg" style="width: 100%" alt="" />
                </div>
                <p style="color: #203c55; font-size: 20px; font-weight: bold; margin-top: 30px;">Product Name</p>
                <p style="color: #203c55; font-size: 18px; font-weight: normal; margin-top: 10px;">Product code</p>
                <p style="color: #203c55; font-size: 18px; font-weight: normal; margin-top: -15px;">Product price</p>
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

