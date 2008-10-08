package org.gameye.psp.image.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * BufferedImage 的读写处理策略接口
 */
public interface IImageProvider {

	public BufferedImage readImage(String source) throws IOException;

	public BufferedImage readImage(File file) throws IOException;

	public BufferedImage readImage(InputStream input) throws IOException;

	public void saveImage(BufferedImage image, String target)
			throws IOException;

}