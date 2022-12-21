package pt.estg.es.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "Cursos")
public class Cursos {

    @Id
    long id;

    @Column(name = "nome" , nullable = false)
    String name;

    @Column(name= "codigo", nullable = false)
    long code;

    @OneToMany(mappedBy = "cursoId")
    @Column(name = "ucs")
    Set<unidadeCurricular> ucs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
