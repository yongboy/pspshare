package test;

import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.Image;
import org.gameye.psp.image.service.IImageService;

public class TestPagedImages {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IImageService imgS = (IImageService)Beans.getBean("imageService");
		Map<Integer, List<Image>>imgMap = imgS.pagedImages(0, 0, null);
		for(Integer inte : imgMap.keySet()){
			System.out.println("all total : " + inte) ;
		}

	}

}
