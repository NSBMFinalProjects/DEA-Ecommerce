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
</head>

<body>
<%@include file="headerAdmin.html" %>

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
            <ul class="nav nav-fill nav-tabs" role="tablist">
                <li
                        class="nav-item"
                        role="presentation"
                        style="border-radius: 0; background-color: #cccccc; margin: 10px"
                >
                    <a
                            class="nav-link productLink active"
                            id="fill-tab-0"
                            data-bs-toggle="tab"
                            href="#fill-tabpanel-0"
                            role="tab"
                            aria-controls="fill-tabpanel-0"
                            aria-selected="true"
                    >
                        MEN'S
                    </a>
                </li>
                <li
                        class="nav-item"
                        role="presentation"
                        style="border-radius: 0; background-color: #cccccc; margin: 10px"
                >
                    <a
                            class="nav-link productLink"
                            id="fill-tab-1"
                            data-bs-toggle="tab"
                            href="#fill-tabpanel-1"
                            role="tab"
                            aria-controls="fill-tabpanel-1"
                            aria-selected="false"
                    >
                        WOMAN'S
                    </a>
                </li>
            </ul>
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
                    <!-- loop this divition (class : tagItem) -->
                    <div
                            class="tagItem"
                            style="display: flex; width: 90%; margin: auto"
                    >
                        <div style="overflow-x: auto; padding-bottom: 40px">
                            <table>
                                <tr>
                                    <th style="min-width: 100px">Product Img 1</th>
                                    <th style="min-width: 100px">Product Img 2</th>
                                    <th style="min-width: 100px">Product Img 3</th>
                                    <th style="min-width: 200px">Product name</th>
                                    <th style="min-width: 200px">Description</th>
                                    <th style="min-width: 100px">Price (LKR)</th>
                                    <th style="min-width: 100px">Size</th>
                                    <th style="min-width: 100px">Quantity</th>
                                    <th style="min-width: 100px">Tags</th>
                                    <th style="min-width: 100px">Sets</th>
                                    <th style="min-width: 150px">Actions</th>
                                </tr>
                                <!-- loop this table row -->
                                <tr>
                                    <td><img src="assets/mp16.jpg" width="90" alt="" /></td>
                                    <td><img src="assets/mp16.jpg" width="90" alt="" /></td>
                                    <td><img src="assets/mp16.jpg" width="90" alt="" /></td>
                                    <td>T-shirt 123</td>
                                    <td>T-shirt 123 description testing 12345</td>
                                    <td>1590</td>
                                    <td>Small</td>
                                    <td>100</td>
                                    <td>Tag1</td>
                                    <td>Set1</td>
                                    <td>
                                        <div>
                                            <img
                                                    onclick="deleteItemMen();"
                                                    style="cursor: pointer; margin: 8px"
                                                    src="assets/delete2.svg"
                                                    alt=""
                                            />
                                            <img
                                                    onclick="editItemMen();"
                                                    style="cursor: pointer; margin: 8px"
                                                    src="assets/edit.svg"
                                                    alt=""
                                            />
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <script>
                        function deleteItemMen() {
                            window.location.href = "#";
                        }
                        function editItemMen() {
                            window.location.href = "updateProduct.jsp";
                        }
                    </script>
                </div>

                <div
                        class="tab-pane fade"
                        id="fill-tabpanel-1"
                        role="tabpanel"
                        aria-labelledby="fill-tab-1"
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
                    <!-- loop this divition (class : tagItem) -->
                    <div
                            class="tagItem"
                            style="display: flex; width: 90%; margin: auto"
                    >
                        <div style="overflow-x: auto; padding-bottom: 40px">
                            <table>
                                <tr>
                                    <th style="min-width: 100px">Product Img 1</th>
                                    <th style="min-width: 100px">Product Img 2</th>
                                    <th style="min-width: 100px">Product Img 3</th>
                                    <th style="min-width: 200px">Product name</th>
                                    <th style="min-width: 200px">Description</th>
                                    <th style="min-width: 100px">Price (LKR)</th>
                                    <th style="min-width: 100px">Size</th>
                                    <th style="min-width: 100px">Quantity</th>
                                    <th style="min-width: 100px">Tags</th>
                                    <th style="min-width: 100px">Sets</th>
                                    <th style="min-width: 150px">Actions</th>
                                </tr>
                                <!-- loop this table row -->
                                <tr>
                                    <td><img src="assets/wp26.jpg" width="90" alt="" /></td>
                                    <td><img src="assets/wp26.jpg" width="90" alt="" /></td>
                                    <td><img src="assets/wp26.jpg" width="90" alt="" /></td>
                                    <td>T-shirt 123</td>
                                    <td>T-shirt 123 description testing 12345</td>
                                    <td>1590</td>
                                    <td>Small</td>
                                    <td>100</td>
                                    <td>Tag1</td>
                                    <td>Set1</td>
                                    <td>
                                        <div>
                                            <img
                                                    onclick="deleteItemWoman();"
                                                    style="cursor: pointer; margin: 8px"
                                                    src="assets/delete2.svg"
                                                    alt=""
                                            />
                                            <img
                                                    onclick="editItemWoman();"
                                                    style="cursor: pointer; margin: 8px"
                                                    src="assets/edit.svg"
                                                    alt=""
                                            />
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <script>
                        function deleteItemWoman() {
                            window.location.href = "#";
                        }
                        function editItemWoman() {
                            window.location.href = "updateProduct.jsp";
                        }
                    </script>

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

</body>
</html>
