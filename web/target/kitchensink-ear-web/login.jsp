<html>
    <head>
        <style>
            .loginStyle{
                display: flex;
                justify-content: center;
                align-content: center;
                height: 250px;

            }
            .formstyle{
                width: 300px;
                height: 200px;
                display: block;
                border-radius: 46px;
                background-color: #D9d9d9;
                text-align: center;
                outline: solid 1px black;
            }

            .button{
                background-color: lightgreen;
                color: white;
                width: 202px;
                height: 39px;
                border: 2px solid black;
                box-shadow: 0 4px 4px rgba(0,0,0,0.25);
                border-radius: 19px;

            }

            .button:hover{
                background-color: #45a049;

            }

            .button:active{
                box-shadow: inset 0px 4px 4px rgba(0, 0, 0, 0.25);
            }

        </style>
        <title>Title</title>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>

    </head>
    <body>
    <h2 style="text-align: center">Login Sistema</h2>
    <div class="loginStyle" ng-app="loginApp" ng-controller="formController"  >
        <div class="formstyle">
            <form id="logform"
                  style="display: inline-block; margin-left: auto; margin-right: auto; margin-top: 30px">
                <label for="fname">Username</label>
                <br>
                <input type="text" id="fname" name="fname" value="" ng-model="username">
                <br>
                <label for="fnumber">Number of User</label>
                <br>
                <input type="password" id="fnumber" name="fnumber" ng-model="numero">
                <br>
                <br>
                <input class="button" type="button" id="subbutton" value="Login" ng-click="postdata(username, numero)">
                <br>
                <p style="color: red">{{msg}}</p>
            </form>
            <html:errors property="error.custom"/>
        </div>

    </div>
    <br>

    </body>

</html>

<script>

    function checkifLogged()
    {
        if (sessionStorage.getItem("user") != null) {
            window.location.href = ("<%=request.getContextPath()%>/main.jsp");
        }
    }

    window.onload = checkifLogged;

    var app = angular.module("loginApp", []);

    app.controller('formController', function ($scope, $http){
        var url = "<%=request.getContextPath()%>/rs/usuarios/login";
        $scope.username = null;
        $scope.numero = null;
        $scope.msg = null;

        $scope.postdata = function (username, numero){
            var data = {
                username: username,
                password: numero
            };
            $http.post(url,
                JSON.stringify(data)).then(function (response){
                if(response.data){
                    sessionStorage.setItem("user",JSON.stringify(response.data));
                    $scope.msg ="Sucesso"
                    window.location.href = "<%=request.getContextPath()%>/main.jsp";

                }
            }, function (error){
                $scope.msg = "Username or password does not exist";
            })

        }

    })
</script>