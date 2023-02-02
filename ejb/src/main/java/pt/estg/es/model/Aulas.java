package pt.estg.es.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonView;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "class")
public class Aulas implements Serializable {

    @Id
    long id;

    /*
    @Column(name = "nome", nullable = false)
    String nome;
    */
    @ManyToOne(targetEntity = unidadeCurricular.class, fetch = FetchType.EAGER)
    unidadeCurricular unidadeCurricular;

    @OneToMany(mappedBy = "aula", fetch = FetchType.EAGER)
            @JsonManagedReference
    Set<Presenca> presencas;


    @Column(nullable = false)

    LocalDateTime data;

    @Column(nullable = true)
    String sumario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public unidadeCurricular getUnidadeCurricular() {
        return unidadeCurricular;
    }

    public void setUnidadeCurricular(unidadeCurricular unidadeCurricular) {
        this.unidadeCurricular = unidadeCurricular;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Set<Presenca> getPresencas() {
        return presencas;
    }

    public void setPresencas(Set<Presenca> presencas) {
        this.presencas = presencas;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }
}
