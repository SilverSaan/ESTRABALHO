package pt.estgp.es.services;

import pt.estg.es.model.Aulas;
import pt.estg.es.model.Presenca;
import pt.estg.es.model.Usuario;
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
public class AulasServices{

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

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

    public void create(Aulas incmng) {
        log.info("Criando Aula de" + incmng.getUnidadeCurricular());
        log.info("Na data de: " + incmng.getData());

        em.persist(incmng);
    }

    public void update(long id, Aulas incmng) {
        Aulas entity = em.find(Aulas.class, id);
        entity.setData(incmng.getData());
        entity.setId(incmng.getId());
        entity.setUnidadeCurricular(incmng.getUnidadeCurricular());
        entity.setPresencas(incmng.getPresencas());
    }

    public void remove(long id) {
        Aulas entity = em.find(Aulas.class, id);
        log.info("A Remover Aula" + entity.getData() + ": " + entity.getUnidadeCurricular());
        em.remove(entity);
    }

    /**
     * Eu não tenho ideia se isso vai funcionar - Pedro
     * Supostamente cria uma tabela de presença com o aluno e a aula
     * @param userID id do Usuario Aluno
     * @param aulaID id da Aula em questão
     */
    public void makeAttendance(long userID, long aulaID){
        Usuario Aluno = em.find(Usuario.class, userID);
        Aulas Aula = em.find(Aulas.class, aulaID);

        Presenca attendance = new Presenca();
        attendance.getId().setAulaId(aulaID);
        attendance.getId().setAlunoId(userID);
        attendance.setAula(Aula);
        attendance.setAluno(Aluno);

        Aula.getPresencas().add(attendance);

        em.persist(attendance);
    }

}
