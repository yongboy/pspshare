package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ITagDao;
import org.gameye.psp.image.dao.base.BaseDaoImpl;
import org.gameye.psp.image.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("tagDao")
public class TagDaoImpl extends BaseDaoImpl<Tag, Long> implements ITagDao {
}
