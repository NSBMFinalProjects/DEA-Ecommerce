<%@ page import="nsbm.dea.admin.model.Admin" %>
<%@ page import="nsbm.dea.admin.dao.AdminDAO" %>
<%@ page import="java.util.Optional" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Document</title>
  <%
    ServletContext context = request.getServletContext();
    Admin admin = (Admin) context.getAttribute("admin");
    String AdminId = admin.getId();
    AdminDAO adminDAO=new AdminDAO();
    Optional<Admin> adminOptional=adminDAO.getByID(AdminId);
    Admin admin1 = adminOptional.orElse(null);
  %>

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
    .adminCard:hover {
      box-shadow: 5px 5px 0px #dadada !important;
      transform: scale(0.98);
    }
    .editBtn:hover {
      box-shadow: 5px 5px 0px #ffffff !important;
      transform: scale(0.98);
    }
  </style>
</head>
<body>
<%@include file="headerAdmin2.html"%>

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
  </div>
  <div style="margin-top: 20px">
    <div
            style="
            width: 90%;
            background-color: #203c55;
            box-shadow: 10px 10px 0px #ffffff;
            height: 400px;
            margin: auto;
            display: flex;
            align-items: center;
            justify-content: space-evenly;
          "
    >
      <div
              onclick="redirectToManageTags();"
              class="adminCard"
              style="
              width: 200px;
              height: 68%;
              background-color: #ffffff;
              box-shadow: 10px 10px 0px #dadada;
              transition: 0.4s ease-in-out;
              cursor: pointer;
            "
      >
        <center>
          <img
                  src="assets/tag.svg"
                  width="150px"
                  style="margin-top: 10px"
                  alt=""
          />
        </center>
        <p
                style="
                color: #203c55;
                font-size: 16px;
                letter-spacing: 5px;
                font-weight: 600;
                text-align: center;
                margin-top: 10px;
              "
        >
          manage
        </p>
        <p
                style="
                color: #203c55;
                font-size: 40px;
                letter-spacing: 3px;
                font-weight: 600;
                text-align: center;
                margin-top: -20px;
              "
        >
          Tags
        </p>
      </div>
      <div
              onclick="redirectToManageSets();"
              class="adminCard"
              style="
              width: 200px;
              height: 68%;
              background-color: #ffffff;
              box-shadow: 10px 10px 0px #dadada;
              transition: 0.4s ease-in-out;
              cursor: pointer;
            "
      >
        <center>
          <img
                  src="assets/collection.png"
                  width="150px"
                  style="margin-top: 10px"
                  alt=""
          />
        </center>
        <p
                style="
                color: #203c55;
                font-size: 16px;
                letter-spacing: 5px;
                font-weight: 600;
                text-align: center;
                margin-top: 10px;

              "
        >
          manage
        </p>
        <p
                style="
                color: #203c55;
                font-size: 40px;
                letter-spacing: 3px;
                font-weight: 600;
                text-align: center;
                margin-top: -20px;
              "
        >
          Sets
        </p>
      </div>
      <div
              onclick="redirectToManageOrders();"
              class="adminCard"
              style="
              width: 200px;
              height: 68%;
              background-color: #ffffff;
              box-shadow: 10px 10px 0px #dadada;
              transition: 0.4s ease-in-out;
              cursor: pointer;
            "
      >
        <center>
          <img
                  src="assets/order.svg"
                  width="150px"
                  style="margin-top: 10px"
                  alt=""
          />
        </center>
        <p
                style="
                color: #203c55;
                font-size: 16px;
                letter-spacing: 5px;
                font-weight: 600;
                text-align: center;
                margin-top: 10px;
              "
        >
          manage
        </p>
        <p
                style="
                color: #203c55;
                font-size: 40px;
                letter-spacing: 3px;
                font-weight: 600;
                text-align: center;
                margin-top: -20px;
              "
        >
          Orders
        </p>
      </div>
      <div
              onclick="redirectToManageProducts();"
              class="adminCard"
              style="
              width: 200px;
              height: 68%;
              background-color: #ffffff;
              box-shadow: 10px 10px 0px #dadada;
              transition: 0.4s ease-in-out;
              cursor: pointer;
            "
      >
        <center>
          <img
                  src="assets/prodduct.png"
                  width="150px"
                  style="margin-top: 10px"
                  alt=""
          />
        </center>
        <p
                style="
                color: #203c55;
                font-size: 16px;
                letter-spacing: 5px;
                font-weight: 600;
                text-align: center;
                margin-top: 10px;
              "
        >
          manage
        </p>
        <p
                style="
                color: #203c55;
                font-size: 40px;
                font-weight: 600;
                text-align: center;
                margin-top: -20px;
              "
        >
          Products
        </p>
      </div>
      <div
              onclick="redirectToEditProducts();"
              class="adminCard"
              style="
              width: 200px;
              height: 68%;
              background-color: #ffffff;
              box-shadow: 10px 10px 0px #dadada;
              transition: 0.4s ease-in-out;
            "
      >
        <center>
          <img
                  src="assets/editHome.svg"
                  width="150px"
                  style="margin-top: 10px"
                  alt=""
          />
        </center>
        <p
                style="
                color: #203c55;
                font-size: 16px;
                letter-spacing: 5px;
                font-weight: 600;
                text-align: center;
                margin-top: 10px;
              "
        >
          edit
        </p>
        <p
                style="
                color: #203c55;
                font-size: 40px;
                font-weight: 600;
                text-align: center;
                margin-top: -20px;
              "
        >
          Products
        </p>
      </div>
    </div>
  </div>
  <div style="width: 90%; margin: auto; margin-top: 20px">
    <p
            style="
            font-size: 40px;
            font-weight: 600;
            color: #203c55;
            text-align: left;
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
            text-align: left;
            letter-spacing: 7px;
            margin-top: -20px;
            padding-left: 5px;
          "
    >
      BLOOM CLOTHING
    </p>
  </div>
  <div
          style="width: 90%; height: 3px; background-color: #203c55; margin: auto"
  ></div>

  <div style="display: flex; width: 90%; margin: auto">
    <div>
      <div
              style="
              background-image:
              background-size: cover;
              width: 250px;
              height: 250px;
              background-color: #203c55;
              margin-top: 20px;
              box-shadow: 10px 10px 0px #ffffff;
            "
      ></div>
    </div>

    <div style="width: 100%; margin-top: 20px">
      <div
              style="
              display: flex;
              justify-content: space-between;
              width: 100%;
              padding-left: 40px;
            "
      >
        <p style="font-size: 22px; font-weight: 600; color: #203c55">
          First Name :
        </p>
        <p style="font-size: 22px; font-weight: 600; color: #203c55">
          <%=admin1.getName()%>
        </p>
      </div>
      <div
              style="
              display: flex;
              justify-content: space-between;
              width: 100%;
              padding-left: 40px;
            "
      >
        <p style="font-size: 22px; font-weight: 600; color: #203c55">
          Last Name :
        </p>
        <p style="font-size: 22px; font-weight: 600; color: #203c55">
          <%=admin1.getUsername()%>
        </p>
      </div>
      <div
              style="
              display: flex;
              justify-content: space-between;
              width: 100%;
              padding-left: 40px;
            "
      >
        <p style="font-size: 22px; font-weight: 600; color: #203c55">
          Admin Email :
        </p>
        <p style="font-size: 22px; font-weight: 600; color: #203c55">
          <%=admin1.getEmail()%>
        </p>
      </div>

      <div
              class="editBtn"
              style="
              width: fit-content;
              margin-left: auto;

              background-color: #203c55;
              box-shadow: 10px 10px 0px #ffffff;
              transition: 0.4s ease-in-out;
              margin-top: 60px;
            "
      >
        <a href="editAdimin.html">
          <p
                  style="
                  color: #ffffff;
                  text-align: center;
                  font-size: 22px;
                  font-weight: 600;
                  padding: 5px 60px 5px 60px;
                "
          >
            Edit Profile
          </p>
        </a>
      </div>
    </div>
  </div>
  <script>
    function redirectToManageTags() {
      window.location.href = "manageTags.jsp";
    }
    function redirectToManageSets() {
      window.location.href = "manageSets.jsp";
    }
    function redirectToManageOrders() {
      window.location.href = "manageOrders.jsp";
    }
    function redirectToManageProducts() {
      window.location.href = "manageProducts.jsp";
    }
    function redirectToEditProducts() {
      window.location.href = "editProducts.jsp";
    }
  </script>
</section>

<%@include file="footerAdmin.html"%>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"
></script>
</body>
</html>
