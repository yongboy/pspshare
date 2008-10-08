package test;

import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.service.IImageService;
import org.gameye.psp.image.utils.DateHelper;
import java.util.Locale;

public class TestImage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IImageService is = (IImageService)Beans.getBean("imageService");
//		is.oneTagImages(1, 10, 2, null);
		
		Image img = is.getImage("C4E1F8057F00134A1C89EF651D4");
		System.out.println(img.getDate().toString());
		System.out.println(img.getDate().toGMTString());
		System.out.println(img.getDate().toLocaleString());
		
		System.out.println(DateHelper.formatDate(img.getDate(),
		"EEE, d MMM yyyy HH:mm:ss Z",Locale.ENGLISH));
		
		//yyyyy.MMMMM.dd GGG hh:mm aaa
		System.out.println(DateHelper.formatDate(img.getDate(),
		"yyyyy.MMMMM.dd GGG hh:mm aaa"));
		
		
//		
//		Image img = is.getImage("C4DFD54B7F0014276FDB7FE59B8");
//		Image iig = is.getNextImage(img.getId());
//		System.out.println("id:" + iig.getId());
	}

}
