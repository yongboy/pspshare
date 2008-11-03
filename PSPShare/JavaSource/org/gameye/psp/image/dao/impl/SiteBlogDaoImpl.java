package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ISiteBlogDao;
import org.gameye.psp.image.entity.SiteBlog;
import org.springframework.stereotype.Repository;

import com.common.dao.base.BaseDaoImpl;

@Repository("siteBlogDao")
public class SiteBlogDaoImpl extends BaseDaoImpl<SiteBlog, Integer> implements
		ISiteBlogDao {
}
