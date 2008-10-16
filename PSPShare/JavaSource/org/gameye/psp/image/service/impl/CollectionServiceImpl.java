package org.gameye.psp.image.service.impl;

import java.util.List;
import java.util.Map;

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

}
