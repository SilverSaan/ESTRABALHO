package pt.estg.es.DTO;


import pt.estg.es.model.PresencaID;

public class FatoDTO {


    String description;

    long evaluation;

    String tipo;

    PresencaID presencaID;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(long evaluation) {
        this.evaluation = evaluation;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public PresencaID getPresencaID() {
        return presencaID;
    }

    public void setPresencaID(PresencaID presencaID) {
        this.presencaID = presencaID;
    }
}
