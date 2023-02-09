<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 27/01/2023
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main Page</title>
    <style>


        .btnselectastudent{
            cursor: pointer; background-color: red; border-radius: 50px; height: 20px; width: 20px
        }

    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

</head>
<body style="margin: 0;">

<jsp:include page="navbar.jsp"></jsp:include>

<div class="container-fluid" ng-app="alunoApp" ng-controller="alunoControl">
    <div class="row" ng-if="user.teacher">
        <div class="col-sm-1"></div>
        <div class="col-sm-10">
            <br>
            <table style="width: 100%">
                <thead>
                <tr>
                    <th style="width: 30%">Nome</th>
                    <th style="width: 60%">Numero</th>
                    <th style="width: 10%">Ver Perfil</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="x in usuarios">
                    <td>{{x.nome}}</td>
                    <td style="text-align: center">{{x.numero}}</td>
                    <td style="text-align: center"><a href="AlunoInfo.jsp?id={{x.id}}" class="btn btn-success">GO</a></td>
                </tr>
                </tbody>

            </table>


        </div>
        <div class="col-sm-1"></div>

    </div>

    <div class="row" ng-if="!user.teacher">
       <div class="col-sm-5">
           <h1>Minhas Unidades Curriculares</h1>
           <table>
                <thead>
                <tr>
                    <th style="width: 60%">Unidade Curricular</th>
                    <th style="width: 40%">Docente</th>
                </tr>
                </thead>
               <tbody>
               <tr ng-repeat="x in myucs">
                   <td>{{x.nome}}</td>
                   <td>{{x.docente.nome}}</td>
               </tr>
               </tbody>
           </table>

       </div>

        <div class="col-sm-5">
            <h1>Perfil</h1>
            <a class="btn btn-success" href="AlunoInfo.jsp?id={{user.id}}">Ver Perfil de {{user.nome}}</a>
        </div>

    </div>


</div>


<div ng-app="ucApp" ng-controller="ucCtrl" ng-init="ucs=null">




</div>

<script>



    var app = angular.module('alunoApp', []);

    app.controller('alunoControl', function ($scope, $http){

        $scope.user = JSON.parse(sessionStorage.getItem("user"));


        $http.get("<%=request.getContextPath()%>/rs/usuarios/list").then(function (response){
            $scope.usuarios = response.data.filter(function (usuarios){
                return !usuarios.teacher;

            });
        })

        $http.get("<%=request.getContextPath()%>/rs/usuarios/"+ $scope.user.id + "/ucs")
            .then(function (response){
                $scope.myucs = response.data;
            })

    })




</script>

</body>
</html>
