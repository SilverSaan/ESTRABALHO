package pt.estg.es.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import pt.estg.es.DTO.UsuarioDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "uc")
public class unidadeCurricular implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JsonBackReference
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

    @JsonIgnore
    public Set<UsuarioDTO> getAlunosDTO(){
        return this.alunos.stream().map(aluno->{
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(aluno.getId());
            dto.setNome(aluno.getNome());
            dto.setEmail(aluno.getEmail());
            dto.setNumero(aluno.getNumero());
            dto.setTeacher(aluno.getTeacher());
            return dto;
        }).collect(Collectors.toSet());
    }



}
