package com.app.exercise.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ extends com.app.exercise.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Project, String> name;
	public static volatile SingularAttribute<Project, String> description;
	public static volatile SingularAttribute<Project, Long> id;
	public static volatile SingularAttribute<Project, Long> ownerId;
	public static volatile ListAttribute<Project, Task> tasks;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String OWNER_ID = "ownerId";
	public static final String TASKS = "tasks";

}

