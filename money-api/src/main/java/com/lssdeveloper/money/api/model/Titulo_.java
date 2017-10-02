package com.lssdeveloper.money.api.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Titulo.class)
public abstract class Titulo_ {

	public static volatile SingularAttribute<Titulo, Long> codigo;
	public static volatile SingularAttribute<Titulo, BigDecimal> valor;
	public static volatile SingularAttribute<Titulo, String> descricao;

}

