<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 07/02/2023
  Time: 09:56
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

<div class="container-fluid" ng-app="ucInfoApp" ng-controller="ucCtrl">
    <div class="row">
        <div class="col-sm-8">
            <h2>Unidade Curricular: {{thisUC.nome}}</h2>
            <br>
            <p>Docente: {{thisUC.docente.nome}}</p>
        </div>

        <div class="col-sm-4">
            <h3>Lista de Aulas</h3>
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>Data da Aula</th>
                    <th>Numero de Presencas</th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <tr ng-repeat="x in thisUCAulas | orderBy: orderBy: ['-data.year', '-data.monthValue','-data.dayOfMonth'] ">
                    <td>{{x.data.dayOfMonth}}/{{x.data.monthValue}}/{{x.data.year}}</td>
                    <td>{{x.presencas.length }}</td>
                    <td><a ng-click="goToAulaInfo(x.id)">Ver Aula</a></td>

                </tr>
                </tbody>

            </table>
        </div>
    </div>

    <div class="row" id="Adicionar aula">

        <div class="col-sm-1"></div>

        <div class="col-sm-11">

            <div ng-if="isTeacher">
                <h1>Adicionar Aula</h1>

                <label>
                    Data:
                    <br>
                    <input type="datetime-local" ng-model="novaAula.data">
                </label>
                <br>
                    Sumario:
                    <br>
                    <textarea rows="10" cols="20" ng-model="novaAula.sumario">

                    </textarea>

                <button class="btn btn-success" ng-click="criarAula()">Submeter:</button>
            </div>
        </div>

    </div>

</div>



</body>
</html>

<script>
    var app = angular.module("ucInfoApp",[])


    //Configurações necessarias para ler os parametros de query
    app.config(['$locationProvider', function($locationProvider){
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        })
    }])

    app.controller('ucCtrl', function ($scope, $http, $location,  $window) {

        $scope.user = JSON.parse(sessionStorage.getItem("user"));
        console.log($scope.user);
        $scope.isTeacher = $scope.user.teacher;
        console.log($scope.isTeacher);
        var ucId = $location.search().id;
        console.log("id = " + ucId);

        $scope.sortByData = function (aula){
            var dat= new Date(aula.data.year, aula.data.month, aula.data.day)
            return Date
        }
        $scope.thisUCAulas = null;
        $scope.novaAulaData = null;

        $http.get("<%=request.getContextPath()%>/rs/unCurricular/"+ ucId).then(function (response){
            console.log(response.data);
            $scope.thisUC = response.data;
            return $http.get("<%=request.getContextPath()%>/rs/unCurricular/" + ucId +"/aulas").then(function (response){
                $scope.thisUCAulas = response.data;

            }, function (error){
                alert("Erro: Não foi possivel alcançar as aulas");
            })

        }, function (error){
            alert("Erro: UC Não existe");
        })

        $scope.novaAula = {
            data: null,
            sumario: null,
            unidadeCurricularID: ucId,

        }

        $scope.goToAulaInfo = function (id){
            $window.location.href ="<%=request.getContextPath()%>/aulasInfo.jsp?id=" + id;

        }

        $scope.criarAula = function (){

            $http.post("<%=request.getContextPath()%>/rs/aula/create", $scope.novaAula).then(function (response){
                alert("Sucesso");
                $http.get("<%=request.getContextPath()%>/rs/unCurricular/" + ucId +"/aulas").then(function (response){
                    $scope.thisUCAulas = response.data;
                })

            }, function (error){
                alert("Erro");
                alert(error.data);
            })
        }



    })
</script>

