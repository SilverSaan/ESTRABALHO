<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 31/01/2023
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Informação de uma determinada aula</title>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

    <div class="container-fluid" ng-app="aulaApp" ng-controller="aulaCtrl" ng-model="aula">
        <div class="row">
            <div class="col-sm-8">
                <h2>Aula de {{aula.unidadeCurricular.nome}}</h2>
                <br>
                <p>Data: {{aula.data.dayOfMonth}}/{{aula.data.monthValue}}/{{aula.data.year}}  - {{aula.data.hour}}: {{aula.data.minute}}</p>
            </div>

            <div class="col-sm-4">
                <h3>Nome do Professor</h3>
                <p>{{aula.unidadeCurricular.docente.nome}}</p>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-4">
                <h3>Sumario: </h3>
                <br>
                Lorem Ipsum Dolor sit amet...{{aula.sumario}}
            </div>

            <div class="col-sm-4"> </div>

            <div class="col-sm-4">
                <h3>Alunos Presentes:</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Numero</th>
                            <th>Email</th>
                            <th ng-if="isTeacher">Adicionar Fato</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="allumni in presencas" ng-model="presencas">
                            <td>
                                <a ng-click="goToAlunoInfo(allumni.id)">
                                {{allumni.nome}}
                                </a>
                            </td>
                            <td>{{allumni.numero}}</td>
                            <td>{{allumni.email}}</td>
                            <td ng-if="isTeacher"><button ng-click="setPresencaIDFato(allumni.id)" class="btn btn-success"
                            data-toggle="modal" data-target="#modalFato">
                                Adicionar Fato
                            </button></td>
                        </tr>
                    </tbody>

                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-1"></div>
            <div class="col-sm-10">
                <div ng-if="isTeacher">
                    <h1>Alterar Lista de Presenças</h1>
                    <ul ng-repeat="alunos in inscritos | orderBy: 'numero' " ng-model="listaPresencasMarcadas" ng-init="alunos.isPresent = false">

                        <li>
                            <label>
                                <input type="checkbox" ng-model="alunos.isPresent" ng-change="updateListaPresenca(alunos)">
                                {{alunos.nome}} - {{alunos.numero}}
                            </label>

                        </li>

                    </ul>
                    <br>
                    <p>Lembrar: Isso vai alterar a lista de Alunos por completo.</p><br>
                    <button type="button" class="btn btn-default" ng-click="subPres()">Submeter Nova Lista</button>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>


        <div id="modalFato" class="modal fade" role="dialog" >
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4>Adicionar Fato</h4>
                    </div>

                    <div class="modal-body">
                        <p>Tipo de Fato: </p> <br>
                        <button class="btn" ng-click="setTipoFato('Atencao')">Atenção</button>
                        <button class="btn" ng-click="setTipoFato('Participacao')">Participacao</button>
                        <button class="btn" ng-click="setTipoFato('Curiosidade')">Curiosidade</button>
                        <button class="btn" ng-click="setTipoFato('Comunicacao')">Comunicação</button>
                        <button class="btn" ng-click="setTipoFato('Responsabilidade')">Responsabilidade</button>
                        <br>
                        <form>
                            <input ng-model="novoFato.evaluation" type="radio" value="1" name="evaluati"> 1
                            <input ng-model="novoFato.evaluation" type="radio" value="2" name="evaluati"> 2
                            <input ng-model="novoFato.evaluation" type="radio" value="3" name="evaluati"> 3
                            <input ng-model="novoFato.evaluation" type="radio" value="4" name="evaluati"> 4
                            <input ng-model="novoFato.evaluation" type="radio" value="5" name="evaluati"> 5
                        </form>

                        <textarea rows="10" cols="20" ng-model="novoFato.description">

                    </textarea>
                        <br>
                        <button class="btn btn-success" ng-click="printEstado()">Start</button>
                        <button class="btn btn-danger" style="align-self: end" data-dismiss="modal">Fechar</button>
                    </div>

                </div>
            </div>

        </div>

    </div>





<script>
    var app = angular.module("aulaApp",[])


    //Configurações necessarias para ler os parametros de query
    app.config(['$locationProvider', function($locationProvider){
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        })
    }])

    app.controller('aulaCtrl', function ($scope, $http, $location,  $window){

        $scope.user = JSON.parse(sessionStorage.getItem("user"));
        console.log($scope.user)
        $scope.isTeacher = $scope.user.teacher;
        console.log($scope.isTeacher);
        var classId= $location.search().id;
        console.log("id = " + classId);
        $scope.aula = null;
        $scope.presencas = null;
        $scope.inscritos = null;

        $scope.listaPresencasMarcadas = [];

        $scope.novoFato = {
            description: null,
            evaluation: null,
            tipo : null,
            presencaID : {
                alunoId: null,
                aulaId: null
            }
        }
        $scope.printEstado = function (){
            console.log($scope.novoFato);
            console.log("HEY YA");

            $http.post("<%=request.getContextPath()%>/rs/aula/criarFato", $scope.novoFato)
            .then(function (response){
                alert("Enviado com Sucesso");
            }, function (error){
                alert("Algo ocorreu em: \n " + error.data);
            })

        }

        $scope.setPresencaIDFato = function (alunoID){

            $scope.novoFato.presencaID = {
                alunoId: alunoID,
                aulaId: $scope.aula.id
            }
            console.log($scope.novoFato);
        }

        $scope.setTipoFato = function (string){
            $scope.novoFato.tipo = string
        }

        $scope.updateListaPresenca = function (aluno){
            if(!aluno.isPresent){
                //var index = $scope.listaPresencasMarcadas.indexOf(aluno);
                $scope.listaPresencasMarcadas = $scope.listaPresencasMarcadas.filter(function (presenca){
                    return presenca.alunoId !== aluno.id;
                });
            }else{
                idPresenca = {
                    alunoId: aluno.id,
                    aulaId: $scope.aula.id
                }

                $scope.listaPresencasMarcadas.push(idPresenca);
            }

        };

        $scope.subPres = function (){
            return $http.post("<%=request.getContextPath()%>/rs/aula/"+ classId + "/updatePresenca", $scope.listaPresencasMarcadas)
                .then(function (response){
                    return $http.get("<%=request.getContextPath()%>/rs/aula/"+ classId + "/presenca").then(function (response){
                        $scope.presencas = response.data;
                    })
                }, function (error){
                    alert("ERRO!!");
                })
        }


        //Como a usar location parece ser necessario fazer os links dessa forma...
        $scope.goToAlunoInfo = function(id) {
          $window.location.href ="<%=request.getContextPath()%>/AlunoInfo.jsp?id=" + id;

        };



        $http.get("<%=request.getContextPath()%>/rs/aula/"+ classId).then(function (response){
            $scope.aula = response.data;
            return $http.get("<%=request.getContextPath()%>/rs/aula/"+ classId + "/presenca").then(function (response){
                $scope.presencas = response.data;
                return $http.get("<%=request.getContextPath()%>/rs/unCurricular/" + $scope.aula.unidadeCurricular.id + "/inscritos")
                    .then(function (response){
                    $scope.inscritos = response.data;
                })
            }, function (error){
                alert("error-presencas not found")
            })
        }, function (error){
            alert("error: aula not found");
        })


    })
</script>


</body>
</html>
