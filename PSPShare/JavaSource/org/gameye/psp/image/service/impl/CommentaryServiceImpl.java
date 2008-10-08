package org.gameye.psp.image.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gameye.psp.image.dao.ICommentDao;
import org.gameye.psp.image.entity.Commentary;
import org.gameye.psp.image.service.ICommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("commentService")
@Transactional
public class CommentaryServiceImpl implements ICommentaryService {

	@Autowired
	private ICommentDao commentDao;

	public void add(Commentary commentary) {
		commentDao.save(commentary);
	}

	public Map<Integer, List<Commentary>> pagedImages(int page, int size,
			long imageId, String order) {

		if (page < 1)
			page = 1;
		if (size < 1)
			size = 10;
		int startIndex = (page - 1) * size;
		if (StringUtils.isEmpty(order)) {
			order = "desc";
		}
		Object[] values = { imageId };
		String hql = "from Commentary where image.id = ? order by date "
				+ order;

		return commentDao.pagedQuery(hql, startIndex, size, values);
	}

}
