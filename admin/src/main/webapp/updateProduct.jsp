<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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

        .addBtn:hover {
            box-shadow: 4px 4px 0px #cccccc !important;
            transform: scale(0.98);
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
<%@include file="headerAdmin2.html" %>

<section
        style="width: 100%; background-color: #e7e7e7; padding: 20px 0px 40px 0px"
>
    <div>
        <p
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
                style="
            font-size: 40px;
            font-weight: 600;
            color: #203c55;
            text-align: center;
            letter-spacing: 1px;
          "
        >
            Update Product
        </p>
    </div>
    <div style="margin-top: 20px">
        <div
                style="
            width: 90%;
            background-color: #203c55;
            box-shadow: 10px 10px 0px #ffffff;
            padding-bottom: 40px;
            margin: auto;
          "
        >
            <div
                    style="width: 100%; display: flex; justify-content: space-evenly"
            >
                <div style="width: 80%; margin-top: 20px">
                    <p style="color: #ffffff; font-weight: 600; margin-bottom: 25px;">
                        Updated product name:
                    </p>
                    <input
                            style="
                  width: 100%;
                  height: 10px;
                  padding: 20px;
                  border: none;
                  margin-top: -20px;
                "
                            type="text"
                            name="updatedName"
                            placeholder="Enter product name"
                            required

                    />
                    <p style="color: #ffffff; font-weight: 600; margin-top: 10px;  margin-bottom: 25px;">
                        Updated product description:
                    </p>
                    <input
                            style="
                  width: 100%;
                  height: 10px;
                  padding: 20px;
                  border: none;

                  margin-top: -20px;
                "
                            type="text"
                            name="updatedDescription"
                            placeholder="Enter product name"
                            id=""
                            required
                    />

                    <div style="width: 100%; margin-top: 20px;">
                        <p style="color: #ffffff; font-weight: 600">Product price :</p>
                        <div class="input-group" style="margin-top: -10px">
                  <span
                          class="input-group-text"
                          style="border-radius: 0 !important"
                  >LKR</span
                  >
                            <input
                                    style="border-radius: 0 !important"
                                    type="text"
                                    class="form-control"
                                    aria-label="Amount (to the nearest dollar)"
                                    placeholder="Enter price"
                                    required
                            />
                            <span
                                    class="input-group-text"
                                    style="border-radius: 0 !important"
                            >.00</span
                            >
                        </div>
                    </div>
                </div>
            </div>
            <div
                    class="saveBtn"
                    style="
              width: 50%;
              margin: auto;
              background-color: #ffffff;
              box-shadow: 8px 8px 0px #e7e7e7;
              transition: 0.4s ease-in-out;
              margin-top: 40px;
            "
            >

                    <button style="width: 100%; border: none; margin: auto" onclick="editItemMen()">
                    <p
                            style="
                  color: #203c55;
                  text-align: center;
                  font-size: 22px;
                  font-weight: 600;
                  padding: 5px 60px 5px 60px;
                "
                    >
                        Save changes
                    </p>
                    </button>
            </div>
        </div>
    </div>
</section>

<%@include file="footerAdmin.html" %>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"
></script>
<script>
    function editItemMen() {

        var urlParams = new URLSearchParams(window.location.search);
        var productId = urlParams.get('productId');

        var updatedName = document.getElementsByName('updatedName')[0].value;
        var updatedDescription = document.getElementsByName('updatedDescription')[0].value;
        var updatedPrice = document.querySelector('.form-control').value;

        var requestBody = JSON.stringify({
            name: updatedName,
            description: updatedDescription,
            price: updatedPrice
        });

        fetch('http://localhost:8081/admin/product/update?productId=' + productId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: requestBody
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                alert('Product updated successfully!');
            })
            .catch(error => {
                alert('Error updating product: ' + error.message);
            });
    }

</script>
</body>
</html>
