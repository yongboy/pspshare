package org.gameye.psp.image.service.impl;

import java.util.List;

import org.gameye.psp.image.dao.ILastPlaceDao;
import org.gameye.psp.image.entity.LastPlace;
import org.gameye.psp.image.entity.User;
import org.gameye.psp.image.service.ILastPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("lastPlaceService")
@Transactional
public class LastPlaceServiceImpl implements ILastPlaceService {

	@Autowired
	private ILastPlaceDao lastPlaceDao;

	public LastPlace getLastTimePlace(User user) {
		String hql = "from LastPlace where user.id = ? order id desc";
		Object[] o = { user.getId() };
		List<LastPlace> list = lastPlaceDao.pagedQueryList(hql, 0, 1, o);
		if (list == null || list.size() == 0)
			return null;
		return list.get(0);
	}

	public void save(LastPlace lastPlace) {
		lastPlaceDao.save(lastPlace);
	}

}
