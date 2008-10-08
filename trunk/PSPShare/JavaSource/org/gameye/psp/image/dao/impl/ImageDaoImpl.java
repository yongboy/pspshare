package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.IImageDao;
import org.gameye.psp.image.dao.base.BaseDaoImpl;
import org.gameye.psp.image.entity.Image;
import org.springframework.stereotype.Repository;

@Repository("imageDao")
public class ImageDaoImpl extends BaseDaoImpl<Image, Long> implements
		IImageDao {
}
