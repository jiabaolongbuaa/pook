/**
 * 
 */
package com.app.server.da.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.app.server.da.IEntityQuery;

public class EntityQuery<T> extends HibernateDaoSupport implements
		IEntityQuery<T> {

	private DetachedCriteria criteria;
	private Class<?> clazz;

	public EntityQuery(Class<T> clazz, SessionFactory factory) {
		criteria = DetachedCriteria.forClass(clazz);
		this.clazz = clazz;
		setSessionFactory(factory);

	}

	public IEntityQuery<T> between(String property, Object low, Object high) {
		criteria.add(Restrictions.between(property, low, high));
		return this;
	}

	public IEntityQuery<T> desc(String property, boolean desc) {
		if (desc) {
			criteria.addOrder(Order.desc(property));
		}
		return this;
	}

	public IEntityQuery<T> eq(String property, Object value, boolean ignoreNull) {
		if (value == null && ignoreNull) {
			return this;
		}
		criteria.add(Restrictions.eq(property, value));
		return this;
	}

	public IEntityQuery<T> eqProperty(String property, String otherProperty) {
		criteria.add(Restrictions.eqProperty(property, otherProperty));
		return this;
	}

	public T get() {
		getHibernateTemplate().setMaxResults(1);
		List<T> resultList = getHibernateTemplate().findByCriteria(criteria);
		if (resultList.size() != 0) {
			return resultList.get(0);
		}
		return null;
	}

	public IEntityQuery<T> ge(String property, Object value, boolean ignoreNull) {
		if (value == null && ignoreNull) {
			return this;
		}
		criteria.add(Restrictions.ge(property, value));
		return this;
	}

	public IEntityQuery<T> in(String property, List<String> values,
			boolean ignoreNull) {
		if (values == null && ignoreNull) {
			return this;
		}
		criteria.add(Restrictions.in(property, values));
		return this;
	}

	public IEntityQuery<T> le(String property, Object value, boolean ignoreNull) {
		if (value == null && ignoreNull) {
			return this;
		}
		criteria.add(Restrictions.le(property, value));
		return this;
	}

	public IEntityQuery<T> ne(String property, Object value, boolean ignoreNull) {
		if (value == null && ignoreNull) {
			return this;
		}
		criteria.add(Restrictions.ne(property, value));
		return this;
	}

	public IEntityQuery<T> like(String property, String value,
			boolean ignoreNull) {
		if ((value == null || value.trim().equals("")) && ignoreNull) {
			return this;
		}
		criteria.add(Restrictions.like(property, value));
		return this;
	}

	public List<T> list() {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List<T> list(int maxResults) {
		getHibernateTemplate().setMaxResults(maxResults);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List<T> list(int startPosition, int maxResults) {
		return getHibernateTemplate().findByCriteria(criteria, startPosition,
				maxResults);
	}

	public IEntityQuery<T> setFetchedProperties(String... properties) {
		ProjectionList projectionList = Projections.projectionList();
		for (int i = 0; i < properties.length; i++) {
			projectionList.add(Projections.property(properties[i]),
					properties[i]);
		}
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Transformers.aliasToBean(clazz));
		return this;
	}

	public IEntityQuery<T> createAlias(String associationPath, String alias) {
		criteria.createAlias(associationPath, alias);
		return this;
	}

	public IEntityQuery<T> isNull(String propertyName) {
		criteria.add(Restrictions.isNull(propertyName));
		return this;
	}

	public IEntityQuery<T> isEmpty(String propertyName) {
		criteria.add(Restrictions.isEmpty(propertyName));
		return this;
	}

	public IEntityQuery<T> isNotNull(String propertyName) {
		criteria.add(Restrictions.isNotNull(propertyName));
		return this;
	}

	@Override
	public int rowCount() {
		Session session = getSession();
		Criteria c = criteria.getExecutableCriteria(session);
		c.setProjection(Projections.rowCount());
		Integer returnValue = Integer.parseInt(String.valueOf(c.uniqueResult()
				.toString()));
		releaseSession(session);
		return returnValue;
	}

}
