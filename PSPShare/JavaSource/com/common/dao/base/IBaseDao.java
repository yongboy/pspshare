package com.common.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface IBaseDao<T, PK extends Serializable> {
	void save(T entity) throws DataAccessException;;

	void update(T entity) throws DataAccessException;;

	void saveOrUpdate(T entity) throws DataAccessException;

	void saveOrUpdateAll(Collection<T> entities) throws DataAccessException;

	void delete(T entity) throws DataAccessException;

	T load(PK id) throws DataAccessException;

	int bulkUpdate(String hql, Object... values) throws DataAccessException;

	Map<Integer, List<T>> pagedQuery(String hql, int startIndex, int pageSize,
			Object... values) throws DataAccessException;

	List<T> pagedQueryList(String hql, int startIndex, int pageSize,
			Object... values) throws DataAccessException;

	List<T> pagedQuery(String hql, int pageSize, Object... values)
			throws DataAccessException;

	List<T> queryList(String hql, Object... values) throws DataAccessException;

	int baseCount(String hql, Object[] params) throws DataAccessException;

	void flush() throws DataAccessException;

	void clear() throws DataAccessException;
}
