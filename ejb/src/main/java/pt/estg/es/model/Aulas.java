package pt.estg.es.model;

import javax.persistence.*;
import java.io.Serializable;
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
    @ManyToOne(targetEntity = unidadeCurricular.class)
    unidadeCurricular unidadeCurricular;

    @OneToMany(mappedBy = "aula")
    Set<Presenca> presencas;

    @Column(nullable = false)
    Date data;

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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Set<Presenca> getPresencas() {
        return presencas;
    }

    public void setPresencas(Set<Presenca> presencas) {
        this.presencas = presencas;
    }


    }
