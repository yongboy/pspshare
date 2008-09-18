package org.gameye.psp.image.service.impl;

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

	public void saveImage(Image image) {
		imageDao.save(image);
	}

}
