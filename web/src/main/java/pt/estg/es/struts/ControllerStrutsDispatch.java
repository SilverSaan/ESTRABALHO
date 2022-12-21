package pt.estg.es.struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerStrutsDispatch extends DispatchAction {


    public ActionForward runOk(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                HttpServletResponse response) throws Exception {


        FormStruts formStruts = (FormStruts) form;

        formStruts.getAluno().setNome("Alterei o nome ok");

        return mapping.findForward("ok");
    }

    public ActionForward runNotOk(ActionMapping mapping,
                               ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {


        FormStruts formStruts = (FormStruts) form;

        formStruts.getAluno().setNome("Alterei o nome notOk");

        return mapping.findForward("notok");
    }
}
