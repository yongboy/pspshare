package test;

import org.gameye.psp.image.dao.IImageDao;
import org.gameye.psp.image.entity.LastPlace;
import org.gameye.psp.image.entity.User;
import org.gameye.psp.image.service.ILastPlaceService;
import org.gameye.psp.image.service.IUserService;

public class TestPagedImages {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		IImageService imgS = (IImageService)Beans.getBean("imageService");
//		Map<Integer, List<Image>>imgMap = imgS.pagedImages(0, 0, null);
//		for(Integer inte : imgMap.keySet()){
//			System.out.println("all total : " + inte) ;
//		}
		
		IImageDao imageDao = (IImageDao)Beans.getBean("imageDao");
		
		IUserService userS = (IUserService)Beans.getBean("userService");
		
		User user = userS.getById("yongboy");
		System.out.println("user name :" + user.getId());
		
		ILastPlaceService lastPlaceService = (ILastPlaceService)Beans.getBean("lastPlaceService");
		
		LastPlace lastPlace = lastPlaceService.getLastTimePlace(user);
		
		if(lastPlace == null){
			System.out.println( "lastPlace 为空 ~" );
		}else{
			System.out.println("lastPlace is not" +
					" nlll");
		}
		
		
		
//		List<Object> params = new ArrayList<Object>();
//		StringBuilder sb = new StringBuilder();
//		sb.append("from org.gameye.psp.image.entity.Image where ");
//		sb.append("user = ? ");
//		params.add(user);
//		Image img = null;
//		List<Image> imgs= imageDao.pagedQuery(sb.toString(),2, params	.toArray());
//		
////		for(Integer inte : imgMap.keySet()){
////			img = imgMap.get(inte);
//		System.out.println("all total : " +imgs.size()) ;
//		for(Image i : imgs){
//			System.out.println( i.getNowName());
//		}
////	}
		
	}

}
