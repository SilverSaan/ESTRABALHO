package pt.estgp.es.services;

import pt.estg.es.model.Cursos;
import pt.estg.es.model.fatoParticipacao;
import pt.estg.es.model.unidadeCurricular;
import pt.estg.es.security.AuditAnnotation;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class FatoParticipacaoService {

    private Logger log;

    private EntityManager em;

    @AuditAnnotation(conf = "findAll")
    public List<fatoParticipacao> findAll(){
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<fatoParticipacao> factQuery = cb.createQuery(fatoParticipacao.class);
        Root<fatoParticipacao> fatos = factQuery.from(fatoParticipacao.class);

        factQuery.select(fatos).orderBy(cb.desc(fatos.get("id")));
        return  em.createQuery(factQuery).getResultList();
    }

    public void remove(long id) {
        fatoParticipacao entity = em.find(fatoParticipacao.class, id);
        log.info("A Remover Curso" + entity.getFactID() + ": " + entity.getDescription());
        em.remove(entity);
    }

    public fatoParticipacao find(long id){
        return em.find(fatoParticipacao.class, id);
    }

    public void update(long id, fatoParticipacao incmng) {
        fatoParticipacao entity = em.find(fatoParticipacao.class, id);
        entity.setPresenca(incmng.getPresenca());
        entity.setFactID(incmng.getFactID());
        entity.setEvaluation(incmng.getEvaluation());
        entity.setDescription(incmng.getDescription());
    }

    public void create(fatoParticipacao incmng) {
        em.persist(incmng);
    }
}
