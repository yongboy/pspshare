package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.ILastPlaceDao;
import org.gameye.psp.image.entity.LastPlace;
import org.springframework.stereotype.Repository;

import com.common.dao.base.BaseDaoImpl;

@Repository("lastPlaceDao")
public class LastPlaceDaoImpl extends BaseDaoImpl<LastPlace, Long> implements
		ILastPlaceDao {
}
