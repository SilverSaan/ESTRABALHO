
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<jsp:useBean id="FormStruts" type="pt.estg.es.struts.FormStruts" scope="request"/>
<div>



    <p>
        Resposta NOT OK
    </p>


    NOME: <bean:write name="FormStruts" property="usuario.nome"/>

</div>