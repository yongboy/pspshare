package org.gameye.psp.image.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.gameye.psp.image.service.IImageProvider;

public class ImageProviderImpl implements IImageProvider {

	public BufferedImage readImage(String source) throws IOException {
		return ImageIO.read(new File(source));
	}

	public BufferedImage readImage(File file) throws IOException {
		return ImageIO.read(file);
	}

	public BufferedImage readImage(InputStream input) throws IOException {
		return ImageIO.read(input);
	}

	public void saveImage(BufferedImage image, String target)
			throws IOException {
		File targetFile = new File(target);
		if (!targetFile.getParentFile().exists())
			targetFile.getParentFile().mkdirs();
		ImageWriter writer = null;
		ImageOutputStream outputStream = null;
		try {
			ImageTypeSpecifier type = ImageTypeSpecifier
					.createFromRenderedImage(image);
			Iterator iterator = ImageIO.getImageWriters(type, "JPEG");
			if (!iterator.hasNext()) {
				return;
			}
			writer = (ImageWriter) iterator.next();
			IIOImage iioImage = new IIOImage(image, null, null);
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(1.0f);
			outputStream = ImageIO.createImageOutputStream(targetFile);
			writer.setOutput(outputStream);
			writer.write(null, iioImage, param);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
			if (writer != null) {
				writer.abort();
			}
		}
	}

}