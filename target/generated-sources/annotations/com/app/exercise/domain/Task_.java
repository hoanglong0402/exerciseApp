package com.app.exercise.domain;

import com.app.exercise.domain.enumeration.TaskStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ extends com.app.exercise.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Task, String> description;
	public static volatile SingularAttribute<Task, Project> project;
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, String> title;
	public static volatile SingularAttribute<Task, Long> projectId;
	public static volatile SingularAttribute<Task, Long> assignedTo;
	public static volatile SingularAttribute<Task, TaskStatus> status;

	public static final String DESCRIPTION = "description";
	public static final String PROJECT = "project";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String PROJECT_ID = "projectId";
	public static final String ASSIGNED_TO = "assignedTo";
	public static final String STATUS = "status";

}

