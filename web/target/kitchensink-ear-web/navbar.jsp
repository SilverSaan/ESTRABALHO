<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 27/01/2023
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .tableGray{
            align-content: center;
            text-align: center;
            display: flex;
            justify-content: left;
            margin-top: 30px;
        }
        .tableGray table, th , td  {
            border: 1px solid grey;
            border-collapse: collapse;
            padding: 5px;

        }
        .tableGray table tr:nth-child(odd) {
            background-color: #f1f1f1;
        }
        .tableGray table tr:nth-child(even) {
            background-color: #ffffff;
        }

        .topnav{
            background-color: #333;
            overflow: hidden;
        }

        .topnav a{
            float: left;
            color: orange;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 20px;
        }

        .topnav a:hover {
            background-color: limegreen;
            color: black;
        }

        .topnav a.active{
            background-color: orange;
            color:black;
        }

        .sessionName{
            margin-left: auto;
            margin-right: 0;
            float: right;
        }

        .dropdown {
            float:right;
            overflow: auto;
        }

        .dropdown .dropbtn{
            font-size: 20px;
            border: none;
            outline: none;
            color: white;
            padding: 14px 16px;
            background-color: orange;
            font-family: inherit;
            margin: 0;
        }

        .dropdown-content {
            display: none;
            position: fixed;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }

        .dropdown-content a{
            float: none;
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            text-align: left;
        }

        .dropdown-content a:hover{
            background-color: #D5D5D5;
        }

        .dropdown:hover .dropdown-content{
            display: block;
        }

        .show  {display: block}
    </style>
</head>
<body>
<div class="topnav">
    <a class="active" href="<%=request.getContextPath()%>/main.jsp" id="nav1">
        Main Page
    </a>
    <a href="#Page1" id="nav2">Pagina_1</a>
    <a href="#Page2" id="nav3">Pagina_2</a>
    <a href="#Page3" id="nav4">Pagina_3</a>

    <div class="dropdown" style="float: right">
        <button id="drpbutton" onclick="showDropdownMenu()" class="dropbtn">
            <i class="fa fa-caret-down"></i>
        </button>

        <div class="dropdown-content">
            <a href="#home">Home</a>
            <a href="#Logout" onclick="logout()">Logout</a>
        </div>
    </div>


    <br>
</div>

<script>
    function checkifLog() {
        if (sessionStorage.getItem("user")) {
            document.getElementById("drpbutton").innerText = JSON.parse(sessionStorage.getItem("user")).nome;
        } else {
            window.location.href = "<%=request.getContextPath()%>/login.jsp"
        }
    }
    window.onload = checkifLog;

    function showDropdownMenu(){
        document.getElementById("drpmenu").classList.toggle("show");
    }

    function logout(){
        sessionStorage.removeItem("user");

        window.location.href = "<%=request.getContextPath()%>/login.jsp";
    }

    window.onclick = function (event){
        if(!event.target.matches('.dropbtn')){
            var dropdown = document.getElementsByClassName("dropdown-content");
            var i;
            for (i = 0; i < dropdown.length; i++){
                var openDropdown = dropdown[i];
                if(openDropdown.classList.contains('show')){
                    openDropdown.classList.remove('show');
                }
            }
        }
    }

    function showActivePage(){
        var links = document.querySelectorAll('.topnav a');
        var currentUrl = window.location.href;

        for(var i=0; i< links.length; i++){
            var link = links[i];
            var linkUrl = link.href ;


            if(currentUrl === linkUrl){
                link.classList.add('active');
                break;
            }
        }

    }

    window.addEventListener('load', showActivePage);

</script>

</body>
</html>
