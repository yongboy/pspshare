package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class TestReg {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{

//		TestReg tr = new TestReg();
		String src = "F:/test.jpg";
		String target = "F:/test-80.jpg";
		
		doIntImg(src,target,80);
		
		target = "F:/test-60.jpg";
		
		doIntImg(src,target,60);
		
		
		target = "F:/test-40.jpg";
		
		doIntImg(src,target,40);
		
		
		target = "F:/test-30.jpg";
		
		doIntImg(src,target,30);
		
		
		target = "F:/test-20.jpg";
		
		doIntImg(src,target,20);
		
		
		doIntImg(src,target,80);
		
		target = "F:/test-60.jpg";
		
		doIntImg(src,target,60);
		
		
		target = "F:/test-40.jpg";
		
		doIntImg(src,target,40);
		
		
		target = "F:/test-30.jpg";
		
		doIntImg(src,target,30);
		
		
		target = "F:/test-90.jpg";
		
		doIntImg(src,target,90);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//WRITE PNG PICTURE...
		//PNGB 不支持
		
//		target = "F:/test-60.png";
//		
//		doIntImg2(src,target,60);
//		
//		
//		target = "F:/test-40.png";
//		
//		doIntImg2(src,target,40);
//		
//		
//		target = "F:/test-30.png";
//		
//		doIntImg2(src,target,30);
//		
//		
//		target = "F:/test-20.png";
//		
//		doIntImg2(src,target,20);
//		
//		
//		doIntImg2(src,target,80);
//		
//		target = "F:/test-60.png";
//		
//		doIntImg2(src,target,60);
//		
//		
//		target = "F:/test-40.png";
//		
//		doIntImg2(src,target,40);
//		
//		
//		target = "F:/test-30.png";
//		
//		doIntImg2(src,target,30);
//		
//		
//		target = "F:/test-90.png";
//		
//		doIntImg2(src,target,90);

	}
	
	public static void doIntImg(String src,String target,int quality)throws Exception{
		long s = System.currentTimeMillis();
		
//		String src = "F:/test.jpg";
//		String target = "F:/test-1.jpg";
		File targetFile = new File(target);
		
		writeJPEG(targetFile,readImage(src),quality);
		
		long e = System.currentTimeMillis();
		
		System.out.println("花费时间: " + (e-s));
	}
	
	public static void doIntImg2(String src,String target,int quality)throws Exception{
		long s = System.currentTimeMillis();
		
//		String src = "F:/test.jpg";
//		String target = "F:/test-1.jpg";
		File targetFile = new File(target);
		
		writePNG(targetFile,readImage(src),quality);
		
		long e = System.currentTimeMillis();
		
		System.out.println("花费时间: " + (e-s));
	}
	
	public static BufferedImage readImage(String source) throws IOException {
		return ImageIO.read(new File(source));
	}
	
	/** 
	  * 保存图像到 JPEG 文件 
	  * @param file 保存的目标文件 
	  * @param image 保存的源图像 
	  * @param quality 保存的 JPEG 图像质量 
	  * @param listener 保存进度监听器 
	  */ 
	  public static void writeJPEG(File file, BufferedImage image, int quality/*, IIOWriteProgressListener listener*/) throws 
	        FileNotFoundException, IOException { 
	    Iterator it = ImageIO.getImageWritersBySuffix("jpg"); 
	    if (it.hasNext()) { 
	        FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(file); 
	        ImageWriter iw = (ImageWriter) it.next(); 
	        ImageWriteParam iwp = iw.getDefaultWriteParam(); 
	        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); 
	        iwp.setCompressionQuality((float) quality / 100f); 
	        iw.setOutput(fileImageOutputStream); 
//	        iw.addIIOWriteProgressListener(listener); 
	        iw.write(null, new IIOImage(image, null, null), iwp); 
	        iw.dispose(); 
	        fileImageOutputStream.flush(); 
	        fileImageOutputStream.close(); 
	    } 
	  }
	  
	  
	  public static void writePNG(File file, BufferedImage image, int quality/*, IIOWriteProgressListener listener*/) throws 
      FileNotFoundException, IOException { 
  Iterator it = ImageIO.getImageWritersBySuffix("png"); 
  if (it.hasNext()) { 
      FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(file); 
      ImageWriter iw = (ImageWriter) it.next(); 
      ImageWriteParam iwp = iw.getDefaultWriteParam(); 
      iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); 
      iwp.setCompressionQuality((float) quality / 100f); 
      iw.setOutput(fileImageOutputStream); 
//      iw.addIIOWriteProgressListener(listener); 
      iw.write(null, new IIOImage(image, null, null), iwp); 
      iw.dispose(); 
      fileImageOutputStream.flush(); 
      fileImageOutputStream.close(); 
  } 
}
	  

}
