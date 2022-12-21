package pt.estg.es.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Aulas.class)
public abstract class Aulas_ {

	public static volatile SetAttribute<Aulas, Presenca> presencas;
	public static volatile SingularAttribute<Aulas, Date> data;
	public static volatile SingularAttribute<Aulas, unidadeCurricular> unidadeCurricular;
	public static volatile SingularAttribute<Aulas, Long> id;

}

