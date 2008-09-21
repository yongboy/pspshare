package org.gameye.psp.image.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gameye.psp.image.dao.IImageDao;
import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imageService")
public class ImageServiceImpl implements IImageService {
	@Autowired
	private IImageDao imageDao;

	public Image getImage(String id) {
		return imageDao.load(id);
	}

	public void updateImage(Image image) {
		imageDao.update(image);
	}

	public void saveImage(Image image) {
		imageDao.save(image);
	}

	public Map<Integer, List<Image>> pagedImages(int page, int size,
			String order) {
		if (page < 1)
			page = 1;
		if (size < 1)
			size = 1;
		if (StringUtils.isEmpty(order))
			order = "";
		int startIndex = (page - 1) * size;
		int pageSize = size;
		Object[] ob = {};
		String hql = "from org.gameye.psp.image.entity.Image order by date "
				+ order;
		return imageDao.pagedQuery(hql, startIndex, pageSize, ob);
	}
}
