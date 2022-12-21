package pt.estg.es.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "uc")
public class unidadeCurricular implements Serializable {

    @Id
    long id;

    @Column
    long code;

    @Column
    long cursoId;

    @ManyToOne(targetEntity = Usuario.class)
    Usuario docente;

    @Column(name = "name", nullable = false)
    String nome;

    @ManyToMany(mappedBy = "ucs")
    Set<Usuario> alunos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getDocente() {
        return docente;
    }

    public void setDocente(Usuario docente) {
        this.docente = docente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Usuario> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<Usuario> alunos) {
        this.alunos = alunos;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getCursoId() {
        return cursoId;
    }

    public void setCursoId(long cursoId) {
        this.cursoId = cursoId;
    }
}
