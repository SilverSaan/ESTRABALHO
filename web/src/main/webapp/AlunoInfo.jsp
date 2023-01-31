<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 27/01/2023
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Perfil do Aluno - MVC</title>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container-fluid" ng-app="profileApp" ng-model="Student" ng-controller="profileCtrl">
    <h1>Perfil do Aluno</h1>
    <div class="row">
        <div class="col-sm-8">

        </div>


        <div class="col-sm-2" style="background-color:white;">
            <p style="background-color:darkgray; font-size: 32px; text-align: center">{{Student.nome}}  </p>
            <br>
            <p style="background-color:darkgray; font-size: 32px; text-align: center">{{Student.numero}}</p>
        </div>
        <div class="col-sm-2">
            <img src="https://cdn.onlinewebfonts.com/svg/img_218090.png"
                 class="img-circle" alt="user Picture" style="width:120px;" >
            </img>
        </div>
    </div>
    <br/>
    <div class="row">
        <div class="col-sm-8">
            <h3>Inscrições do Aluno</h3>
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>Unidade Curricular</th>
                    <th>Codigo</th>
                    <th>Professor</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="uc in Student.ucs" >
                    <td><a href="#UC">{{uc.nome}}</a></td>
                    <td>{{uc.code}}</td>
                    <td>{{uc.docente.nome}}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col-sm-1"></div>

        <div class="col-sm-3">
            <h3>Historico de Presenças</h3>
            <table class="table table-bordered table-hover">

                <thead>
                <tr>
                    <th>Data da Aula</th>
                    <th>Unidade Curricular</th>
                    <th>Professor</th>
                </tr>
                </thead>

            </table>
        </div>

    </div>

    <div class="row">
        <div class="col-sm-1"></div>
        <div class="col-sm-10">
            <!-- Trabalhar no mapa de Presenças -->

        </div>
        <div class="col-sm-1"></div>
    </div>

</div>




<script>
    var app = angular.module("profileApp", ["ng"]);

    //Configurações necessarias para ler os parametros de query
    app.config(['$locationProvider', function($locationProvider){
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        })
    }])

    app.controller('profileCtrl', function ($scope, $http, $location){
        //$locationProvider.html5Mode(true);
        var userId = $location.search().id;
        console.log($location.search())

        $http.get("<%=request.getContextPath()%>/rs/usuarios/"+userId).then(function (response){
            $scope.Student = response.data;
        })
    })
</script>

</body>
</html>


