package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ICollectionDao;
import org.gameye.psp.image.entity.Collection;
import org.springframework.stereotype.Repository;

import com.common.dao.base.BaseDaoImpl;

@Repository("collectionDao")
public class CollectionDaoImpl extends BaseDaoImpl<Collection, Long>
		implements ICollectionDao {
}
