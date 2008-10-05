package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ISuggestionDao;
import org.gameye.psp.image.dao.base.BaseDaoImpl;
import org.gameye.psp.image.entity.Suggestion;
import org.springframework.stereotype.Repository;

@Repository("suggestionDao")
public class SuggestionDaoImpl extends BaseDaoImpl<Suggestion, Integer>
		implements ISuggestionDao {
}
