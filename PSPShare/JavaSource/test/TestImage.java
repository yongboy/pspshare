package test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.gameye.psp.image.entity.Collection;
import org.gameye.psp.image.service.ICollectionService;

public class TestImage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		IImageService is = (IImageService)Beans.getBean("imageService");
//		String chooseIds = "678|34|35|";
//		StringTokenizer token = new StringTokenizer(chooseIds, "|");
//		if (token.countTokens() > 30) {
//			return;
//		}
//		Set<String> idList = new HashSet<String>();
//		while (token.hasMoreTokens()) {
//			idList.add(token.nextToken());
//		}
//		List<Image>images = is.getImageByIds(idList);
//		System.out.println(images.size());
		
		
		
		
//		ICollectionService cols = (ICollectionService)Beans.getBean("collectionService");
//		String chooseIds = "2|3|4|";
//		StringTokenizer token = new StringTokenizer(chooseIds, "|");
//		if (token.countTokens() > 30) {
//			return;
//		}
//		Set<String> idList = new HashSet<String>();
//		while (token.hasMoreTokens()) {
//			idList.add(token.nextToken());
//		}
//		List<Collection>colls = cols.getCollectionByIds(idList);
//		System.out.println(colls.size());
		
		
		
		ICollectionService cols = (ICollectionService)Beans.getBean("collectionService");
		String chooseIds = "2|";
		StringTokenizer token = new StringTokenizer(chooseIds, "|");
		if (token.countTokens() > 30) {
			return;
		}
		Set<String> idList = new HashSet<String>();
		while (token.hasMoreTokens()) {
			idList.add(token.nextToken());
		}
		boolean result= cols.deleteCollectionByIds(idList);
		System.out.println(result);
	}

}
