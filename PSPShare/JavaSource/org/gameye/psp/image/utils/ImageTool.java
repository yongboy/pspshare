package org.gameye.psp.image.utils;

import org.gameye.psp.image.entity.Image;

public class ImageTool {

	public static String getPath(Image image) {
		return "images/" + image.getPath() + image.getId() + image.getPostfix();
	}

	public static String getThumbnail(Image image) {
		return "images/" + image.getPath() + "thumbnail/" + image.getId()
				+ image.getPostfix();
	}
}
