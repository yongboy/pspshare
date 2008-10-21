package org.gameye.psp.image.service;

import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.SiteBlog;

public interface ISiteBlogService {
	void addBlog(SiteBlog blog);

	SiteBlog getBlog(Integer id);

	Map<Integer, List<SiteBlog>> pagedImages(int page, int size);
}
