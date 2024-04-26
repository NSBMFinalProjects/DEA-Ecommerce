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
      list-style: none !important;
    }
    a {
      text-decoration: none;
    }
    .containerBase {
      background-color: #ffffff;
      box-shadow: 10px 10px 0px #203c55;
      width: 90%;
      height: 95%;
      margin: auto;
    }
    .column {
      float: left;
      width: 50%;
    }
    .custom-radios div {
      display: inline-block;
    }
    .custom-radios input[type="radio"] {
      display: none;
    }

    .custom-radios input[type="radio"] + label span {
      display: inline-block;
      width: 30px;
      height: 30px;
      margin: -1px 4px 0 0;
      vertical-align: middle;
      cursor: pointer;
      border-radius: 50%;
      border: 2px solid #fff;
      box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.33);
      background-repeat: no-repeat;
      background-position: center;
      text-align: center;
      line-height: 25px;
    }
    .custom-radios input[type="radio"] + label span img {
      opacity: 0;
      transition: all 0.3s ease;
    }
    .custom-radios input[type="radio"]#color-1 + label span {
      background-color: #000000;
    }
    .custom-radios input[type="radio"]#color-2 + label span {
      background-color: #f1c40f;
    }
    .custom-radios input[type="radio"]#color-3 + label span {
      background-color: #5c8d00;
    }
    .custom-radios input[type="radio"]#color-4 + label span {
      background-color: #001e80;
    }
    .custom-radios input[type="radio"]:checked + label span img {
      opacity: 1;
      width: 14px;
    }
    .wrapper {
      height: 40px;
      min-width: fit-content;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #fff;
      background-color: #6c757d;
      color: #ffffff;
    }
    .wrapper span {
      width: 100%;
      height: 40px;
      text-align: center;
      font-size: 20px;
      font-weight: normal;
      cursor: pointer;
      user-select: none;
      margin: auto;
    }
    .wrapper span.num {
      font-size: 20px;
      border-right: 2px solid #ffffff;
      border-left: 2px solid #ffffff;
      pointer-events: none;
      margin: auto;
    }
    .topcard:hover {
      box-shadow: 5px 5px 0px #c3c3c3 !important;
      transform: scale(0.99) !important;
    }
    .buttonCommon:hover {
      box-shadow: 3px 3px 0px #c3c3c3 !important;
      transform: scale(0.99) !important;
    }
  </style>
</head>
<body>
<%@include file="header.html"%>
<%
  ProductDAO productDAO = new ProductDAO();
  int id = Integer.parseInt(request.getParameter("id"));
  Optional<Product> productDetails = productDAO.getProductById(id);
  if (productDetails.isPresent()) {
    Product product = productDetails.get();
%>

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
                      src="assets/Products/Men/m1/mp11.jpg"
                      style="max-width: 320px !important; margin: auto"
                      class="d-block w-100"
                      alt="..."
              />
            </div>
            <div class="carousel-item">
              <img
                      src="assets/Products/Men/m1/mp12.jpg"
                      style="max-width: 320px !important; margin: auto"
                      class="d-block w-100"
                      alt="..."
              />
            </div>
            <div class="carousel-item">
              <img
                      src="assets/Products/Men/m1/mp13.jpg"
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
              <div class="custom-radios" style="padding-left: 40px">
                <div>
                  <input
                          type="radio"
                          id="color-1"
                          name="color"
                          value="color-1"
                          checked
                  />
                  <label for="color-1">
                        <span>
                          <img
                                  src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
                                  alt="Checked Icon"
                          />
                        </span>
                  </label>
                </div>

                <div>
                  <input
                          type="radio"
                          id="color-2"
                          name="color"
                          value="color-2"
                  />
                  <label for="color-2">
                        <span>
                          <img
                                  src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
                                  alt="Checked Icon"
                          />
                        </span>
                  </label>
                </div>

                <div>
                  <input
                          type="radio"
                          id="color-3"
                          name="color"
                          value="color-3"
                  />
                  <label for="color-3">
                        <span>
                          <img
                                  src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
                                  alt="Checked Icon"
                          />
                        </span>
                  </label>
                </div>

                <div>
                  <input
                          type="radio"
                          id="color-4"
                          name="color"
                          value="color-4"
                  />
                  <label for="color-4">
                        <span>
                          <img
                                  src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/242518/check-icn.svg"
                                  alt="Checked Icon"
                          />
                        </span>
                  </label>
                </div>
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
                <div class="custom-radios" style="padding-left: 40px">
                  <input
                          type="radio"
                          class="btn-check"
                          name="options-base"
                          id="option5"
                          autocomplete="off"
                          checked=""
                  />
                  <label
                          class="btn btn-outline-secondary"
                          style="border-radius: 0px; width: 40px !important"
                          for="option5"
                  >S</label
                  >

                  <input
                          type="radio"
                          class="btn-check"
                          name="options-base"
                          id="option6"
                          autocomplete="off"
                  />
                  <label
                          class="btn btn-outline-secondary"
                          style="border-radius: 0px; width: 40px !important"
                          for="option6"
                  >M</label
                  >
                  <input
                          type="radio"
                          class="btn-check"
                          name="options-base"
                          id="option7"
                          autocomplete="off"
                  />
                  <label
                          class="btn btn-outline-secondary"
                          style="border-radius: 0px; width: 40px !important"
                          for="option7"
                  >L</label
                  >
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
                                class="minus"
                                style="padding-left: 10px; padding-right: 10px"
                        >-</span
                        >
                    <span
                            class="num"
                            style="padding-left: 20px; padding-right: 20px"
                    >01</span
                    >
                    <span
                            class="plus"
                            style="padding-left: 10px; padding-right: 10px"
                    >+</span
                    >
                  </div>
                  <script>
                    const plus = document.querySelector(".plus"),
                            minus = document.querySelector(".minus"),
                            num = document.querySelector(".num");
                    let a = 1;
                    plus.addEventListener("click", () => {
                      if (a < 10) {
                        a++;
                        a = a < 10 ? "0" + a : a;
                        num.innerText = a;
                      }
                    });

                    minus.addEventListener("click", () => {
                      if (a > 1) {
                        a--;
                        a = a < 10 ? "0" + a : a;
                        num.innerText = a;
                      }
                    });
                  </script>
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
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      const formEl = document.querySelector(".addToCartForm");
      formEl.addEventListener("submit", function(event) {
        event.preventDefault();

        const urlParams = new URLSearchParams(window.location.search);
        const productId = urlParams.get('id');

        const productName = document.querySelector('input[name="dress-name"]').value;
        const productPrice = document.querySelector('input[name="dress-price"]').value;
        const quantity = document.querySelector('.num').innerText;

        const sizeRadios = document.querySelectorAll('input[name="options-base"]');
        let selectedSize = '';
        sizeRadios.forEach(radio => {
          if (radio.checked) {
            switch (radio.id) {
              case 'option5':
                selectedSize = 'small';
                break;
              case 'option6':
                selectedSize = 'medium';
                break;
              case 'option7':
                selectedSize = 'large';
                break;
              default:
                selectedSize = 'Unknown';
            }
          }
        });

        // Create the product object with the selected size
        const product = {
          id: productId, // Assign the product ID
          name: productName,
          price: productPrice,
          quantity: quantity,
          size: selectedSize // Include the selected size
        };

        // Retrieve the existing products from local storage
        let products = localStorage.getItem('products');
        // If there are no products in local storage, initialize an empty array
        if (!products) {
          products = [];
        } else {
          // If there are products, parse the JSON string back into an array
          products = JSON.parse(products);
        }

        // Append the new product to the array
        products.push(product);

        // Stringify the updated array and store it back in local storage
        localStorage.setItem('products', JSON.stringify(products));

        alert('Product added to cart!');
      });
    });


  </script>



</section>
<%
  } else {
    // Handle the case where the product is not found
  }
%>

<%@include file="footer.html"%>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"
></script>
</body>
</html>