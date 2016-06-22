package com.app.server.da.impl;

import org.hibernate.SessionFactory;

import com.app.server.da.IEntityQuery;
import com.app.server.da.IEntityQueryFactory;

public class EntityQueryFactory implements IEntityQueryFactory {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	} 
	
	public <E> IEntityQuery<E> createQuery(Class<E> clazz) {
		return new EntityQuery<E>(clazz, sessionFactory);
	}
	




}
