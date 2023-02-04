package pt.estgp.es.services;

import org.hibernate.Hibernate;
import pt.estg.es.model.*;
import pt.estg.es.security.AuditAnnotation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.xml.ws.Response;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public void edit(Aulas aula){
        Aulas managedAula = em.merge(aula);
        em.flush();
    }

    /**
     * Eu não tenho ideia se isso vai funcionar - Pedro
     * Supostamente cria uma tabela de presença com o aluno e a aula
     * @param id id da Presença
     */
    public void makeAttendance(PresencaID id){
        Usuario Aluno = em.find(Usuario.class, id.getAlunoId());
        Aulas Aula = em.find(Aulas.class, id.getAulaId());

        Presenca attendance = new Presenca();
        attendance.setId(id);
        attendance.setAula(Aula);
        attendance.setAluno(Aluno);

        Aula.getPresencas().add(attendance);

        em.persist(attendance);
    }

    public void deleteAttendance(PresencaID a){
        Aulas aula = em.find(Aulas.class,a.getAulaId());
        Presenca presenca = em.find(Presenca.class, a);
        aula.getPresencas().remove(presenca);
        em.remove(presenca);

    }

    public List<Usuario> getAlunosPresentes(long id){
        Aulas aula = em.find(Aulas.class, id);
        Set<Presenca> presencas = aula.getPresencas();
        return presencas.stream().map(Presenca::getAluno).collect(Collectors.toList());
    }

    public Set<Presenca> getAllPresencas(long aulaID){
        Aulas aula = em.find(Aulas.class,aulaID);
        return aula.getPresencas();
    }

    public void updateAllPresencas(long aulaID, Set<Presenca> presencas){
        Aulas aula = find(aulaID);

        if(aula == null){
            return;
        }
        aula.getPresencas().size();

        for(Presenca pre : aula.getPresencas()){
            em.remove(pre);
        }

        aula.getPresencas().clear();


        for (Presenca pre : presencas) {
            pre.setId(new PresencaID(aulaID, pre.getId().getAlunoId()));
            em.merge(pre);
            aula.getPresencas().add(pre);
        }
        em.merge(aula);
        em.flush();
    }

    public Presenca findPresenca(PresencaID id) {
        return em.find(Presenca.class, id);
    }
}
