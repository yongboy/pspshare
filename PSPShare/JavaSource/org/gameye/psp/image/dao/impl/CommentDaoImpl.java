package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ICommentDao;
import org.gameye.psp.image.dao.base.BaseDaoImpl;
import org.gameye.psp.image.entity.Commentary;
import org.springframework.stereotype.Repository;

@Repository("commentDao")
public class CommentDaoImpl extends BaseDaoImpl<Commentary, Long> implements
		ICommentDao {
}
