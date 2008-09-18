package org.gameye.psp.image.service;

import org.gameye.psp.image.entity.Image;

public interface IImageService {
	void saveImage(Image image);

	Image getImage(String id);
}
