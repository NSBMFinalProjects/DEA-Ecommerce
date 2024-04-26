<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Optional" %>
<%@ page import="nsbm.dea.web.dao.UserDAO" %>
<%@ page import="nsbm.dea.web.models.User" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        @import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        * { font-family: "Poppins", sans-serif !important; }
        li { list-style: none !important; }
        a { text-decoration: none; }
        .top-section {
            background-color: #e7e7e7;
            height: 80vh;
            background-image: url('assets/contactus.svg');
            background-position: center;
            background-repeat: no-repeat;
            background-size: cover;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
            text-align: center;
        }
        .top-section h1 {
            font-size: 5rem;
            margin-bottom: 0;
        }
        .top-section p {
            font-size: 2rem;
            margin-top: 0;
        }
        .box-section {
            justify-content: center;
            padding: 50px 0;
            display: flex;
            flex-wrap: wrap;
            background-color: #e7e7e7;
        }
        .box-section .box {
            background-color: #243c55;
            color: white;
            padding: 30px;
            flex: 1 0 300px;
            margin: 10px;
            text-align: left;
            max-width: 1200px;
            display: flex;
            height: 175px;
        }
        .box-section .map {
            flex: 1 0 1200px;
            margin: 10px;
        }
        a {
            color: inherit;
            text-decoration: none;
        }

        .containerBase {
            width: 80%;
            padding: 30px;
            margin-top: -220px;
            background-color: #e7e7e7;
            box-shadow: 8px 8px 0px #203c55;
        }
        .icon {
            width: 50px;
            height: 50px;
        }

        .box-section .box button {
            width: 60%;
            border-radius: 0;
            box-shadow: 5px 5px 0px rgba(255, 255, 255, 0.5);
        }

    </style>
    <title>Title</title>

</head>
<body>
<%@include file="header.html"%>

<section class="top-section">
</section>

<section class="box-section">
    <div class="containerBase">
        <div class="row col-sm-12">
            <div class="col-sm-5">
                <div class="box row">
                    <div class="col-sm-2">
                        <img src="assets/Phone.png" alt="Contact Icon" class="icon">
                    </div>
                    <div class="col-sm-10">
                        <h3>Contact Us</h3>
                        <p>+94 123456789</p>
                        <a href="#">See all numbers and locations</a>
                    </div>
                </div>
                <div class="box row">
                    <div class="col-sm-2">
                        <img src="assets/Chat.png" alt="Chat Icon" class="icon">
                    </div>
                    <div class="col-sm-10">
                        <h3>Chat with us</h3></br>
                        <button class="btn btn-light">Chat</button>
                    </div>
                </div>
                <div class="box row">
                    <div class="col-sm-2">
                        <img src="assets/Location.png" alt="Location Icon" class="icon">
                    </div>
                    <div class="col-sm-10">
                        <h3>Meet Us</h3>
                        <p>29 Visaka Road</br>Dublication Rd, 00400</br>Sri Lanaka</p>
                    </div>
                </div>

            </div>
            <div class="col-sm-7">
                <div class="map">
                    <iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d15830.1966753657!2d80.6371148!3d7.2920223!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3ae3662be0c57725%3A0x79ab90a53deb7071!2sKandy%20City%20Centre!5e0!3m2!1sen!2slk!4v1713605678425!5m2!1sen!2slk"
                            width="104%" height="540px" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                </div>
            </div>
        </div>
    </div>
</section>

<%@include file="footer.html"%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
