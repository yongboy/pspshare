package test;

import org.gameye.psp.image.entity.Type;
import org.gameye.psp.image.service.ITypeService;

public class TestImgTypes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ITypeService typeS = (ITypeService)Beans.getBean("typeService");
		Type type = typeS.loadType(2);
		System.out.println(type.getTitle());

	}

}
