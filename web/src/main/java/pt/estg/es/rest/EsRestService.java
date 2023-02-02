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
package pt.estg.es.rest;

import pt.estg.es.model.Aulas;
import pt.estg.es.model.Presenca;
import pt.estg.es.model.Usuario;
import pt.estg.es.model.unidadeCurricular;
import pt.estgp.es.services.usuarioService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read/write the contents of the members table.
 */
@Path("/usuarios")
@RequestScoped
public class EsRestService {

    @Inject
    private Logger log;

    @Inject
    private usuarioService servico;

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> loadAlunos() {
        log.info("Loading alunos");
        return servico.findAll();
    }

    @GET
    @Path("/listHibernate")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> loadAlunosHibernate() {
        log.info("Loading alunos mode hibernate");
        return servico.findAllHibernate();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario loadAlunoById(@PathParam("id") long id)
    {
        log.info("Loading aluno: " + id);
        Usuario usuario = servico.loadById(id);
        if (servico == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return usuario;
    }


    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response criarAluno(Usuario usuario) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates member using bean validation
            // validateMember(member); //usaria as anotações javax.validator que não estamos a usar no Aluno

            servico.salvar(usuario);

            // Create an "ok" response
            builder = Response.ok(usuario);
        /*} catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());*/
      /*  } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);*/
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }


    @GET
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarAluno(@QueryParam("nome") String nome,@QueryParam("numero") Integer numero) {

        Response.ResponseBuilder builder;

        try {
            // Validates member using bean validation
            // validateMember(member); //usaria as anotações javax.validator que não estamos a usar no Aluno

            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setNumero(numero);

            servico.salvar(usuario);

            // Create an "ok" response
            builder = Response.ok(usuario);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario verifyLogin(Login info){
        return servico.validateLoginInformation(info.getUsername(), info.getPassword());
    }



    @GET
    @Path("/{id:[0-9][0-9]*}/partic")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Aulas> loadPresencas(@PathParam("id") long id){
        return servico.getListaPresenca(id);
    }

    @GET
    @Path("/{id:[0-9][0-9]*}/ucs")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<unidadeCurricular> loadUCInscrito(@PathParam("id") long id){
        return servico.loadById(id).getUcs();
    }

    /*
    private void validateMember(Member member) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Member>> violations = validator.validate(member);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<>(violations));
        }

        // Check the uniqueness of the email address
        if (emailAlreadyExists(member.getEmail())) {
            throw new ValidationException("Unique Email Violation");
        }
    }*/

}
