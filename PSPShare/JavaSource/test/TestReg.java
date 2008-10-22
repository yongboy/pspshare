package test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class TestReg {

	/**
	 * @param args
	 */
	public static void main2(String[] args) throws Exception{

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
		
		
		//测试，发现在压缩率在80-90之间较理想
		// 用时 
//		花费时间: 500
//		花费时间: 78
//		花费时间: 63
//		花费时间: 78
//		花费时间: 62
//		花费时间: 78
//		花费时间: 63
//		花费时间: 78
//		花费时间: 47
//		花费时间: 78
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//WRITE PNG PICTURE...
		//PNG 不支持
		
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
	  
	  
	  /**
	   * 下面的方法不好使用，但提供的默认压缩方法，不错
	   * @param args
	   * @throws Exception
	   */
	  
	  public static void main(String[] args) throws Exception{
		  String srcS = "F:/test.jpg";
		  File src = new File(srcS);
			String target = "F:/test-doCompress.jpg";
			long s = System.currentTimeMillis();
			doCompress(src,target);
			long e = System.currentTimeMillis();
			
			
			//75% 转换使用时间 : 422
			System.out.println("75% 转换使用时间 : " + (e-s));
			
			
			
			target = "F:/test-60.jpg";
			
			doCompressQuality(src,target,60);
			
			
			target = "F:/test-40.jpg";
			
			doCompressQuality(src,target,40);
			
			
			target = "F:/test-30.jpg";
			
			doCompressQuality(src,target,30);
			
			
			target = "F:/test-20.jpg";
			
			doCompressQuality(src,target,20);
			
			
			doCompressQuality(src,target,80);
			
			target = "F:/test-60.jpg";
			
			doCompressQuality(src,target,60);
			
			
			target = "F:/test-40.jpg";
			
			doCompressQuality(src,target,40);
			
			
			target = "F:/test-30.jpg";
			
			doCompressQuality(src,target,30);
			
			
			target = "F:/test-90.jpg";
			
			doCompressQuality(src,target,90);
	  }
	  
	  
	  /**
	     * 可以自定义压缩比例
	     * @param srcFile
	     */
	    private static void doCompressQuality(File srcFile,String target,int imageQuality)  {
	    	long s = System.currentTimeMillis();
	    	
	        BufferedImage src = null;
	        FileOutputStream out =  null;
	        JPEGImageEncoder encoder = null;
	        JPEGEncodeParam  param   = null;
	        
	        try{
	            src= ImageIO.read(srcFile);//or png.
//	            if(isOverlay){
//	                out= new FileOutputStream(srcFile);
//	            }else{
	                out= new FileOutputStream(target);
//	            }
	            encoder= JPEGCodec.createJPEGEncoder(out);
	            param= encoder.getDefaultJPEGEncodeParam(src);            
	            param.setQuality(imageQuality, true);
	            encoder.setJPEGEncodeParam(param);            
	            encoder.encode(src);//近JPEG编码
	            out.close();
	            //ImageIO.write(src, "jpeg", new File(outPath+File.separator+file.getName()));            
//	            log("over:"+file.getPath());
	        } catch (IOException e){
	        	e.printStackTrace();
//	            log("compress error:"+file.getPath()+";error:"+e.getMessage());
	        }
	        out = null;
	        encoder = null;
	        param   = null;
	        src     = null;
	        
	        long e = System.currentTimeMillis();
	        
	        
	        System.out.println("分辨率 :" + imageQuality + " 转换使用时间 : " + (e-s));
	    }

	    /**
	     * 压缩图片，默认压缩比例75%
	     * @param srcFile
	     */
	    private static void doCompress(File srcFile,String target){
	        File descFile = null;
	        boolean writeOk=false;
//	        long fileSize=srcFile.length();

	        try{
	            
//	            if(isOverlay){                
	                descFile = new File(target);
	                writeOk = ImageIO.write(ImageIO.read(srcFile), "jpeg", descFile);
//	            }else{
//	                descFile = new File(outPath+File.separator+file.getName());
//	                writeOk = ImageIO.write(ImageIO.read(file), "jpeg", descFile);
//	            }        
	            
	            if(writeOk){
//	                log("ok:"+file.getPath()+"\t"+fileSize+"\t"+descFile.length());
	            }else{
//	                log("fail:"+file.getPath()+"\t"+fileSize+"\t"+descFile.length());
	            }            
	            
	        } catch (Exception e){
	        	e.printStackTrace();
//	            log("compress error:"+file.getPath()+";descFile="+descFile+";error:"+e.getMessage());
	        }
	        descFile=null;
	    }

}
