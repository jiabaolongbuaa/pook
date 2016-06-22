package com.app.server.da.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.app.server.da.IEntityPersist;

public class EntityPersist extends HibernateDaoSupport implements
		IEntityPersist {

	public void remove(Object object) {
		getHibernateTemplate().delete(object);
	}

	public void save(Object object) {
		getHibernateTemplate().save(object);
	}

	public void saveOrUpdate(Object object) {
		getHibernateTemplate().saveOrUpdate(object);
	}

	public void update(Object object) {
		getHibernateTemplate().update(object);
	}

}
