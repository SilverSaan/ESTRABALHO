package pt.estgp.es.services;

import pt.estg.es.model.unidadeCurricular;
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
public class ucServices implements ServicesInterface<unidadeCurricular> {


    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Override
    @AuditAnnotation(conf = "find All")
    public List<unidadeCurricular> findAll() {
        log.info("Listando todas UC");
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<unidadeCurricular> criteria = cb.createQuery(unidadeCurricular.class);

        Root<unidadeCurricular> uc = criteria.from(unidadeCurricular.class);
        criteria.select(uc).orderBy(cb.asc(uc.get("nome")));
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public void remove(long id) {
        unidadeCurricular entity = em.find(unidadeCurricular.class, id);
        log.info("A Remover UC" + entity.getNome() + ": " + entity.getCode());
        em.remove(entity);
    }

    @Override
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

    @Override
    public void create(unidadeCurricular incmng) {
        em.persist(incmng);
    }

    public org.hibernate.SessionFactory obtainSessionFactory()
    {
        return em.getEntityManagerFactory()
                .unwrap(org.hibernate.SessionFactory.class);
    }


}
