package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.IDownHistoryDao;
import org.gameye.psp.image.dao.base.BaseDaoImpl;
import org.gameye.psp.image.entity.DownHistory;
import org.springframework.stereotype.Repository;

@Repository("downHistoryDao")
public class DownHistoryDaoImpl extends BaseDaoImpl<DownHistory, Long> implements
		IDownHistoryDao {}
