package com.demo.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.demo.dao.IDataBaseControlDAO;

//@Repository("baseDao")
public class DataBaseControlDAOImpl implements IDataBaseControlDAO {

	final Logger log = Logger.getLogger(DataBaseControlDAOImpl.class);
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate = null;

	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * �����µ�ʵ��
	 * 
	 * @param obj
	 *            ӳ��BEAN����
	 */
	public void setSave(Object obj) {
		hibernateTemplate.save(obj);
	}

	/**
	 * ���ʵ��״̬��ѡ�񱣴���߸��¡�
	 * 
	 * @param obj
	 *            ӳ��BEAN����
	 */
	public void setSaveOrUpdate(Object obj) {
		hibernateTemplate.saveOrUpdate(obj);
	}

	/**
	 * ��ݶ���ɾ��
	 * 
	 * @param obj
	 *            ӳ��BEAN����
	 */
	public void setDelete(Object obj) {
		hibernateTemplate.delete(obj);
	}

	/**
	 * ��ݶ���ɾ��
	 * 
	 * @param obj
	 *            ӳ��BEAN����
	 */
	public void setUpdate(Object obj) {
		hibernateTemplate.update(obj);
	}

	public void setUpdate(final String sql) {
		hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {		
				return session.createQuery(sql).executeUpdate();		 
	}
}, false);
	}

	/**
	 * ����һ���¼
	 * 
	 * @param obj
	 *            ��ݱ�ӳ��� javabean ����
	 * @param id
	 *            ��Ҫ��ѯ����� �����õ��������
	 * @return Object
	 */

	public Object getOneAnnal(String path, Integer id) {

		return hibernateTemplate.get(path, id);

	}

	public Object getOneAnnal(String path, String id) {

		return hibernateTemplate.get(path, id);

	}

	/**
	 * ԭ��̬��ѯ
	 */
	public List getSelect(String sql) {
		return hibernateTemplate.find(sql);
	}

	/**
	 * ���ڷ�ҳ
	 * 
	 * @param sql
	 * @param startIndex
	 *            ��ʼ�±�
	 * @param endIndex
	 *            �����±�
	 * @return List
	 */
	public List getSelect(String sql, int startIndex, int endIndex) {
		List list = hibernateTemplate.find(sql).subList(startIndex, endIndex);
		return list;
	}

	private int searchNum = -1;

	/**
	 * ���ڷ�ҳ ?�Ƿ���ǹر�session ��
	 * 
	 * @param sql
	 * @param pageSize
	 *            ��ȡ�ļ�¼��
	 * @param startIndex
	 *            ��ʼ�±�
	 * @return List
	 */
	public List getSearchResult(final String sql, final int startIndex,
			final int pageSize) {
		return hibernateTemplate.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
					Query query = session.createQuery(sql);
					searchNum = query.list().size();
					query.setFirstResult(startIndex);
					query.setMaxResults(pageSize);
					return query.list();
			}
		});
	}

	public List getPageList(String hql, int currPage, int pageSize) {
		int startIndex = 0;
		if (currPage > 1) {
			startIndex = (currPage - 1) * pageSize;
		}
		return getSearchResult(hql, startIndex, pageSize);
	}

	public int getSearchNum() {
		return searchNum;
	}

	public Object getOneAnnal(String path, Long id) {
		return hibernateTemplate.get(path, id);
	}
}