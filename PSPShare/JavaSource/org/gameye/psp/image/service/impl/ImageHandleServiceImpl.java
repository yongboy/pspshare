package org.gameye.psp.image.service.impl;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.gameye.psp.image.service.IImageHandleService;
import org.gameye.psp.image.service.IImageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 标准的Image处理的装饰器模式实现
 * 
 * 原作者 梁飞 liangfei0201@163.com 尊重原作者，改动大，已非原貌 代码运行有问题，现已经修改之
 */

@Service("imageHandleService")
public class ImageHandleServiceImpl implements IImageHandleService {

	private IImageProvider imageProvider;

	@Autowired
	public ImageHandleServiceImpl(IImageProvider imageProvider) {
		this.imageProvider = imageProvider;
	}

	public Dimension getImgInfo(String source) throws IOException {
		BufferedImage image = imageProvider.readImage(source);
		return new Dimension(image.getWidth(), image.getHeight());
	}

	public Dimension getImgInfo(InputStream input) throws IOException {
		BufferedImage image = imageProvider.readImage(input);
		return new Dimension(image.getWidth(), image.getHeight());
	}

	public void generate(String source, String target, int width, int height)
			throws IOException {
		// 当前操作不等比例缩放
		generate(source, target, width, height, false);
	}

	public void generate(String source, String target, int width, int height,
			boolean proportion) throws IOException {
		Image img = null;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Applet app = new Applet();
		MediaTracker mt = new MediaTracker(app);
		try {
			img = tk.getImage(source);
			mt.addImage(img, 0);
			mt.waitForID(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// //下面一行代码，效率很低
		// BufferedImage img = ImageIO.read(new File(source));
		// boolean proportion = false;
		if (img.getWidth(null) == -1) {
			System.out.println(" can't read,retry!");
			return;
		} else {
			// 设为等比缩放
			if (proportion) { // 判断是否是等比缩放.
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) img.getWidth(null)) / (double) width
						+ 0.1;
				double rate2 = ((double) img.getHeight(null)) / (double) height
						+ 0.1;
				double rate = rate1 > rate2 ? rate1 : rate2;
				width = (int) (((double) img.getWidth(null)) / rate);
				height = (int) (((double) img.getHeight(null)) / rate);
			}
			BufferedImage buffImg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			buffImg.getGraphics().drawImage(img, 0, 0, width, height, null);

			imageProvider.saveImage(buffImg, target);
		}
	}

	public void generate(InputStream input, String target, int width, int height)
			throws IOException {
		BufferedImage img = imageProvider.readImage(input);
		if (img.getWidth(null) == -1) {
			System.out.println(" can't read,retry!" + "<BR>");
			return;
		} else {
			BufferedImage buffImg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			buffImg.getGraphics().drawImage(img, 0, 0, width, height, null);

			imageProvider.saveImage(buffImg, target);
		}
	}

	public void cutterImage(String srcImageFile, String targetImageFile, int x,
			int y, int destWidth, int destHeight) throws IOException {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度

			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				// 改进的想法:是否可用多线程加快切割速度
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();
				// 输出为文件
				// 一种方法
				// 转化结束,用时 ：13391
				// File targetFile = new File(targetImageFile);
				// ImageIO.write(tag, "JPEG", targetFile);

				// // 另外一种方法 ：转化结束,用时 ：13375
				tag.getGraphics().drawImage(img, 0, 0, null);
				imageProvider.saveImage(tag, targetImageFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resizeAndCutImage(File srcImgFile, int srcWidth, int srcHeight,
			Rectangle subImageBounds, String targetPath) throws IOException {
		BufferedImage bufferedImage = resize(imageProvider
				.readImage(srcImgFile), srcWidth, srcHeight);
		saveSubImage(bufferedImage, subImageBounds, targetPath);
	}

	public void resizeAndCutImage(String imgSrcPath, int srcWidth,
			int srcHeight, Rectangle subImageBounds, String targetPath)
			throws IOException {
		resizeAndCutImage(new File(imgSrcPath), srcWidth, srcHeight,
				subImageBounds, targetPath);
	}

	/**
	 * 实现图像的等比缩放
	 * 
	 * @param source
	 * @param targetW
	 * @param targetH
	 * @return
	 */
	private BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx < sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	private void saveSubImage(BufferedImage image, Rectangle subImageBounds,
			String subImageFile) throws IOException {
		if (subImageBounds.x < 0 || subImageBounds.y < 0
				|| subImageBounds.width - subImageBounds.x > image.getWidth()
				|| subImageBounds.height - subImageBounds.y > image.getHeight()) {
			System.out.println("Bad   subimage   bounds");
			return;
		}
		BufferedImage subImage = image.getSubimage(subImageBounds.x,
				subImageBounds.y, subImageBounds.width, subImageBounds.height);
		// String fileName = subImageFile.getName();
		// String formatName = fileName.substring(fileName.lastIndexOf('.') +
		// 1);
		// ImageIO.write(subImage, formatName, subImageFile);
		imageProvider.saveImage(subImage, subImageFile);
	}

	public void tranJPG2PNG(File jpgFile, String pngPath) throws IOException {
		jpg2PNG(ImageIO.read(jpgFile), pngPath);
	}

	public void tranJPG2PNG(String jpgPath, String pngPath) throws IOException {
		tranJPG2PNG(new File(jpgPath), pngPath);
	}

	public void tranJPG2PNG(InputStream jpgStream, String pngPath)
			throws IOException {
		jpg2PNG(ImageIO.read(jpgStream), pngPath);
	}

	private void jpg2PNG(BufferedImage input, String pngPath)
			throws IOException {
		File output = new File(pngPath);
		if (!output.getParentFile().exists())
			output.getParentFile().mkdirs();

		ImageIO.write(input, "PNG", output);
	}

	public void tranPNG2JPG(File jpgFile, String pngPath) throws IOException {
		png2JPG(ImageIO.read(jpgFile), pngPath);
	}

	private void png2JPG(BufferedImage input, String pngPath)
			throws IOException {
		File output = new File(pngPath);
		if (!output.getParentFile().exists())
			output.getParentFile().mkdirs();

		ImageIO.write(input, "JPEG", output);
	}

	public void tranPNG2JPG(InputStream inputStream, String jpgPath)
			throws IOException {
		png2JPG(ImageIO.read(inputStream), jpgPath);
	}

}