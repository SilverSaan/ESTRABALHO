package pt.estgp.es.services;

import pt.estg.es.DTO.InscricaoPOJO;
import pt.estg.es.DTO.UsuarioDTO;
import pt.estg.es.model.Aulas;
import pt.estg.es.model.Presenca;
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
import java.util.stream.Collectors;

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

    /**
     * Retorna todos os Inscritos na unidade curricular
     * @param UCId ID da unidade curricular
     * @return SET (HashSet) de DTOs de Usuarios (Apenas para leitura)
     */
    public Set<UsuarioDTO> getInscritos(long UCId){
        unidadeCurricular uc = em.find(unidadeCurricular.class, UCId);
        return uc.getAlunosDTO();
    }

    /**
     * Inscreve o Aluno em uma Unidade Curricular, recebe um POJO (Plain Old Java Object) que representa a inscrição
     * (Não existente no controlador de Dados relativo ao modelo). Cria a ligação entre Aluno e Unidade Curricular
     * @param inscricao
     */
    public void inscreverAlunoEmUC(InscricaoPOJO inscricao){
        Usuario aluno = em.find(Usuario.class, inscricao.getAlunoId());
        unidadeCurricular uc = em.find(unidadeCurricular.class, inscricao.getUcId());
        //Forçar o carregamento dos alunos
        uc.getAlunos().size();

        uc.getAlunos().add(aluno);

        aluno.getUcs().size();

        aluno.getUcs().add(uc);

        em.merge(uc);
        em.merge(aluno);

    }

    /**
     * Retorna a lista de Aulas da Unidade Curricular especificada pelo ID
     * @param id ID da Unidade Curricular
     * @return Lista de Aulas da UC
     */
    public List<Aulas> getListaAulas(long id){
        //Criação dos criterios
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aulas> criteriaQ = cb.createQuery(Aulas.class);
        Root<Aulas> aulasRoot = criteriaQ.from(Aulas.class);
        //Join entre Aulas e Unidades Curriculares
        Join<Aulas, unidadeCurricular> aulasunidadeCurricularJoin = aulasRoot.join("unidadeCurricular", JoinType.INNER);

        //Seleciona todas as Aulas onde a UnidadeCurricular.id igual ao id passado como parametro
        criteriaQ.select(aulasRoot).where(cb.equal(aulasunidadeCurricularJoin.get("id"), id));
        //Retorna a Lista dos resultados
        return em.createQuery(criteriaQ).getResultList();

    }


}
