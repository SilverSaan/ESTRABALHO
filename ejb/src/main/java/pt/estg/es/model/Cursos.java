package pt.estg.es.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Cursos")
public class Cursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "nome" , nullable = false)
    String nome;

    @Column(name= "codigo", nullable = false)
    long code;

    @OneToMany(mappedBy = "cursoId")
    @Column(name = "ucs")
            @JsonManagedReference
            @JsonIgnore
    Set<unidadeCurricular> ucs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {this.nome = nome;}

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Set<unidadeCurricular> getUcs() {
        return ucs;
    }

    public void setUcs(Set<unidadeCurricular> ucs) {
        this.ucs = ucs;
    }


}
