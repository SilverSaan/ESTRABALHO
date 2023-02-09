package pt.estgp.es.services;

import org.hibernate.Hibernate;
import pt.estg.es.DTO.AulaDTO;
import pt.estg.es.DTO.FatoDTO;
import pt.estg.es.model.*;
import pt.estg.es.security.AuditAnnotation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.xml.ws.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    /**
     * Procura todas as aulas em persistencia
     */
    public List<Aulas> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Aulas> aulasCQ = cb.createQuery(Aulas.class);
        Root<Aulas> aulas = aulasCQ.from(Aulas.class);

        aulasCQ.select(aulas).orderBy(cb.desc(aulas.get("id")));
        return  em.createQuery(aulasCQ).getResultList();
    }

    /**
     *
     * @param id ID Da Aula
     * @return Aula com ID Especificado
     */
    public Aulas find(long id){
        return em.find(Aulas.class, id);
    }

    /**
     * Cria uma aula a partir de um DTO (Data Transfer Object) que não contém relações.
     * @param aulaDTO Data Transfer Object - Aula
     */
    public void createfromDTO(AulaDTO aulaDTO){
        Aulas aula = new Aulas();
        aula.setData(aulaDTO.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        aula.setUnidadeCurricular(em.find(unidadeCurricular.class, aulaDTO.getUnidadeCurricularID()));
        aula.setSumario(aulaDTO.getSumario());
        create(aula);

    }

    /**
     * Cria uma aula a partir de um objeto Aula
     * @param incmng - Incoming (Objeto Aula que é passado)
     */
    public void create(Aulas incmng) {
        log.info("Criando Aula de" + incmng.getUnidadeCurricular());
        log.info("Na data de: " + incmng.getData());
        em.persist(incmng);
    }

    /**
     * Atualiza uma aula. Não faz um merge
     * @param id ID
     * @param incmng Objeto Aula
     */
    public void update(long id, Aulas incmng) {
        Aulas entity = em.find(Aulas.class, id);
        entity.setData(incmng.getData());
        entity.setId(incmng.getId());
        entity.setUnidadeCurricular(incmng.getUnidadeCurricular());
        entity.setPresencas(incmng.getPresencas());
    }

    /**
     * Remove uma aula a partir de seu ID
     * @param id ID da Aula a remover
     */
    public void remove(long id) {
        Aulas entity = em.find(Aulas.class, id);
        log.info("A Remover Aula" + entity.getData() + ": " + entity.getUnidadeCurricular());
        em.remove(entity);
    }

    /**
     * Faz merge do objeto Aula com sua instancia no modelo de dados
     * @param aula
     */
    public void edit(Aulas aula){
        Aulas managedAula = em.merge(aula);
        em.flush();
    }

    /**
     *
     * Cria uma tabela de presença com o aluno e a aula
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

    /**
     * Deleta uma Presenca em especifico
     * @param id
     */
    public void deleteAttendance(PresencaID id){
        Aulas aula = em.find(Aulas.class,id.getAulaId());
        Presenca presenca = em.find(Presenca.class, id);
        aula.getPresencas().remove(presenca);
        em.remove(presenca);

    }

    /**
     * Retorna uma lista de Usuarios a partir da Aulas ID passado
     * @param id
     * @return Lista de Usuarios
     */
    public List<Usuario> getAlunosPresentes(long id){
        Aulas aula = em.find(Aulas.class, id);
        Set<Presenca> presencas = aula.getPresencas();
        return presencas.stream().map(Presenca::getAluno).collect(Collectors.toList());
    }

    /**
     * Retorna todas as presenças de uma determinada aula
     * @param aulaID ID da aula
     * @return Set(HashSet) de Presenças
     */
    public Set<Presenca> getAllPresencas(long aulaID){
        Aulas aula = em.find(Aulas.class,aulaID);
        return aula.getPresencas();
    }

    /**
     * Altera TODAS as presenças de uma determinada aula, também deleta fatos de participação associados a essas
     * @param aulaID id da Aula
     * @param presencas Lista com todas as presenças novas
     */
    public void updateAllPresencas(long aulaID, Set<Presenca> presencas){
        Aulas aula = find(aulaID);

        if(aula == null){
            return;
        }
        aula.getPresencas().size();

        for(Presenca pre : aula.getPresencas()){
            pre.getFacts().size();
            for(fatoParticipacao fact : pre.getFacts()){
                em.remove(fact);
            }

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

    /**
     * Encontra uma presença em especifico a partir do id da presença (alunoId, aulaId)
     * @param id ID da Presença
     * @return Presença encontrada
     */
    public Presenca findPresenca(PresencaID id) {
        return em.find(Presenca.class, id);
    }


    /**
     * Adiciona Fato de Participação a uma presença em especifico através de FatoDTO (Data Transfer Object) que apenas
     * contém id da presença e campos relativos ao Fato (Não contém a tabela Presença)
     * @param dto
     */
    public void addFato(FatoDTO dto){
        //Encontra uma tabela presença
        Presenca presenca = em.find(Presenca.class,dto.getPresencaID());
        //Força carregamento de dados da presença
        presenca.getFacts().size();
        presenca.getAula().getId();
        presenca.getAluno().getId();
        //Cria novo fato de participacao e define seus campos
        fatoParticipacao novo = new fatoParticipacao();
        novo.setDescription(dto.getDescription());
        novo.setEvaluation(dto.getEvaluation());
        novo.setPresenca(presenca);
        novo.setTipo(dto.getTipo());
        //Adiciona o novo fato a presença
        presenca.getFacts().add(novo);
        //Persiste o novo fato
        em.persist(novo);
        //Modifica/Edita a presença
        em.merge(presenca);
        //Sincroniza com o modelo de dados
        em.flush();

    }

}
