<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 07/02/2023
  Time: 09:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container-fluid">

    <div class="row">
        <div class="col-sm-1"></div>


        <div class="col-sm-10" ng-app="ucsApp" ng-controller="ucsCtrl">
            <label>
                Buscar Unidade Curricular (Nome)
                <input type="text" ng-model="userSearch" style="align-self: center" >
            </label>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Unidade Curricular</th>
                        <th>Docente Responsavel</th>
                        <th>Email do Docente</th>
                    </tr>
                </thead>

                <tbody>
                <tr ng-repeat="x in ucs |filter: {'nome': userSearch}">
                    <td><a href="UC.jsp?id={{x.id}}">{{x.nome}}</a></td>
                    <td>{{x.docente.nome}}</td>
                    <td>{{x.docente.email}}</td>
                </tr>
                </tbody>


            </table>
        </div>

        <div class="col-sm-1"></div>
    </div>


</div>



</body>
</html>

<script>

    var app = angular.module('ucsApp', []);

    app.controller('ucsCtrl', function ($scope, $http){
        $http.get("<%=request.getContextPath()%>/rs/unCurricular/list").then(function (response){
            $scope.ucs = response.data;
        })
    })

</script>
