package pt.estg.es.model;

import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "class")
@NamedQuery(name= "aulasFindbyUcID", query = "SELECT a FROM Aulas a where a.unidadeCurricular.id = :id")
public class Aulas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /*
    @Column(name = "nome", nullable = false)
    String nome;
    */
    @ManyToOne(targetEntity = unidadeCurricular.class, fetch = FetchType.EAGER)
    unidadeCurricular unidadeCurricular;

    @OneToMany(mappedBy = "aula", fetch = FetchType.EAGER)
            @JsonManagedReference(value = "aula_presenca")
    Set<Presenca> presencas;


    @Column(nullable = false)
    LocalDateTime data;

    @Column(nullable = true, length = 765)
    String sumario;

    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
