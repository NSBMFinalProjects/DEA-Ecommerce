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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
            Edit Products
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
            <style>
                .productLink {
                    color: #203c55 !important;
                    border-radius: 0 !important;
                    font-weight: 600;
                }
            </style>

            <div class="tab-content" id="tab-content">
                <div
                        class="tab-pane fade show active"
                        id="fill-tabpanel-0"
                        role="tabpanel"
                        aria-labelledby="fill-tab-0"
                        style="color: #ffffff"
                >

                    <div
                            style="
                  width: 90%;
                  margin: auto;
                  margin-top: 50px;
                  margin-bottom: -10px;
                "
                    >
                        <p
                                style="
                    font-size: 30px;
                    font-weight: 600;
                    color: #ffffff;
                    text-align: center;
                    letter-spacing: 1px;
                  "
                        >
                            Current products
                        </p>
                    </div>
                    <div
                            style="
                  width: 90%;
                  height: 3px;
                  background-color: #ffffff;
                  margin: auto;
                  margin-bottom: 20px;
                "
                    ></div>

                    <div class="tagItem" style="display: flex; width: 90%; margin: auto">
                        <div style="overflow-x: auto; padding-bottom: 40px">
                            <table id="productTable">
                                <tr>
                                    <th style="min-width: 100px">Product Img 1</th>
                                    <th style="min-width: 100px">Product Img 2</th>
                                    <th style="min-width: 100px">Product Img 3</th>
                                    <th style="min-width: 200px">Product name</th>
                                    <th style="min-width: 200px">Description</th>
                                    <th style="min-width: 100px">Price (LKR)</th>
                                    <th style="min-width: 100px">Created AT</th>
                                    <th style="min-width: 150px">Actions</th>
                                </tr>

                            </table>
                        </div>
                    </div>


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
    <%@include file="js/manageProducts/index.js" %>
</script>
<script>
    $(document).ready(function() {
        $.ajax({
            url: 'http://localhost:8081/admin/products/get-all?page=1&limit=10',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                var products = JSON.parse(data.data);
                if(products>0){
                    alert("ists is in ");
                }
                var table = $('#productTable');

                products.forEach(function(product) {
                    var row = $('<tr></tr>');

                    var img1 = $('<td></td>').html('<img src="' + product.photoUrls[0] + '" width="90" alt="" />');
                    var img2 = $('<td></td>').html('<img src="' + product.photoUrls[1] + '" width="90" alt="" />');
                    var img3 = $('<td></td>').html('<img src="' + product.photoUrls[2] + '" width="90" alt="" />');
                    var name = $('<td></td>').text(product.name);
                    var description = $('<td></td>').text(product.description);
                    var price = $('<td></td>').text(product.price);
                    var createdAt=$('<td></td>').text(product.created);

                    let productId = product.id;


                    var actions = $('<td></td>');
                    actions.append('<div style="display: flex; align-items: center;">');
                    actions.append('<img onclick="deleteItemMen(\'' + productId + '\');" style="cursor: pointer; margin: 8px" src="assets/delete2.svg" alt="" />');
                    actions.append('<img onclick="editItemMen(\'' + productId + '\');" style="cursor: pointer; margin: 8px" src="assets/edit.svg" alt="" />');
                    actions.append('</div>');

                    row.append(img1, img2, img3, name, description, price,createdAt, actions);
                    table.append(row);
                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error fetching products: " + textStatus + ", " + errorThrown);
            }
        });
    });

    function deleteItemMen(productId) {

        var url = "http://localhost:8081/admin/products/delete?id=" + productId;
        console.log(url);
        fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                alert('Product deleted successfully!');
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                alert('Error deleting product: ' + error.message);
            });
    }

    function editItemMen(productId) {
        window.location.href = "updateProduct.jsp?productId=" + productId;
    }
</script>
</body>
</html>
