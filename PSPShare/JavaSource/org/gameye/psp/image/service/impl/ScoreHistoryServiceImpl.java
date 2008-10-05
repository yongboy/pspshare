package org.gameye.psp.image.service.impl;

import org.gameye.psp.image.dao.IScoreHistoryDao;
import org.gameye.psp.image.entity.ScoreHistory;
import org.gameye.psp.image.service.IScoreHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("scoreHistoryService")
public class ScoreHistoryServiceImpl implements IScoreHistoryService {

	@Autowired
	private IScoreHistoryDao scoreHistoryDao;

	@Transactional
	public void add(ScoreHistory scoreHistory) {
		scoreHistoryDao.save(scoreHistory);
	}

}
