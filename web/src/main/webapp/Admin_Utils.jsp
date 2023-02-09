<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 07/02/2023
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin_Utility Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container-fluid" ng-app="adminApp" ng-controller="adminCtrl" >
    <div class="row" ng-if="user.teacher">

        <div class="col-sm-2">
            <button class="btn" ng-click="switchMode('aluno')">Add Aluno</button>
            <button class="btn" ng-click="switchMode('professor')">Add Prof</button>
            <button class="btn" ng-click="switchMode('uc')">Adicionar UC</button> <br>
            <button class="btn btn-success" ng-click="switchMode('insc')">Inscrever Aluno em UC</button>
            <button class="btn btn-info" ng-click="switchMode('curso')">Ver Cursos</button> <br>
            <!--button class="btn btn-warning" ng-click="switchMode('fato')">Testar Fato de Participacao</button-->
            <button class="btn btn-danger" ng-click="refreshData()">Refrescar Dados
            <span class="glyphicon glyphicon-refresh"></span>
            </button> <br>
        </div>

        <div class="col-sm-5">
            <div ng-if="op == 'aluno'" class="form-group">
                <form id="formAluno"  >
                    Nome do Aluno:<br>
                    <input type="text" ng-model="alunoCriar.nome"><br>
                    Numero:<br>
                    <input type="number" ng-model="alunoCriar.numero"><br>
                    Email:<br>
                    <input type="email" ng-model="alunoCriar.email"><br>
                    Password:<br>
                    <input type="password" ng-model="alunoCriar.password"><br>

                    <input type="button" value="Submeter" ng-click="criarAluno()"/>
                </form>
            </div>

            <div ng-if="op == 'professor' " class="form-group">

                <form>
                    Nome do Professor:<br>
                    <input type="text" ng-model="profCriar.nome"><br>
                    Numero:<br>
                    <input type="number" ng-model="profCriar.numero"><br>
                    Email:<br>
                    <input type="email" ng-model="profCriar.email"><br>
                    Password:<br>
                    <input type="password" ng-model="profCriar.password"><br>

                    <input type="button" value="Submeter" ng-click="criarProf()"/>
                </form>

            </div>

            <div ng-if="op =='uc'" class="form-group">
                    <form>
                        Nome da Unidade Curricular:<br>
                        <input type="text" ng-model="uc.nome"><br>
                        Codigo (Pode deixar vazio):
                        <input type="number" ng-model="uc.code"><br>
                        Docente:<br>
                        <div ng-repeat="x in professores">
                            <input type="button" value="SELECT" ng-click="setProfessor(x)">{{x.nome}}<br>
                        </div>
                        Curso:<br>
                        <div ng-repeat="x in cursos">
                            <input type="button" value="SELECT" ng-click="setCurso(x.id)">{{x.nome}}<br>
                        </div>
                        <input type="button" value="Submeter" ng-click="criarUC()"/>
                    </form>
            </div>


            <div ng-if="op == 'curso'">
                <h3>Criar Curso</h3>
                <form>
                    Nome do Curso:<br>
                    <input type="text" ng-model="cursoCreate.nome"><br>
                    Codigo (Pode deixar nulo):<br>
                    <input type="number" ng-model="cursoCreate.code"><br>


                    <input type="button" value="Submeter" ng-click="criarCurso()"/>
                </form>
                <br>
                <table>
                    <thead>
                    <tr>
                        <th>Curso</th>
                        <th>Codigo</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="x in cursos">
                        <td>{{x.nome}}</td>
                        <td>{{x.code}}</td>
                    </tr>
                    </tbody>
                </table>

            </div>

            <div ng-if="op == 'insc'">

                <table >
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Numero</th>
                        <th>Select</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr ng-repeat="x in listaAlunos">
                        <td>{{x.nome}}</td>
                        <td>{{x.numero}}</td>
                        <td><button class="btn btn-success" ng-click="changeAlunoInsc(x.id)">Add</button></td>
                    </tr>
                    </tbody>
                </table>

            </div>



        </div>

        <div class="col-sm-5">
            <div ng-if="op == 'insc'">
                <table>
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Codigo</th>
                        <th>Select</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr ng-repeat="x in listaUcs">
                        <td>{{x.nome}}</td>
                        <td>{{x.code}}</td>
                        <td><button class="btn btn-success" ng-click="changeUCInsc(x.id)">Add</button></td>
                    </tr>
                    </tbody>
                </table>
                <h3>Resultado Final: </h3><br>
                <p>Aluno: {{inscricao.alunoId}}</p><br>
                <p>UC: {{inscricao.ucId}}</p>
                <button value="Submeter" class="btn btn-danger" ng-click="criarInscrito()">CRIAR</button>
            </div>

        </div>




    </div>
</div>


</body>
</html>

<script>


    var app = angular.module("adminApp",[]);

    app.controller('adminCtrl', function ($scope, $http){

        $scope.user = JSON.parse(sessionStorage.getItem("user"));


        $scope.op = 'aluno';

        $scope.alunoCriar = {
            nome: null,
            numero: null,
            email: null,
            password: null
        }

        $scope.usuarios = null;

        $scope.profCriar = {
            nome: null,
            numero: null,
            email: null,
            password: null,
            teacher: true
        }

        $scope.uc = {
            nome: null,
            code: null,
            docente: null,
            cursoId: null
        }

        $scope.cursoCreate = {
            nome: null,
            code: null
        }

        $scope.inscricao = {
            alunoId: null,
            ucId: null
        }

        $scope.listaAlunos = null;

        $scope.setProfessor = function (json){
            $scope.uc.docente = json;
            console.log($scope.uc);
        }
        $scope.setCurso = function (id){
            $scope.uc.cursoId = id;
            console.log($scope.uc);
        }
        $scope.criarCurso = function (){
            $http.post("<%=request.getContextPath()%>/rs/cursos/create", $scope.cursoCreate)
                .then(function (response){
                    console.log(response.data)
                    alert("Sucesso")
                }, function (error){
                    alert(error.data);
                })
        }

        $scope.criarAluno = function (){
            $http.post("<%=request.getContextPath()%>/rs/usuarios/create", $scope.alunoCriar)
                .then(function (response){
                    console.log(response.data)
                    alert("Sucesso")
                })
        }

        $scope.criarProf = function (){
            $http.post("<%=request.getContextPath()%>/rs/usuarios/create", $scope.profCriar)
                .then(function (response){
                    console.log(response.data)
                    alert("Sucesso")
                })
        }

        $http.get("<%=request.getContextPath()%>/rs/usuarios/list").then(function (response){
            $scope.listaAlunos = response.data.filter(function (usuario){
                return !usuario.teacher;
            });
            $scope.professores = response.data.filter(function (usuario){
                return usuario.teacher;
            })
        })

        $http.get("<%=request.getContextPath()%>/rs/cursos/list").then(function (response){
            $scope.cursos = response.data;
        })


        $http.get("<%=request.getContextPath()%>/rs/unCurricular/list").then(function (response){
            $scope.listaUcs = response.data;
        })

        $http.get("<%=request.getContextPath()%>/rs/aula/list").then(function (response){
            $scope.listaAulas = response.data;
        })

        $scope.criarUC = function (){
            $http.post("<%=request.getContextPath()%>/rs/unCurricular/create", $scope.uc).then(function (response){
                alert("Sucesso");
                console.log(response.data);

            }, function (error){
                alert("error");
                console.log(error.data);
            })

        }

        $scope.switchMode = function (mode){
            $scope.op = mode;
            console.log($scope.op);

        }

        $scope.refreshData = function (){
            //Lista de Cursos
            $http.get("<%=request.getContextPath()%>/rs/cursos/list").then(function (response){
                $scope.cursos = response.data;
            })

            $http.get("<%=request.getContextPath()%>/rs/unCurricular/list").then(function (response){
                $scope.listaUcs = response.data;
            })

            $http.get("<%=request.getContextPath()%>/rs/aula/list").then(function (response){
                $scope.listaAulas = response.data;
            })

            //Lista de Usuarios
            $http.get("<%=request.getContextPath()%>/rs/usuarios/list").then(function (response){
                $scope.listaAlunos = response.data.filter(function (usuario){
                    return !usuario.teacher;
                });
                $scope.professores = response.data.filter(function (usuario){
                    return usuario.teacher;
                })
            })
        }

        $scope.changeAlunoInsc = function (id){
            $scope.inscricao.alunoId = id;
        }
        $scope.changeUCInsc = function (id){
            $scope.inscricao.ucId = id;
        }

        $scope.criarInscrito = function (){
            $http.post("<%=request.getContextPath()%>/rs/unCurricular/createInscricao", $scope.inscricao)
            .then(function (response){
                alert("Sucesso");

            }, function (error){
                alert(error.data);
            })
        }

    })


</script>
