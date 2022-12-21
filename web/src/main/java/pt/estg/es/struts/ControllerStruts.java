package pt.estg.es.struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import pt.estg.es.model.Usuario;
import pt.estgp.es.services.usuarioService;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerStruts extends Action {

    //@Inject
    //AlunosService service;
    //Estamos num sistema antigo não usa o CDI temos de ir buscar o serviço com o JNDI da forma convencional



    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {


        //Estamos num sistema antigo não usa o CDI temos de ir buscar o serviço com o JNDI da forma convencional
        //kitchensink-ear nome do ear
        //kitchensink-ear-ejb app que tem o BEAN
        // AlunosService.class.getSimpleName() nome do Bean

        InitialContext i = new InitialContext();
        usuarioService usuarioService = (usuarioService) i.lookup("java:global/kitchensink-ear/kitchensink-ear-ejb/" + usuarioService.class.getSimpleName());


        FormStruts formStruts = (FormStruts) form;

        Usuario a = formStruts.getAluno();
        a.setEmail("teste@email.pt");
        usuarioService.salvar(a);

        formStruts.getAluno().setId(a.getId());

        return mapping.findForward("ok");
    }

}
