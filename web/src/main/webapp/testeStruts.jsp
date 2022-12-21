
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<div>
TESTE AOS LAYOUTS DO STRUTS

    <p>
        <html:link action="/testeAoStruts2">Teste 2</html:link>
    </p>
    <p>
        <html:link action="/testeAoStruts3">Teste 3</html:link>
    </p>

    <p>
        <a href="<%=request.getContextPath()%>/testeAoStruts2.do">Teste 2 de outra forma</a>
    </p>
    <p>
        <a href="<%=request.getContextPath()%>/testeAoStruts3.do">Teste 3 de outra forma</a>
    </p>



    <p>Entrar nos Forms <html:link action="/testeAoFormStruts">AQUI</html:link> </p>
</div>