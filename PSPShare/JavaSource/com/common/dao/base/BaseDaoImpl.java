package com.common.dao.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository("baseDao")
public class BaseDaoImpl<T, PK extends Serializable> implements IBaseDao<T, PK> {
	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> entityClass;// 实体对象
	private String className;// 实体类名

	private HibernateTemplate getHibernateTemplate() {
		return new HibernateTemplate(sessionFactory);
	}

	public BaseDaoImpl() {
		this.entityClass = ReflectObjectHelper
				.getSuperClassGenricType(getClass());
		this.className = entityClass.getName();
	}

	public BaseDaoImpl(final Class<T> persistentClass) {
		this.entityClass = persistentClass;
		this.className = persistentClass.getName();
	}

	public void setEntityClass(final Class<T> entityClass) {
		this.entityClass = entityClass;
		this.className = entityClass.getName();
	}

	public int bulkUpdate(String hql, Object... values)
			throws DataAccessException {
		return getHibernateTemplate().bulkUpdate(hql, values);
	}

	public void clear() throws DataAccessException {
		getHibernateTemplate().clear();
	}

	public void delete(T entity) throws DataAccessException {
		getHibernateTemplate().delete(entity);
	}

	public void flush() throws DataAccessException {
		getHibernateTemplate().flush();
	}

	public T load(PK id) throws DataAccessException {
		return (T) getHibernateTemplate().get(this.entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, List<T>> pagedQuery(final String hql,
			final int startIndex, final int pageSize, final Object... values)
			throws DataAccessException {
		return (Map<Integer, List<T>>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						int allCount = baseCount(session, hql, values);
						Query query = createQuery(session, hql, values);
						List<T> list = query.setFirstResult(startIndex)
								.setMaxResults(pageSize).list();
						if (list == null)
							return null;
						Map<Integer, List<T>> map = new HashMap<Integer, List<T>>();
						map.put(allCount, list);
						return map;

					}
				});
	}
	
	private int baseCount(Session session, String hql, Object[] params) {
		try {
			String countQueryString = " select count (*) "
					+ SqlHelper.removeSelect(SqlHelper.removeOrders(hql));
			Query q = createQuery(session, countQueryString, params);
			// 修正在一些情况下异常原因
			Object obj = q.iterate().next();
			if (obj == null)
				return 0;
			else
				try {
					return Integer.parseInt(obj.toString());
				} catch (NumberFormatException e) {
					return 0;
				}
		} catch (HibernateException he) {
			he.printStackTrace();
			return 0;
		}
	}

	private Query createQuery(Session session, String queryString,
			Object... values) {
		Assert.hasText(queryString);
		Query query = session.createQuery(queryString);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	public List<T> pagedQueryList(final String hql, final int startIndex,
			final int pageSize, final Object... values)
			throws DataAccessException {
		return (List<T>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Query query = createQuery(session, hql, values);
						List<T> list = query.setFirstResult(startIndex)
								.setMaxResults(pageSize).list();
						return list;
					}
				});
	}

	public List<T> pagedQuery(String hql, int pageSize, Object... values)
			throws DataAccessException {
		return pagedQueryList(hql, 0, pageSize, values);
	}

	public int baseCount(String hql, Object[] params) {
		try {
			String countQueryString = " select count (*) "
					+ SqlHelper.removeSelect(SqlHelper.removeOrders(hql));
			List list = getHibernateTemplate().find(countQueryString, params);
			if (list == null || list.size() == 0)
				return 0;

			// 修正在一些情况下异常原因
			Object obj = list.get(0);
			if (obj == null)
				return 0;
			else
				try {
					return Integer.parseInt(obj.toString());
				} catch (NumberFormatException e) {
					return 0;
				}
		} catch (HibernateException he) {
			he.printStackTrace();
			return 0;
		}
	}

	public List<T> queryList(String hql, Object... values)
			throws DataAccessException {
		return (List<T>) getHibernateTemplate().find(hql, values);
	}

	public void save(T entity) throws DataAccessException {
		getHibernateTemplate().save(entity);
	}

	public void saveOrUpdate(T entity) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<T> entities)
			throws DataAccessException {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public void update(T entity) throws DataAccessException {
		// getHibernateTemplate().clear();
		// getHibernateTemplate().update(entity);
		getHibernateTemplate().merge(entity);
	}
}
