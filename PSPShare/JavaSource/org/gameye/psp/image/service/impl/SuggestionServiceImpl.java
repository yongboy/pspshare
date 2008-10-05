package org.gameye.psp.image.service.impl;

import java.util.List;
import java.util.Map;

import org.gameye.psp.image.dao.ISuggestionDao;
import org.gameye.psp.image.entity.Suggestion;
import org.gameye.psp.image.service.ISuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("suggestionService")
@Transactional
public class SuggestionServiceImpl implements ISuggestionService {

	@Autowired
	private ISuggestionDao suggestionDao;

	public void add(Suggestion suggestion) {
		suggestionDao.save(suggestion);
	}

	public Map<Integer, List<Suggestion>> pagedSuggestions(int page, int size) {
		if (page < 1)
			page = 1;
		if (size < 1)
			size = 1;
		int startIndex = (page - 1) * size;
		int pageSize = size;
		Object[] ob = {};
		String hql = "from Suggestion order by date desc";
		return suggestionDao.pagedQuery(hql, startIndex, pageSize, ob);
	}

}
