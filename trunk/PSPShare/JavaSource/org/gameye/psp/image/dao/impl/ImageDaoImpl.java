package org.gameye.psp.image.dao.impl;

import org.gameye.psp.image.dao.IImageDao;
import org.gameye.psp.image.entity.Image;
import org.springframework.stereotype.Repository;

import com.common.dao.base.BaseDaoImpl;

@Repository("imageDao")
public class ImageDaoImpl extends BaseDaoImpl<Image, Long> implements
		IImageDao {
}
