package org.gameye.psp.image.service;

import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.Collection;
import org.gameye.psp.image.entity.User;

public interface ICollectionService {

	Collection getById(long id);

	void add(Collection collection);

	Map<Integer, List<Collection>> pagedImages(int page, int size, User user,
			String order);

	/**
	 * 得到上一个收藏
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	Collection getPreCollection(String userId, long id);

	/**
	 * 得到下一个收藏
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	Collection getNextCollection(String userId, long id);
}
