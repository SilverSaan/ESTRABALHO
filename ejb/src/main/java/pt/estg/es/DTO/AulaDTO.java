package pt.estg.es.DTO;

import javax.persistence.Id;
import java.util.Date;

public class AulaDTO {


    private long unidadeCurricularID;
    private Date data;
    private String sumario;

    public long getUnidadeCurricularID() {
        return unidadeCurricularID;
    }

    public void setUnidadeCurricularID(long unidadeCurricularID) {
        this.unidadeCurricularID = unidadeCurricularID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }


}
