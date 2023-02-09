package pt.estg.es.DTO;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.validator.constraints.Email;
import pt.estg.es.model.Presenca;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

public class UsuarioDTO {

    private Long id;

    private String nome;

    private Integer numero;

    private String email;

    private Boolean isTeacher = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getTeacher() {
        return isTeacher;
    }

    public void setTeacher(Boolean teacher) {
        isTeacher = teacher;
    }
}
