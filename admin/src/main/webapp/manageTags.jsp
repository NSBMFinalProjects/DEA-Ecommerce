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
          Manage Tags
        </p>
      </div>
      <div style="margin-top: 20px">
        <form class="addTag">
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
                <p style="color: #ffffff; font-weight: 600">Tag name:</p>
                <input
                  style="
                    width: 100%;
                    height: 50px;
                    padding: 20px;
                    border: none;
                    font-size: 18px;
                    margin-top: -20px;
                    font-weight: 600;
                  "
                  type="text"
                  name="tagName"
                  placeholder="Enter tag name"
                  id=""
                />
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
              <button
                type="submit"
                style="
                  width: 100%;
                  border: none;
                  color: #203c55;
                  text-align: center;
                  font-size: 22px;
                  font-weight: 600;
                  padding: 5px 60px 5px 60px;
                "
              >
                Add tag
              </button>
            </div>
          </div>
        </form>

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
            Current tags
          </p>
        </div>
        <div
          style="
            width: 90%;
            height: 3px;
            background-color: #203c55;
            margin: auto;
            margin-bottom: 20px;
          "
        ></div>
        <!-- loop this divition (class : tagItem) -->
        <div class="tagItem" style="display: flex; width: 90%; margin: auto">
          <div style="width: 100%">
            <div
              style="display: flex; justify-content: space-between; width: 100%"
            >
              <p style="font-size: 22px; font-weight: 600; color: #203c55">
                Tag 1
              </p>
              <a href="#"
                ><img src="assets/delete.svg" width="30px" alt=""
              /></a>
            </div>
          </div>
        </div>
      </div>
    </section>

    <script>
      document.addEventListener("DOMContentLoaded", function () {
        const formEl = document.querySelector(".addTag");
        formEl.addEventListener("submit", (event) => {
          event.preventDefault();

          const formData = new FormData(formEl);
          const data = Object.fromEntries(formData);

          fetch("http://localhost:8080/web/auth/login", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
          })
            .then((res) => res.json())
            .then((data) => console.log(data))
            .catch((err) => console.log(err));
        });
      });
    </script>

    <%@include file="footerAdmin.html"%>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
