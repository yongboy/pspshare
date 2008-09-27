package org.gameye.psp.image.config;

import java.io.File;

public class Constants {
	public static final String savePath = new Constants().getWebRootPath() + "images";
		
//		"E:/www2/yongboy-5ecd3b231c774c31011c9d44f8cd0370/webapp/images";// "D:/java/eclipse 3.2/workspace/PSPShare/WebContent/images"
																											// ;

	public String getWebRootPath() {
		String cla = this.getClass().getClassLoader().getResource(".").getPath();
		// if(cla.startsWith("//")){
		cla = cla.substring(1);
		// }
		int index = cla.indexOf("WEB-INF");
		if (index != -1) {
			cla = cla.substring(0, index);
		}
		return cla.trim();
	}
	
	public String getImgSavePath() {
		
		System.out.println("path:" + new File("").getAbsolutePath());
		
		return getWebRootPath() + "images/";
	}
}
