package org.gameye.psp.image.service.impl;

import java.util.List;
import java.util.Map;

import org.gameye.psp.image.dao.ISiteBlogDao;
import org.gameye.psp.image.entity.SiteBlog;
import org.gameye.psp.image.service.ISiteBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("siteBlogService")
public class SiteBlogServiceImpl implements ISiteBlogService {

	@Autowired
	private ISiteBlogDao siteBlogDao;

	@Transactional
	public void addBlog(SiteBlog blog) {
		siteBlogDao.save(blog);
	}

	public SiteBlog getBlog(Integer id) {
		return siteBlogDao.load(id);
	}

	public Map<Integer, List<SiteBlog>> pagedImages(int page, int size) {

		if (page < 1)
			page = 1;
		if (size < 1)
			size = 10;

		String hql = "from org.gameye.psp.image.entity.SiteBlog order by id desc";

		int startIndex = (page - 1) * size;
		Object[] o = {};
		return siteBlogDao.pagedQuery(hql, startIndex, size, o);
	}

}
