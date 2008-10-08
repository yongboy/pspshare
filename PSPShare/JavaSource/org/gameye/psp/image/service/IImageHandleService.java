package org.gameye.psp.image.service;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 预览图生成的策略接口
 */

public interface IImageHandleService {

	/**
	 * 主要用于判断是否为大图，小图就copy
	 * 
	 * @param source
	 *            源图片位置
	 * @return Dimension 图片的大小
	 * @throws IOException
	 */
	Dimension getImgInfo(String source) throws IOException;

	Dimension getImgInfo(InputStream input) throws IOException;

	/**
	 * 处理生成预览图的策略方法 大小判定，比例调整已在PreivewManager，请直接使用width,height
	 * 当前方法不等比例缩放，缩放成指定的宽、高
	 * 
	 * @param source
	 *            源图片位置
	 * @param target
	 *            保存预览图位置
	 * @param width
	 *            预览图宽度
	 * @param height
	 *            预览图高度
	 * @throws IOException
	 */
	void generate(String source, String target, int width, int height)
			throws IOException;

	/**
	 * 缩放图片
	 * 
	 * @param source
	 *            要缩放图片的原始路径 eg: f:/old.jpg
	 * @param target
	 *            保存缩放结果路径 eg: f:/new.jpg
	 * @param width
	 *            要缩放的宽
	 * @param height
	 *            要缩放的高
	 * @param proportion
	 *            是否启用等比例缩放，true ： 启用等比例缩放，否则，直接生成指定宽和高的图片
	 * @throws IOException
	 */
	void generate(String source, String target, int width, int height,
			boolean proportion) throws IOException;

	void generate(InputStream input, String target, int width, int height)
			throws IOException;

	// void generate(String source, String target, int x, int y, int width,
	// int height) throws IOException;

	/**
	 * 图像切割（改），从指定位置开始截取一段图像
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param targetImageFile
	 *            要保存切割结果后图像的路径
	 * 
	 * @param x
	 *            目标切片起点x坐标
	 * @param y
	 *            目标切片起点y坐标
	 * @param destWidth
	 *            目标切片宽度
	 * @param destHeight
	 *            目标切片高度
	 */
	void cutterImage(String srcImageFile, String targetImageFile, int x, int y,
			int destWidth, int destHeight) throws IOException;

	/**
	 * 对图片先缩放然后进行截取缩放等
	 * 
	 * @param imgSrcPath
	 *            原始图片路径
	 * @param srcWidth
	 *            原始图片缩放后的像素值width
	 * @param srcHeight
	 *            原始图片缩放后的像素值height
	 * @param subImageBounds
	 *            保存着要截取图片的像素值和坐标位置
	 * @param targetPath
	 *            要保存子级文件的路径
	 */
	void resizeAndCutImage(String imgSrcPath, int srcWidth, int srcHeight,
			Rectangle subImageBounds, String targetPath)throws IOException;

	/**
	 * 对图片先缩放然后进行截取缩放等
	 * 
	 * @param srcImgFile
	 *            原始图片File实例
	 * @param srcWidth
	 *            原始图片缩放后的像素值width
	 * @param srcHeight
	 *            原始图片缩放后的像素值height
	 * @param subImageBounds
	 *            保存着要截取图片的像素值和坐标位置
	 * @param targetPath
	 *            要保存子级文件的路径
	 */
	void resizeAndCutImage(File srcImgFile, int srcWidth, int srcHeight,
			Rectangle subImageBounds, String targetPath)throws IOException;
	
	/**
	 * 转换JPG文件到PNG文件
	 * 
	 * @param jpgFile
	 *            JPG文件实例
	 * @param pngPath
	 *            要保存PNG图片的路基你个
	 * @throws IOException
	 */
	void tranJPG2PNG(File jpgFile, String pngPath) throws IOException;

	/**
	 * 转换JPG文件到PNG文件
	 * 
	 * @param jpgPath
	 *            JPG文件实例
	 * @param pngPath
	 *            要保存成PNG图片的路径
	 * @throws IOException
	 */
	void tranJPG2PNG(String jpgPath, String pngPath) throws IOException;
	
	/**
	 * 转换JPG文件到PNG文件
	 * 
	 * @param inputStream
	 *            一个JPG文件的输入流
	 * @param pngPath
	 * @throws IOException
	 */
	void tranJPG2PNG(InputStream inputStream, String pngPath)
			throws IOException;

}