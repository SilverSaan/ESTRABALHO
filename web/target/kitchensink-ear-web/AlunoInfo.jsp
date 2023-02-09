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

<div class="container-fluid" ng-app="profileApp" ng-model="Student" ng-init="Student = null" ng-controller="profileCtrl" id="contain">
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

        <div class="col-sm-6" style="max-height: 500px; overflow-y: scroll">
            <h3>Inscrições do Aluno</h3>
            <table class="table table-bordered table-hover table-striped" >
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

        <div class="col-sm-5" style="max-height: 500px; overflow-y: scroll">
            <h3>Historico de Presenças</h3>
            <table class="table table-bordered table-hover" >

                <thead>
                <tr>
                    <th>Data da Aula</th>
                    <th>Unidade Curricular</th>
                    <th>Professor</th>
                    <th>Checar Fatos</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="pres in Student.presencas | orderBy: ['-data.year', '-data.monthValue','-data.dayOfMonth']">
                    <td>
                        <a ng-click="goToAulaInfo(pres.id)">
                        {{pres.data.dayOfMonth}}/{{pres.data.monthValue}}/{{pres.data.year}}
                        </a>
                    </td>
                    <td>{{pres.unidadeCurricular.nome}}</td>
                    <td>{{pres.unidadeCurricular.docente.nome}}</td>
                    <td><button class="btn btn-success" ng-click="loadFatos(pres.id)" data-toggle="modal" data-target="#modalFatos">
                        Ver Participacao
                    </button></td>

                </tr>

                </tbody>

            </table>
        </div>
    </div>



    <div id="modalFatos" class="modal fade" role="dialog" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4>Fatos desse aluno</h4>
                </div>

                <div class="modal-body" style="max-height: 1100px">
                    <table>
                        <thead>
                        <tr>
                            <th>Tipo de Fato</th>
                            <th>Classificação</th>
                            <th>Descrição</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="x in listaFatos">
                            <td>{{x.tipo}}</td>
                            <td>{{x.evaluation}}</td>
                            <td>{{x.description}}</td>
                        </tr>
                        </tbody>
                    </table>


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Fechar</button>
                </div>
            </div>
        </div>

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

    app.controller('profileCtrl', function ($scope, $http, $location, $window){
        //$locationProvider.html5Mode(true);
        var userId = $location.search().id;
        console.log($location.search())

        $scope.testNum = 50;
        $scope.getNum = function (num){
            return new Array(num);
        }

        $scope.presencaID = {
            alunoId : null,
            aulaId : null
        }

        $scope.listaFatos = null;

        $scope.loadFatos = function (id){
            $scope.presencaID = {
                alunoId: parseInt(userId),
                aulaId: id
            }

            $http.post("<%=request.getContextPath()%>/rs/usuarios/fatos", $scope.presencaID)
            .then(function (response){
                $scope.listaFatos = response.data;
                console.log($scope.listaFatos);
            }, function (error){
                alert(error);
                console.log(error);
            })

        }

        $scope.goToAulaInfo = function (id){
            $window.location.href ="<%=request.getContextPath()%>/aulasInfo.jsp?id=" + id;

        }

        $http.get("<%=request.getContextPath()%>/rs/usuarios/"+userId).then(function (response){
            $scope.Student = response.data;
            return $http.get("<%=request.getContextPath()%>/rs/usuarios/"+userId+"/ucs").then(function (response){
                $scope.Student.ucs = response.data;
                return $http.get("<%=request.getContextPath()%>/rs/usuarios/"+userId+"/partic").then(function (response){
                    $scope.Student.presencas = response.data;
                })
            })
        });





    })


</script>

</body>
</html>


