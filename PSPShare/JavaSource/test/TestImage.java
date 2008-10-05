package test;

import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.service.IImageService;

public class TestImage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IImageService is = (IImageService)Beans.getBean("typeService");
		is.oneTagImages(1, 10, 2, null);
		
//		
//		Image img = is.getImage("C4DFD54B7F0014276FDB7FE59B8");
//		Image iig = is.getNextImage(img.getId());
//		System.out.println("id:" + iig.getId());
	}

}
