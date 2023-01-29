/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pt.estg.es.model;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {


    @Id
    @GeneratedValue
    private Long id;


    @Column(name = "nome", nullable = false)
    private String nome;


    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "isdocente", columnDefinition = "boolean default false", nullable = false)
    private Boolean isTeacher = false;

    @OneToMany(mappedBy = "aluno")
            @JsonBackReference
    Set<Presenca> presencas;

    @ManyToMany
            @JoinTable(name = "inscricoes",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "uc_id"))
            @JsonBackReference
    Set<unidadeCurricular> ucs;

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

    public Set<Presenca> getPresencas() {
        return presencas;
    }

    public void setPresencas(Set<Presenca> presencas) {
        this.presencas = presencas;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<unidadeCurricular> getUcs() {
        return ucs;
    }

    public void setUcs(Set<unidadeCurricular> ucs) {
        this.ucs = ucs;
    }
}

