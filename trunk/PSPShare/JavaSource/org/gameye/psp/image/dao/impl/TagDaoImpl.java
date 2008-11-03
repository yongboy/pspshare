package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ITagDao;
import org.gameye.psp.image.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.common.dao.base.BaseDaoImpl;

@Repository("tagDao")
public class TagDaoImpl extends BaseDaoImpl<Tag, Long> implements ITagDao {
}
