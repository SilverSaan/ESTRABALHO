package pt.estgp.es.services;

import pt.estg.es.DTO.UsuarioDTO;
import pt.estg.es.model.Usuario;
import pt.estg.es.model.unidadeCurricular;
import pt.estg.es.security.AuditAnnotation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Stateless
public class ucServices{


    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @AuditAnnotation(conf = "find All")
    public List<unidadeCurricular> findAll() {
        log.info("Listando todas UC");
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<unidadeCurricular> criteria = cb.createQuery(unidadeCurricular.class);

        Root<unidadeCurricular> uc = criteria.from(unidadeCurricular.class);
        criteria.select(uc).orderBy(cb.asc(uc.get("nome")));
        return em.createQuery(criteria).getResultList();
    }

    public void remove(long id) {
        unidadeCurricular entity = em.find(unidadeCurricular.class, id);
        log.info("A Remover UC" + entity.getNome() + ": " + entity.getCode());
        em.remove(entity);
    }

    public void update(long id, unidadeCurricular incmng) {
        unidadeCurricular entity = em.find(unidadeCurricular.class, id);
        log.info("A Alterar UnidadeCurricular \nEntidade Curricular anterior:" +
                "\n Nome:" + entity.getNome() +
                "\n Docente:" + entity.getDocente().getNome() +
                "\n Entidade Alterada: \n" +
                " Nome" + incmng.getNome() + "\n" +
                " Docente" + incmng.getDocente().getNome());
        entity.setNome(incmng.getNome());
        entity.setCode(incmng.getCode());
        entity.setDocente(incmng.getDocente());
        em.merge(entity);
    }

    public void create(unidadeCurricular incmng) {
        em.persist(incmng);
    }

    public unidadeCurricular find(long id){
        return em.find(unidadeCurricular.class, id);
    }

    public org.hibernate.SessionFactory obtainSessionFactory()
    {
        return em.getEntityManagerFactory()
                .unwrap(org.hibernate.SessionFactory.class);
    }

    public Set<UsuarioDTO> getInscritos(long UCId){
        unidadeCurricular uc = em.find(unidadeCurricular.class, UCId);
        return uc.getAlunosDTO();
    }


}
