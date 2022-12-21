package pt.estg.es.struts;

import org.apache.struts.action.*;
import pt.estg.es.model.Usuario;

import javax.servlet.http.HttpServletRequest;

public class FormStruts extends ActionForm
{
    public FormStruts() {
        this.usuario = new Usuario();
    }

    Usuario usuario;

    public Usuario getAluno() {
        return usuario;
    }

    public void setAluno(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        if (getAluno().getNome().equals(""))
            errors.add(ActionErrors.GLOBAL_MESSAGE, new SimpleMessage("O nome não pode ser vazio"));

        if (getAluno().getNumero() <= 0)
            errors.add(ActionErrors.GLOBAL_MESSAGE, new SimpleMessage("O numero tem de ser maior que ZERO"));

        return errors;
    }
}
