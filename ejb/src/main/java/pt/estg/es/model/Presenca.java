package pt.estg.es.model;

import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;

@Entity
public class Presenca {

    @EmbeddedId
    PresencaID id;


    @ManyToOne
            @MapsId("alunoId")
            @JoinColumn(name = "aluno_id")
            @JsonManagedReference
    Usuario aluno;

    @ManyToOne
            @MapsId("aulaId")
            @JoinColumn(name = "aula_id")
            @JsonManagedReference
    Aulas aula;

    public PresencaID getId() {
        return id;
    }

    public void setId(PresencaID id) {
        this.id = id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Aulas getAula() {
        return aula;
    }

    public void setAula(Aulas aula) {
        this.aula = aula;
    }
}
