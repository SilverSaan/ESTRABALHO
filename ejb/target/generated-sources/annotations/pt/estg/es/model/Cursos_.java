package pt.estg.es.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cursos.class)
public abstract class Cursos_ {

	public static volatile SingularAttribute<Cursos, Long> code;
	public static volatile SetAttribute<Cursos, unidadeCurricular> ucs;
	public static volatile SingularAttribute<Cursos, String> name;
	public static volatile SingularAttribute<Cursos, Long> id;

}

