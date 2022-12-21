
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<div>


    <h1>FORM SIMPLES A INVOCAR o testeAoFormStrutsCtrl</h1>
    <html:form styleId="form1" action="/testeAoFormStrutsCtrl">
        <html:errors/>
        <div>
            Nome : <html:text property="usuario.nome"/>
        </div>
        <div>
            Número : <html:text property="usuario.numero"/>
        </div>
        <div>
            <html:submit value="Submeter"/>
        </div>
    </html:form>


    <h1>FORM SIMPLES A INVOCAR o testeAoFormStrutsCtrlDispatch a invocar o runok</h1>
    <html:form styleId="form2" action="/testeAoFormStrutsCtrlDispatch">
        <input type="hidden" name="dispatch" value="runOk">
        <html:errors/>
        <div>
            Nome : <html:text property="usuario.nome"/>
        </div>
        <div>
            Número : <html:text property="usuario.numero"/>
        </div>
        <div>
            <html:submit value="Submeter"/>
        </div>
    </html:form>





    <h1>FORM SIMPLES A INVOCAR o testeAoFormStrutsCtrlDispatch a invocar o runNotOk</h1>
    <html:form styleId="form3"  action="/testeAoFormStrutsCtrlDispatch">
        <input type="hidden" name="dispatch" value="runNotOk">
        <html:errors/>
        <div>
            Nome : <html:text property="usuario.nome"/>
        </div>
        <div>
            Número : <html:text property="usuario.numero"/>
        </div>
        <div>
            <html:submit value="Submeter"/>
        </div>
    </html:form>



</div>