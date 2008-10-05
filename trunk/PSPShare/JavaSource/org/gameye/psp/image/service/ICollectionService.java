package org.gameye.psp.image.service;

import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.Collection;

public interface ICollectionService {
	void add(Collection collection);

	Map<Integer, List<Collection>> pagedImages(int page, int size,
			Long userId, String order);
}
