
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<jsp:useBean id="FormStruts" type="pt.estg.es.struts.FormStruts" scope="request"/>
<div>



    <p>
        Resposta OK
    </p>


    <div>NOME: <bean:write name="FormStruts" property="usuario.nome"/></div>
    <div>ID: <span id="alunoId"><bean:write name="FormStruts" property="usuario.id"/></span></div>

</div>