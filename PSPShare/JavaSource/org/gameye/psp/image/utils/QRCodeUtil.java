package org.gameye.psp.image.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.feipy.framework.exception.BaseException;

/**
 * 
 * 对传入的信息进行QRCode编码 目前只针对page的title，code简单编码，后续根据需要扩展
 * 
 * @author zj.dong
 * 
 */
public class QRCodeUtil {
	
	private final static Logger logger = Logger.getLogger(QRCodeUtil.class);

	/**
	 * @param pageTitle 页面标题
	 * @param pageCode 页面code
	 * @param fileAbsName 要保存文件的绝对路径，后缀为jpg格式
	 * @return
	 */
	public static boolean encodePage(String pageTitle, String pageCode,String fileAbsName) throws BaseException{
		try {
			if (fileAbsName == null || fileAbsName.length() == 0) {
				throw new Exception("未指定文件路径");
				//return false;
			}
			if (!fileAbsName.toLowerCase().endsWith(".jpg")) {
				
				fileAbsName = fileAbsName.substring(0, fileAbsName.lastIndexOf(".")) + ".jpg";
			}
			
			String encodeStr = "MEBKM:TITLE:" + (pageTitle == null ? "" : pageTitle) + ";URL:" + (pageCode == null ? "" : pageCode) + ";;";
			
			QRCodeEncoder encoder=new QRCodeEncoder();
			encoder.setQrcodeErrorCorrect('L');
			encoder.setQrcodeEncodeMode('B');
			encoder.setQrcodeVersion(0);
			boolean[][] matrix = encoder.calQrcode(encodeStr.getBytes("GB2312"));
			int len=matrix.length;

			// 每个模块的像素值
			final int modulePixel = 5;
			// 正方形周围4模块的空白
			final int margin = 8;
			
			
	        BufferedImage img = new BufferedImage((len + margin) * modulePixel,
					(len + margin) * modulePixel, BufferedImage.TYPE_INT_RGB);
			img.createGraphics();
			Graphics2D gs2 = (Graphics2D) img.getGraphics();

			// 整个背景填充为白色
			gs2.setBackground(Color.WHITE);
			gs2.fillRect(0, 0, img.getWidth(), img.getHeight());

			// 把matrix里为true的填充为modulePixel大的黑块
			gs2.setColor(Color.BLACK);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix.length; j++) {
					if (matrix[j][i]) {
						gs2.fillRect((4 + j) * modulePixel, (4 + i)
								* modulePixel, modulePixel, modulePixel);
					}
				}
			}
			gs2.dispose();
			
			File file = new File(fileAbsName);
			if (file.exists())
				file.delete();
			
			file = new File(fileAbsName.substring(0, fileAbsName.lastIndexOf("/")));
			file.mkdirs();
			file = new File(fileAbsName);
			
			ImageIO.write(img, "jpg", file);
			return true;
	        
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BaseException(e.getMessage());
		}
		//return false;
	}
}
