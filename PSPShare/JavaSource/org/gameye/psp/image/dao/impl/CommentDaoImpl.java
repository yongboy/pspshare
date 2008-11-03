package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ICommentDao;
import org.gameye.psp.image.entity.Commentary;
import org.springframework.stereotype.Repository;

import com.common.dao.base.BaseDaoImpl;

@Repository("commentDao")
public class CommentDaoImpl extends BaseDaoImpl<Commentary, Long> implements
		ICommentDao {
}
