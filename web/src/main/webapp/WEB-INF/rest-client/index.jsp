<%--
  Created by IntelliJ IDEA.
  User: Pedro
  Date: 24/11/2022
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testar cliente JS</title>
</head>
<body>
    <script>
        function invokePost(servicePath,data){
            fetch(servicePath, {
                method: 'POST',
                headers: {'Content-type' : "application/json"},
                body: JSON.stringify(data)
            }).then(
                response => {
                    response.json().then(
                        json =>{
                            if(response.ok)
                                alert(JSON.stringify(json));
                        }
                    );
                }
            ).catch(
                error => {
                    console.log(error);
                    alert(error);
                    if(fail)
                        fail(error);
                });
        }

        function createAluno(){

            let usuario = {
                nome: "john",
                email: "johndoe@email.com",
                numero: 12553
            };

            invokePost("<%=request.getContextPath()%>/rs/usuarios/create", usuario,
                function (json){
                    alert(JSON.stringify(json));
                },
                function (error){
                    alert(error);
                },);

        }

        function createAlunoForm(nome, numero, email){

            let aluno = {
                nome: nome,
                numero: numero,
                email: email
            };

            invokePost("<%=request.getContextPath()%>/rs/usuarios/create", aluno,
                function (json){
                    alert(JSON.stringify(json));
                },
                function (error){
                    alert(error)
                },);

            document.getElementById("nome").value = '';
            document.getElementById("numero").value = '';
            document.getElementById("email").value = '';
        }

    var button = document.getElementById('submit'),
        value = button.form.valueId.value;



    </script>

    <form id="formAluno" >
        <label for="nome">Nome do Aluno:</label><br>
        <input type="text" id="nome" name="nome"><br>
        <label for="numero">Numero:</label><br>
        <input type="number" id="numero" name="num"><br>
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email"><br>

        <input type="button" value="Submeter" onclick="createAlunoForm(this.form.nome.value,
        this.form.num.value,
        this.form.email.value)"/>
    </form>


    <button onclick="createAluno()">Criar Aluno Teste</button>


</body>
</html>
