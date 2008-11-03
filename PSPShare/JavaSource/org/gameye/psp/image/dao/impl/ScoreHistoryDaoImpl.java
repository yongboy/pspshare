package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.IScoreHistoryDao;
import org.gameye.psp.image.entity.ScoreHistory;
import org.springframework.stereotype.Repository;

import com.common.dao.base.BaseDaoImpl;

@Repository("scoreHistoryDao'")
public class ScoreHistoryDaoImpl extends BaseDaoImpl<ScoreHistory, Long>
		implements IScoreHistoryDao {
}
