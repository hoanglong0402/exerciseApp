package com.app.exercise.domain;

import com.app.exercise.domain.enumeration.UserRole;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.app.exercise.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<User, UserRole> role;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SingularAttribute<User, String> email;

	public static final String ROLE = "role";
	public static final String ID = "id";
	public static final String USER_NAME = "userName";
	public static final String EMAIL = "email";

}

