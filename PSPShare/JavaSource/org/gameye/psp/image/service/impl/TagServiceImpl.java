package org.gameye.psp.image.service.impl;

import java.util.Collection;

import org.gameye.psp.image.dao.ITagDao;
import org.gameye.psp.image.entity.Tag;
import org.gameye.psp.image.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tagService")
@Transactional
public class TagServiceImpl implements ITagService {

	@Autowired
	private ITagDao tagDao;

	public void addTags(Collection<Tag> tags) {
		tagDao.saveOrUpdateAll(tags);
	}

}
