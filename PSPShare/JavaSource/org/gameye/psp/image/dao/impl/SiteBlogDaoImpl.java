package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ISiteBlogDao;
import org.gameye.psp.image.dao.base.BaseDaoImpl;
import org.gameye.psp.image.entity.SiteBlog;
import org.springframework.stereotype.Repository;

@Repository("siteBlogDao")
public class SiteBlogDaoImpl extends BaseDaoImpl<SiteBlog, Integer> implements
		ISiteBlogDao {
}
