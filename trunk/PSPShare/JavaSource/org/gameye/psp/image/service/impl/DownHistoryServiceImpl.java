package org.gameye.psp.image.service.impl;

import org.gameye.psp.image.dao.IDownHistoryDao;
import org.gameye.psp.image.entity.DownHistory;
import org.gameye.psp.image.service.IDownHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("downHistoryService")
@Transactional
public class DownHistoryServiceImpl implements IDownHistoryService {

	@Autowired
	private IDownHistoryDao downHistoryDao;

	public void add(DownHistory downHistory) {
		downHistoryDao.save(downHistory);
	}

}
