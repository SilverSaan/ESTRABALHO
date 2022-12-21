package pt.estg.es.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(unidadeCurricular.class)
public abstract class unidadeCurricular_ {

	public static volatile SingularAttribute<unidadeCurricular, Long> code;
	public static volatile SetAttribute<unidadeCurricular, Usuario> alunos;
	public static volatile SingularAttribute<unidadeCurricular, Usuario> docente;
	public static volatile SingularAttribute<unidadeCurricular, String> nome;
	public static volatile SingularAttribute<unidadeCurricular, Long> id;
	public static volatile SingularAttribute<unidadeCurricular, Long> cursoId;

}

