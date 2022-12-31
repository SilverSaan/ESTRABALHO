package pt.estgp.es.services;

import pt.estg.es.model.Aulas;
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
public class AulasServices implements ServicesInterface<Aulas>{

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Override
    @AuditAnnotation(conf = "findAll")
    public List<Aulas> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Aulas> aulasCQ = cb.createQuery(Aulas.class);
        Root<Aulas> aulas = aulasCQ.from(Aulas.class);

        aulasCQ.select(aulas).orderBy(cb.desc(aulas.get("id")));
        return  em.createQuery(aulasCQ).getResultList();
    }

    public Aulas find(long id){
        return em.find(Aulas.class, id);
    }

    @Override
    public void create(Aulas incmng) {
        log.info("Criando Aula de" + incmng.getUnidadeCurricular());
        log.info("Na data de: " + incmng.getData());

        em.persist(incmng);
    }

    @Override
    public void update(long id, Aulas incmng) {
        Aulas entity = em.find(Aulas.class, id);
        entity.setData(incmng.getData());
        entity.setId(incmng.getId());
        entity.setUnidadeCurricular(incmng.getUnidadeCurricular());
        entity.setPresencas(incmng.getPresencas());
    }

    @Override
    public void remove(long id) {
        Aulas entity = em.find(Aulas.class, id);
        log.info("A Remover Aula" + entity.getData() + ": " + entity.getUnidadeCurricular());
        em.remove(entity);
    }
}
