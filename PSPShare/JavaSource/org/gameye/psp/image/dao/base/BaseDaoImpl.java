package org.gameye.psp.image.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.Image;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
//		return (T) getHibernateTemplate().get(this.entityClass, id);
		return (T)sessionFactory.openSession().get(Image.class, id);
	}

	public Map<Integer, List<T>> pagedQuery(String hql, int startIndex,
			int pageSize, Object... values) throws DataAccessException {
		int allCount = baseCount(hql, values);
		Query query = createQuery(hql, values);
		List<T> list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();
		if (list == null)
			return null;
		Map<Integer, List<T>> map = new HashMap<Integer, List<T>>();
		map.put(allCount, list);
		return map;
	}

	public List<T> pagedQueryList(String hql, int startIndex, int pageSize,
			Object... values) throws DataAccessException {
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();
		if (list == null)
			return null;
		return (List<T>) list;
	}

	public int baseCount(String hql, Object[] params) {
		try {
			String countQueryString = " select count (*) "
					+ SqlHelper.removeSelect(SqlHelper.removeOrders(hql));
			Query query = createQuery(countQueryString, params);
			// 修正在一些情况下异常原因
			Object obj = query.iterate().next();
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

	protected Query createQuery(String queryString, Object... values) {
		Assert.hasText(queryString);
		Query query = sessionFactory.openSession().createQuery(queryString);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	public List<T> queryList(String hql, Object... values)
			throws DataAccessException {
		return (List<T>) createQuery(hql, values).list();
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
//		getHibernateTemplate().clear();
//		getHibernateTemplate().update(entity);
		getHibernateTemplate().merge(entity);
	}
}
