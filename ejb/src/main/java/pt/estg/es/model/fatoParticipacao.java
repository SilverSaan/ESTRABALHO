package pt.estg.es.model;

import javax.persistence.*;
import java.io.Serializable;


//@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,name = "Fact_Disc", length = 5)
@Entity
public class fatoParticipacao implements Serializable {

    @Id
    long factID;

    @ManyToOne()
    Presenca presenca;

    @Column
    String description;

    @Column
    long evaluation;

    public long getFactID() {
        return factID;
    }

    public void setFactID(long factID) {
        this.factID = factID;
    }

    public Presenca getPresenca() {
        return presenca;
    }

    public void setPresenca(Presenca presenca) {
        this.presenca = presenca;
    }

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
}
