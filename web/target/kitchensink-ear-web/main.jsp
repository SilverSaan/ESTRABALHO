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


    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/Tables.css">
</head>
<body style="margin: 0;">

<jsp:include page="navbar.jsp"></jsp:include>

<div id="box" style="background-color: #D5D5D5; width: fit-content; padding: 10px; margin-left: 50px; margin-top: 50px; border-radius: 33px">
    <div ng-app="alunoApp" ng-controller="alunoControl" ng-init="usuarios=null" style="justify-content: center; text-align: center">
        <!-- Colocar um ng-if aqui depois, que cheque se o Usuario é professor -->
        <br>
        Search by Name
        <br>
        <input type="text" ng-model="userSearch">
        <p style="color: orange">Nome do Aluno</p>
        <div class="tbl-content">
            <table cellpadding="0" cellspacing="0" border="0" style="table-layout: auto; ">
                <tr ng-repeat="x in usuarios |filter: {'nome': userSearch} | orderBy: 'numero'" ng-model="usuarios">
                    <td>{{x.nome}} - {{x.numero}}</td>
                    <td><button class="btnselectastudent" id="btn{{x.id}}"></button></td>

                </tr>
            </table>
        </div>
        <br>
    </div>
</div>


<div ng-app="ucApp" ng-controller="ucCtrl" ng-init="ucs=null">




</div>

<script>



    var app = angular.module('alunoApp', []);

    app.controller('alunoControl', function ($scope, $http){
        $http.get("<%=request.getContextPath()%>/rs/usuarios/list").then(function (response){
            $scope.usuarios = response.data.filter(function (usuarios){
                return !usuarios.teacher;

            });
        })
    })





</script>

</body>
</html>
