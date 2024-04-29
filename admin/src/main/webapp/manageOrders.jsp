<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
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
            list-style: none;
        }
        a {
            text-decoration: none !important;
        }
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
        .saveBtn:hover {
            box-shadow: 4px 4px 0px #ffffff !important;
            transform: scale(0.98);
        }
    </style>
</head>
<body>
<%@include file="headerAdmin.html"%>

<section
        style="width: 100%; background-color: #e7e7e7; padding: 20px 0px 40px 0px"
>
    <div>
        <p
                class="pe-none"
                style="
            font-size: 40px;
            font-weight: 600;
            color: #203c55;
            text-align: center;
            letter-spacing: 1px;
          "
        >
            ADMIN PANEL
        </p>
        <p
                class="pe-none"
                style="
            font-size: 20px;
            font-weight: 600;
            color: #203c55;
            text-align: center;
            letter-spacing: 7px;
            margin-top: -20px;
            padding-left: 5px;
          "
        >
            BLOOM CLOTHING
        </p>
        <p
                class="pe-none"
                style="
            font-size: 40px;
            font-weight: 600;
            color: #203c55;
            text-align: center;
            letter-spacing: 1px;
          "
        >
            Manage Orders
        </p>
    </div>

    <div
            style="
          width: 90%;
          background-color: #203c55;
          box-shadow: 10px 10px 0px #ffffff;
          padding-bottom: 40px;
          padding-top: 20px;
          margin: auto;
          margin-top: 20px;
        "
    >
        <div style="overflow-x: auto; padding-bottom: 40px;">
            <table class="pe-none">
                <tr>
                    <th style="min-width: 100px;">Order Id</th>
                    <th style="min-width: 200px;">User Name</th>
                    <th style="min-width: 200px;">User email</th>
                    <th style="min-width: 200px;">Product name</th>
                    <th >Quantity</th>
                    <th style="min-width: 100px;">Size</th>
                    <th style="min-width: 200px;">Ordered Date</th>
                    <th style="min-width: 200px;">Delivary Date</th>
                    <th style="min-width: 200px;">Set date</th>
                </tr>
                <!-- loop this table row -->
                <tr>
                    <td>1</td>
                    <td>Navindu Dissanayake</td>
                    <td>navindu@gmail.com</td>
                    <td>T-shirt 123</td>
                    <td>5</td>
                    <td>Small</td>
                    <td>2024/08/26</td>
                    <td>N/A</td>
                    <td>
                        <div style="width: 100%;">
                            <input type="text" name="date" id="" placeholder="Enter date" style="height: 40px; width: 100%; border: none; font-size: 18px; font-weight: 500; color: #203c55; padding: 0px 10px 0px 10px;">
                            <button type="submit" style="width: 100%; border: none; background-color: #e7e7e7; color: #203c55; padding: 8px; margin-top: 5px; font-weight: 600;">Set Date</button>
                        </div>
                    </td>
                </tr>

            </table>
        </div>

    </div>
</section>

<%@include file="footerAdmin.html"%>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"
></script>
</body>
</html>
