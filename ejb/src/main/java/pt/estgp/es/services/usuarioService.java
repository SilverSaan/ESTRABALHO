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
package pt.estgp.es.services;

import pt.estg.es.model.Presenca;
import pt.estg.es.model.Usuario;
import pt.estg.es.security.AuditAnnotation;
import pt.estg.es.security.Auditavel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

//import org.jboss.as.quickstarts.kitchensink_ear.model.Aluno_;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Auditavel
@Stateless
public class usuarioService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;


    public void criarNovoAluno(Usuario usuario) throws Exception
    {
        log.info("Registando " + usuario.getNome());
        em.persist(usuario);
    }


    public Usuario loadById(Long id) {
        return em.find(Usuario.class, id);
    }


    @AuditAnnotation(conf = "find all")
    public List<Usuario> findAll(){

        log.info("Listando todos os alunos");
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);

        Root<Usuario> aluno = criteria.from(Usuario.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        //criteria.select(aluno).orderBy(cb.asc(aluno.get(Aluno_.nome)));
        criteria.select(aluno).orderBy(cb.asc(aluno.get("nome")));
        return em.createQuery(criteria).getResultList();
    }

    @AuditAnnotation(conf = "find all hibernate")
    public List<Usuario> findAllHibernate(){

        log.info("Listando todos os alunos modo hibernate");
        List<Usuario> list = obtainSessionFactory().getCurrentSession()
                .createCriteria(Usuario.class)
                .list();
        return list;
    }


    public void salvar(Usuario usuario) throws Exception {
        log.info("Registando " + usuario.getNome());

        //Usando o Entity Manager
        em.persist(usuario);

        //Usando o Hibernate
        //obtainSessionFactory().getCurrentSession().save(member);

    }

    public Set<Presenca> getAttendanceList(Long UsuarioId){
        Usuario aluno = em.find(Usuario.class,UsuarioId);
        return aluno.getPresencas();


    }

    public Usuario validateLoginInformation(String username, String password){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);

        return (Usuario) em.createQuery("SELECT u from Usuario u where u.nome = :username and u.password = :password").
                setParameter("username", username).setParameter("password", password).getSingleResult();
    }

    public org.hibernate.SessionFactory obtainSessionFactory()
    {
        return em.getEntityManagerFactory()
                .unwrap(org.hibernate.SessionFactory.class);
    }

}
