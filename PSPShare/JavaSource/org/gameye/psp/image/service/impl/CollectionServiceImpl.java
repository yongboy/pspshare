package org.gameye.psp.image.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.gameye.psp.image.dao.ICollectionDao;
import org.gameye.psp.image.entity.Collection;
import org.gameye.psp.image.entity.User;
import org.gameye.psp.image.service.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("collectionService")
public class CollectionServiceImpl implements ICollectionService {

	@Autowired
	private ICollectionDao collectionDao;

	@Transactional
	public void add(Collection collection) {
		collectionDao.save(collection);
	}

	public Collection getById(long id) {
		return collectionDao.load(id);
	}

	public Map<Integer, List<Collection>> pagedImages(int page, int size,
			User user, String order) {

		if (page < 1)
			page = 1;
		if (size < 1)
			size = 1;
		if (StringUtils.isEmpty(order))
			order = "";
		int startIndex = (page - 1) * size;
		int pageSize = size;

		String hql = "from org.gameye.psp.image.entity.Collection where user =  ? order by id "
				+ order;
		Object[] values = { user };

		return collectionDao.pagedQuery(hql, startIndex, pageSize, values);
	}

	public Collection getNextCollection(String userId, long id) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sb.append("from org.gameye.psp.image.entity.Collection where id > ? ");
		params.add(id);
		if (StringUtils.isEmpty(userId)) {
			sb.append(" and user = null ");
		} else {
			sb.append(" and user.id = ? ");
			params.add(userId);
		}
		sb.append("order by id");

		return getOneCollection(sb, params);
	}

	public Collection getPreCollection(String userId, long id) {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sb.append("from org.gameye.psp.image.entity.Collection where id < ? ");
		params.add(id);
		if (StringUtils.isEmpty(userId)) {
			sb.append(" and user = null ");
		} else {
			sb.append(" and user.id = ? ");
			params.add(userId);
		}
		sb.append("order by id desc");

		return getOneCollection(sb, params);
	}

	private Collection getOneCollection(StringBuilder sb, List<Object> params) {
		List<Collection> list = collectionDao.pagedQueryList(sb.toString(), 0,
				1, params.toArray());
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}

	public List<Collection> getCollectionByIds(Set<String> ids) {
		StringBuilder sb = new StringBuilder();
		sb.append("from org.gameye.psp.image.entity.Collection where id in (");
		for (String id : ids) {
			sb.append("'").append(id).append("',");
		}
		sb.append(")");
		String hql = sb.toString();
		hql = hql.replaceAll(",\\)", "\\)");
		Object[] o = {};
		return collectionDao.queryList(hql, o);
	}

	public boolean deleteCollectionByIds(Set<String> ids) {
		StringBuilder sb = new StringBuilder();
		sb
				.append("delete org.gameye.psp.image.entity.Collection where id in (");
		for (String id : ids) {
			sb.append("'").append(id).append("',");
		}
		sb.append(")");
		String hql = sb.toString();
		hql = hql.replaceAll(",\\)", "\\)");
		Object[] o = {};
		try {
			collectionDao.bulkUpdate(hql, o);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
