package pt.estg.es.struts;

import org.apache.struts.action.ActionMessage;


public class SimpleMessage extends ActionMessage
{
    String message;
    public SimpleMessage(String message)
    {
        super(message);
        this.message = message;
    }

    @Override
    public boolean isResource() {
        return false;
    }

    @Override
    public String toString() {
        return message;
    }
}

