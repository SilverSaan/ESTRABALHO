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
package pt.estg.es.dao;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import pt.estg.es.model.Usuario;
import org.jboss.as.quickstarts.kitchensink_ear.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import pt.estg.es.security.AuditAnnotation;
import pt.estg.es.security.Auditavel;
import pt.estg.es.security.AuditedInterceptor;
import pt.estgp.es.services.usuarioService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class UsuarioBdIT
{

    //Configuração do Arquilian para adicionar os recursos da Arquitetura EE
    @Deployment
    public static Archive<?> createTestArchive() {

        //O ShrinkWrap é o componente que faz a emulação do ambiente EE, neste caso vamos criar uma WAR emulada
        //apesar desta app ser um ejb jar não há problema de emularmos uma WAR
        //com as classes que nos interessam e adicionamos os recursos de configuração que nos interessam
        return ShrinkWrap.create(WebArchive.class, "test.war")

            .addClasses(Usuario.class, usuarioService.class, Resources.class, Auditavel.class, AuditAnnotation.class, AuditedInterceptor.class)

            //Usa a BD em memória
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")

            //Usa o MySQL e requer que se crie o esquema primeiro
            .addAsResource("META-INF/test-persistence-mysql.xml", "META-INF/persistence.xml")


            //Usa os interceptores
            .addAsResource("META-INF/beans.xml", "META-INF/beans.xml")

            //Não usa os interceptores
            //.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")

            //Deploy our test datasource
            //.addAsWebInfResource("test-ds.xml", "test-ds.xml");

            //Adiciona o Datasource do MySql requer criação da base de dados ES no MySQL, username e pass no ficheiro
            .addAsWebInfResource("mysql-ds.xml", "mysql-ds.xml");
    }

    @Inject
    usuarioService usuarioService;

    @Inject
    EntityManager em;

    @Inject
    Logger log;

    static Usuario a  = new Usuario();

    @Test
    public void testRegister() throws Exception {

        Usuario a = new Usuario();
        a.setNome("Joao");
        a.setNumero(123);
        a.setEmail("joao@ipportalegre.pt");
        usuarioService.salvar(a);
        assertNotNull(a.getId());
        log.info(a.getNome() + " was persisted with id " + a.getId());
        List<Usuario> all = usuarioService.findAll();
        assertTrue(all.size() > 1);
    }

    void testRegisterLoad() throws Exception{
        Usuario a = new Usuario();
        a.setNome("Joao" + Math.random() *1000);
        a.setNumero(123);
        a.setEmail("jao@ipportalegre.pt");
        usuarioService.salvar(a);
        assertNotNull(a.getId());

        Usuario usuario2 = usuarioService.loadById(a.getId());
        assertEquals(usuario2.getNome(), a.getNome());

    }

}
