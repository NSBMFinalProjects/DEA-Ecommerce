<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="nsbm.dea.web.dao.ProductDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="nsbm.dea.web.models.Product" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Optional" %>
<%@ page import="nsbm.dea.web.models.Category" %>
<%@ page import="nsbm.dea.web.models.Color" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="nsbm.dea.web.config.Env" %>

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
    <%@include file="css/productDetails/style.css"%>
  </style>
</head>
<body>
<%@include file="header.html"%>
<%
  Gson gson = new Gson();
  Product product = ((Product) pageContext.getServletContext().getAttribute("product"));
%>

<p id="product" hidden aria-hidden="true"
>
  <%= gson.toJson(product)%>
</p>

<section
        style="
        padding: 40px;
        height: 90vh;
        background-color: #e7e7e7;
        display: flex;
        align-items: center;
      "
>
  <div class="containerBase">
    <div class="row" style="height: 100%; overflow: hidden">
      <div class="column" style="margin: auto">
        <div
                id="carouselExampleIndicators"
                class="carousel slide"
                data-bs-ride="carousel"
        >
          <div class="carousel-indicators">
            <button
                    type="button"
                    data-bs-target="#carouselExampleIndicators"
                    data-bs-slide-to="0"
                    class="active"
                    aria-current="true"
                    aria-label="Slide 1"
            ></button>
            <button
                    type="button"
                    data-bs-target="#carouselExampleIndicators"
                    data-bs-slide-to="1"
                    aria-label="Slide 2"
            ></button>
            <button
                    type="button"
                    data-bs-target="#carouselExampleIndicators"
                    data-bs-slide-to="2"
                    aria-label="Slide 3"
            ></button>
          </div>
          <div
                  class="carousel-inner"
                  style="margin-left: 20px !important; padding: 15px 0px 15px 0px"
          >
            <div class="carousel-item active">
              <img
                      src=<%= String.format("%s/assets/Products/Men/m1/mp11.jpg", Env.getURL())%>
                      style="max-width: 320px !important; margin: auto"
                      class="d-block w-100"
                      alt="..."
              />
            </div>
            <div class="carousel-item">
              <img
                      src=<%= String.format("%s/assets/Products/Men/m1/mp12.jpg", Env.getURL())%>
                      style="max-width: 320px !important; margin: auto"
                      class="d-block w-100"
                      alt="..."
              />
            </div>
            <div class="carousel-item">
              <img
                      src=<%= String.format("%s/assets/Products/Men/m1/mp13.jpg", Env.getURL())%>
                      style="max-width: 320px !important; margin: auto"
                      class="d-block w-100"
                      alt="..."
              />
            </div>
          </div>
          <button
                  class="carousel-control-prev"
                  type="button"
                  data-bs-target="#carouselExampleIndicators"
                  data-bs-slide="prev"
          >
                <span
                        class="carousel-control-prev-icon"
                        aria-hidden="true"
                ></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
                  class="carousel-control-next"
                  type="button"
                  data-bs-target="#carouselExampleIndicators"
                  data-bs-slide="next"
          >
                <span
                        class="carousel-control-next-icon"
                        aria-hidden="true"
                ></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>
      <div class="column">
        <form class="addToCartForm">
          <div style="margin-top: 15px">
            <p
                    style="
                    color: #203c55;
                    font-size: 26px;
                    width: 80%;
                    font-weight: bold;
                    margin: auto;
                  "
            >
              <input
                      type="text"
                      value="<%=product.getName()%>"
                      name="dress-name"
                      disabled
                      style="
                      color: #203c55;
                      font-size: 26px;
                      width: 80%;
                      font-weight: bold;
                      border: none;
                    "
              />
            </p>
            <p
                    style="
                    color: #203c55;
                    font-size: 20px;
                    width: 80%;
                    margin: auto;
                  "
            >
              <input
                      type="text"
                      name="dress-price"
                      value="Rs: <%=product.getPrice()%>/-"
                      style="
                        color: #203c55;
                        font-size: 20px;
                        width: 80%;
                        border: none;
                      "
              />
            </p>
            <div
                    style="
                    width: 80%;
                    margin: auto;
                    margin-top: 10px;
                    background-color: #203c55;
                    height: 2px;
                  "
            ></div>
            <div
                    style="
                    display: flex;
                    width: 80%;
                    margin: auto;
                    margin-top: 30px;
                  "
            >
              <p style="color: #203c55; font-size: 16px; font-weight: 600">
                Availability :
              </p>
              <p style="color: #203c55; font-size: 16px; margin-left: 20px">
                In stock
              </p>
            </div>
            <div
                    style="
                    display: flex;
                    width: 80%;
                    margin: auto;
                    margin-top: -15px;
                  "
            >
              <p style="color: #203c55; font-size: 16px; font-weight: 600">
                Code :
              </p>
              <p style="color: #203c55; font-size: 16px; margin-left: 20px">
                <%=product.getCreatedBy()%>
              </p>
            </div>
            <div style="width: 80%; margin: auto">
              <p
                      style="
                      color: #203c55;
                      font-size: 16px;
                      font-weight: 600;
                      margin-bottom: 5px;
                    "
              >
                Color :
              </p>
              <div id="color-display" class="custom-radios" style="padding-left: 40px">
                  <% for (Color color : product.getCategories()[0].getColors()) { %>
                  <div
                  >
                      <input
                              type="radio"
                              id=<%= color.getSlug()%>
                              name="colors"
                              value=<%= color.getSlug()%>
                              <%= product.getCategories()[0].getColors()[0].getSlug().equals(color.getSlug()) ? "checked" : ""%>
                      />
                      <label for=<%= color.getSlug()%>
                    >
                        <span
                                style="background-color: <%= color.getHex()%>"
                        >
                          <img
                                  src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
                                  alt="Checked Icon"

                          />
                        </span>
                      </label>
                  </div>
                  <% }; %>
              </div>
            </div>
            <div style="display: flex; width: 80%; margin: auto">
              <div style="width: 80%; margin: auto">
                <p
                        style="
                        color: #203c55;
                        font-size: 16px;
                        font-weight: 600;
                        margin-bottom: 5px;
                      "
                >
                  Size :
                </p>
                <div class="custom-radios" style="padding-left: 40px; display: block">
                    <% for (Category category : product.getCategories()) { %>
                      <input
                            type="radio"
                            class="btn-check"
                            name="sizes"
                            id=<%= category.getSlug()%>
                            autocomplete="off"
                            value=<%= category.getSlug()%>
                            <%= product.getCategories()[0].getSlug().equals(category.getSlug()) ? "checked" : "" %> />
                  <label
                          class="btn btn-outline-secondary"
                          style="border-radius: 0px; width: 100px !important"
                          for=<%= category.getSlug() %>
                  ><%= category.getName()%></label
                  >
                    <% }; %>
                </div>
              </div>
              <div style="width: 80%; margin: auto">
                <p
                        style="
                        color: #203c55;
                        font-size: 16px;
                        font-weight: 600;
                        margin-bottom: 5px;
                      "
                >
                  Quantity :
                </p>
                <div class="custom-radios" style="padding-left: 40px">
                  <div class="wrapper">
                        <span
                                id="minus"
                                class="minus"
                                style="padding-left: 10px; padding-right: 10px"
                        >-</span
                        >
                    <span
                            id="qty"
                            class="qty"
                            style="padding-left: 20px; padding-right: 20px"
                    >01</span
                    >
                    <span
                            id="plus"
                            class="plus"
                            style="padding-left: 10px; padding-right: 10px"
                    >+</span
                    >
                  </div>
                </div>
              </div>
            </div>
            <div
                    style="
                    margin-top: 120px;
                    width: 100%;
                    display: flex;
                    justify-content: center;
                  "
            >
              <button
                      type="submit"
                      style="
                      width: 80%;
                      height: fit-content;
                      background-color: transparent;
                      border: none;
                      margin-left: auto;
                      margin-right: auto;
                    "
              >
                <div
                        class="buttonCommon"
                        style="
                        background-color: #203c55;
                        width: 100%;
                        color: #ffffff;
                        font-size: 22px;
                        font-weight: 500;
                        padding-left: 15px;
                        padding-right: 15px;
                        box-shadow: 6px 6px 0px #c3c3c3;
                        transition: 0.4s ease-in-out;
                      "
                >
                  <center>
                    <p
                            class="pe-none"
                            style="padding-top: 5px; padding-bottom: 5px"
                    >
                      ADD TO CART
                    </p>
                  </center>
                </div>
              </button>
            </div>
          </div>
        </form>
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
<script>
  <%@include file="js/productDetails/index.js"%>
</script>
</body>
</html>
