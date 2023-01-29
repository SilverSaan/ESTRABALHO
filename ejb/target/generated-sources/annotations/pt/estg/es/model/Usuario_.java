package pt.estg.es.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SetAttribute<Usuario, Presenca> presencas;
	public static volatile SingularAttribute<Usuario, String> password;
	public static volatile SingularAttribute<Usuario, Integer> numero;
	public static volatile SetAttribute<Usuario, unidadeCurricular> ucs;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SingularAttribute<Usuario, Long> id;
	public static volatile SingularAttribute<Usuario, Boolean> isTeacher;
	public static volatile SingularAttribute<Usuario, String> email;

}

