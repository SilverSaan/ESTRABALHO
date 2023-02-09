package pt.estgp.es.services;

import pt.estg.es.model.Cursos;
import pt.estg.es.security.AuditAnnotation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CursosServices {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @AuditAnnotation(conf = "findAll")
    public List<Cursos> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Cursos> cursosCQ = cb.createQuery(Cursos.class);
        Root<Cursos> cursos = cursosCQ.from(Cursos.class);

        cursosCQ.select(cursos).orderBy(cb.desc(cursos.get("id")));
        return  em.createQuery(cursosCQ).getResultList();
    }

    public void remove(long id) {
        Cursos entity = em.find(Cursos.class, id);
        log.info("A Remover Curso" + entity.getNome() + ": " + entity.getCode());
        em.remove(entity);
    }

    public Cursos find(long id){
        return em.find(Cursos.class, id);
    }


    public void update(long id, Cursos incmng) {
        Cursos entity = em.find(Cursos.class, id);
        entity.setCode(incmng.getCode());
        entity.setId(incmng.getId());
        entity.setNome(incmng.getNome());
        entity.setUcs(incmng.getUcs());
    }


    public void create(Cursos incmng) {
        log.info("A Criar Curso " + incmng.getNome());
        em.persist(incmng);
    }

}
