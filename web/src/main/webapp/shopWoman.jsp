<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="nsbm.dea.web.dao.ProductDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="nsbm.dea.web.models.Product" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Optional" %>
<%@ page import="nsbm.dea.web.models.Category" %>
<%@ page import="nsbm.dea.web.models.Color" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous" />

    <style>
        @import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");

        * { font-family: "Poppins", sans-serif !important; }
        a{text-decoration: none}
        li { list-style: none !important; }
        .productCard:hover { box-shadow: 3px 3px 0px #ffffff !important; transform: scale(0.99) !important; }
        .btn-check-label { font-size: 14px; }
        .sidebar { background-color: #203c55; width: 20%; height: 90vh; padding: 20px; color: white; }
        .sidebar p { font-weight: bold; }
        .sidebar form { margin-top: -10px; padding-left: 20px; }
        .productCard { background-color: #d9d9d9; width: 300px; height: 500px; box-shadow: 10px 10px 0px #ffffff; padding: 20px; transition: 0.4s ease-in-out; }
        .productCard img { width: 100%; }
        .productInfo { background-color: #203c55; width: 100%; height: 70%; margin: auto; overflow: hidden; }
    </style>
</head>
<body>
<%@include file="header.html"%>

<section style="background-color: #e7e7e7">
    <div style="width: 100%;">
<%--        <div class="sidebar">--%>
<%--            <p style="font-size: 30px;">Women Wear</p>--%>
<%--            <p>Search:</p>--%>
<%--            <form id="searchForm" action="search.jsp" method="get">--%>
<%--                <input type="text" class="form-control" id="searchTerm" name="searchTerm" placeholder="Search products...">--%>
<%--                <button type="submit" class="btn btn-primary">Search</button>--%>
<%--            </form>--%>
<%--        </div>--%>

            <div style="background-color: #d9d9d9; box-shadow: 10px 10px 0px #ffffff; width: 80%; margin: auto; margin-bottom: 15px; padding: 20px 40px 20px 40px;">
                <div style="background-color: #203c55; width: 100%; margin-bottom:10px; height: fit-content; display: flex; align-items: center; padding: 5px 0px 5px 0px;"><p style="font-size: 24px; margin: auto; width: 100%; height: 100%; text-align: center; font-weight: bold; color: #ffffff;">SHOP WOMEN</p></div>
                <form id="searchForm" action="search.jsp" method="get" style="display: flex">
                    <input type="text" class="form-control" id="searchTerm" name="searchTerm" placeholder="Search products..." style="margin-right: 20px; border-radius: 0px;">
                    <button type="submit" class="btn btn-primary" style="background-color: #203c55 !important; border-radius: 0px !important; border: none !important;">Search</button>
                </form>
            </div>

            <div id="productContainer" style="background-color: #e7e7e7; width: 80%; margin: auto; height: fit-content; padding: 40px; display: flex; flex-wrap: wrap; justify-content: space-around;">
                <%
                    ProductDAO productDao = new ProductDAO();
                    List<Product> maleProducts = productDao.getProductsByCollection("women");
                    for (Product product : maleProducts) {
                %>
                <div class="productCard" style="margin-bottom: 20px; cursor: pointer;" onclick="redirectToProductDetails('<%= product.getId() %>')">
                    <div class="productInfo">
                        <img src="<%= product.getPhotoUrls()[0] %>" alt="" style="width: 100%; height: auto;" />
                    </div>
                    <p style="color: #203c55; font-size: 20px; font-weight: bold; margin-top: 30px;"><%= product.getName() %></p>
                    <p style="color: #203c55; font-size: 18px; font-weight: normal; margin-top: -15px;">Rs: <%= product.getPrice() %></p>
                </div>

                <script>
                    function redirectToProductDetails(productId) {
                        window.location.href = 'productDetails.jsp?id=' + productId;
                    }
                </script>

                <% } %>
            </div>


    </div>
</section>

<%@include file="footer.html"%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function searchProducts() {
        var searchTerm = $('#searchTerm').val();
        $.ajax({
            url: "http://localhost:8080/web/search.jsp",
            type: 'GET',
            data: {searchTerm: searchTerm},
            success: function (response) {
                var products = response;
                var productContainer = $('#productContainer');
                productContainer.empty();
                products.forEach(function (product) {
                    var productCard = $('<div class="productCard" style="margin-bottom: 20px;" onclick="redirectToProductDetails(' + product.id + ')"></div>');
                    productCard.append('<div class="productInfo"><img src="' + product.photoUrls[0] + '" alt="" style="width: 100%; height: auto;" /></div>');
                    productCard.append('<p style="color: #203c55; font-size: 20px; font-weight: bold; margin-top: 30px;">' + product.name + '</p>');
                    productCard.append('<p style="color: #203c55; font-size: 18px; font-weight: normal; margin-top: -15px;">Rs ' + product.price + '</p>');
                    productContainer.append(productCard);
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("AJAX request failed:", textStatus, errorThrown);
            }
        });
    }
    function redirectToProductDetails(productId) {
        window.location.href = 'productDetails.jsp?id=' + productId;
    }
    $('#searchForm').on('submit', function (e) {
        e.preventDefault();
        searchProducts();
    });
</script>

</body>
</html>
